package controller;

import exceptions.MyADTException;
import exceptions.MyControllerException;
import model.adt.MyIHeap;
import model.state.ProgState;
import model.value.IValue;
import model.value.RefValue;
import repo.IRepo;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final IRepo repo;
    private ExecutorService executor;
    private boolean showAllSteps;

    public Controller(IRepo repo, ExecutorService executor) {
        this.repo = repo;
        this.executor = executor;
        this.showAllSteps = false;
    }

    public Controller(IRepo repo) {
        this.repo = repo;
        this.showAllSteps = false;
    }

    public boolean getShowAllSteps() {return this.showAllSteps;}
    public void setShowAllSteps(boolean showAllSteps) {this.showAllSteps = showAllSteps;}

    public List<Integer> getAddrFromHeap(Collection<IValue> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) throws MyADTException {
        return symTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v-> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    public List<Integer> getAllActiveAddresses(Collection<IValue> symTableValues, MyIHeap heap){
        return symTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->(RefValue)v)
                .flatMap(v->{
                    List<Integer> addresses = new ArrayList<>();
                    while(true){
                        if(v.getAddress() == 0){
                            break;
                        }
                        addresses.add(v.getAddress());
                        try {
                            IValue nextVal = heap.get(v.getAddress());
                            if(nextVal instanceof RefValue){
                                v = (RefValue) nextVal;
                            }
                            else{
                                break;
                            }
                        } catch (MyADTException e) {
                            throw new MyControllerException(e.getMessage());
                        }
                    }
                    return addresses.stream();
                }).collect(Collectors.toList());
    }

    Map<Integer, IValue> safeGarbageCollector(List<Integer> activeAddresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> activeAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void conservativeGarbageCollector(List<ProgState> programStates) {
        Set<Integer> activeAddresses = programStates.stream()
                .flatMap(p -> {
                    return getAllActiveAddresses(p.getSymTable().getValues(), p.getHeap()).stream();
                })
                .collect(Collectors.toSet());

        programStates.forEach(p ->
                p.getHeap().set(safeGarbageCollector(new ArrayList<>(activeAddresses), p.getHeap().getContent())));
    }

    public void allSteps() throws InterruptedException {
        this.executor = Executors.newFixedThreadPool(2);
        List<ProgState> programStateList  = this.removeCompletedPrg(repo.getProgramsList());
        programStateList.forEach(prg ->
        {
            this.repo.logProgramState(prg);
            if (this.getShowAllSteps()) {
                this.displayState(prg);
            }
        });
        while(!programStateList.isEmpty()){
            oneStepForAll(programStateList);
            conservativeGarbageCollector(programStateList);
            programStateList = this.removeCompletedPrg(repo.getProgramsList());
        }

        executor.shutdownNow();
        repo.setProgramsList(programStateList);
    }

    public IRepo getRepo() {
        return repo;
    }

    public List<ProgState> getProgramsList() {
        return repo.getProgramsList();
    }

    public void displayState(ProgState state) {
        System.out.println("Current state:\n" + state.toString());
    }

    public List<ProgState> removeCompletedPrg(List<ProgState> states){
        return states.stream()
                .filter(ProgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAll(List<ProgState> states) throws InterruptedException {
        List<Callable<ProgState>> callables = states.stream()
                .map(p -> ((Callable<ProgState>) p::oneStep))
                .toList();
        List<ProgState> newProgram = this.executor.invokeAll(callables).stream()
                .map(future->{
                    try{
                        return future.get();
                    }catch (Exception e) {
                        throw new MyControllerException(e.getMessage());
                    }
                })
                .filter(Objects::nonNull).toList();
        states.addAll(newProgram);
        states.forEach(prg ->
        {
            this.repo.logProgramState(prg);
            if (this.getShowAllSteps()) {
                this.displayState(prg);
            }
        });
        repo.setProgramsList(states);
    }

    public void executeOneStepForAll() throws InterruptedException {
        this.executor = Executors.newFixedThreadPool(2);
        repo.setProgramsList(removeCompletedPrg(repo.getProgramsList()));
        List<ProgState> program_states=repo.getProgramsList();

        if(!program_states.isEmpty())
        {
            try
            {
                oneStepForAll(repo.getProgramsList());
            }
            catch (InterruptedException error)
            {
                System.out.println(error.getMessage());
            }
            //repo.setProgramsList(removeCompletedPrg(repo.getProgramsList()));
            //executor.shutdown();
            //conservativeGarbageCollector(program_states);
        }
    }
}
