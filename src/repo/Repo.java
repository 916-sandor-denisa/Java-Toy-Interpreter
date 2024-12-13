package repo;

import exceptions.MyRepoException;
import model.state.ProgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo{

    private List<ProgState> states;
    private String logFilePath;

    public Repo(String logFilePath) {
        states = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgState> getProgramsList() {
        return states;
    }

    @Override
    public void setProgramsList(List<ProgState> progState) {
        states = progState;
    }

    @Override
    public void addProgState(ProgState progState) {
        states.add(progState);
    }

    @Override
    public void logProgramState(ProgState progState) throws MyRepoException {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            pw.println(progState.toString());
            pw.close();
        } catch (IOException e) {
            throw new MyRepoException(e.getMessage());
        }
    }

    @Override
    public void setProgList(List<ProgState> progList) {
        this.states = progList;
    }

}
