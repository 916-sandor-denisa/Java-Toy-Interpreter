package GUI;

import exceptions.MyADTException;
import exceptions.MyExpressionException;
import exceptions.MyStatementException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.state.ProgState;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;
import view.command.RunExample;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import controller.Controller;


public class MainController {

    private Controller controller;

    @FXML
    private TextField numberOfProgramStatesTextField;

    @FXML
    private TableView<Pair<Integer, IValue>> heapTableView;

    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> addressColumn;

    @FXML
    private TableColumn<Pair<Integer, IValue>, String> valueColumn;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<Integer> programStateIdentifiersListView;

    @FXML
    private TableView<Pair<String, IValue>> symbolTableView;

    @FXML
    private TableColumn<Pair<String, IValue>, String> variableNameColumn;

    @FXML
    private TableColumn<Pair<String, IValue>, String> variableValueColumn;

    @FXML
    private ListView<String> executionStackListView;

    @FXML
    private Button runOneStepButton;

    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    @FXML
    public void initialize() {
        programStateIdentifiersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        variableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        variableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
    }

    private ProgState getCurrentProgState() {

        if (controller.getRepo().getProgramsList().isEmpty())
            return null;
        else {
            int currentId = programStateIdentifiersListView.getSelectionModel().getSelectedIndex();
            if (currentId == -1)
                return controller.getRepo().getProgramsList().getFirst();
            else
                return controller.getRepo().getProgramsList().get(currentId);
        }
    }

    private void populate() {
        populateHeapTableView();
        populateOutputListView();
        populateFileTableListView();
        populateProgStateIdentifiersListView();
        populateSymbolTableView();
        populateExecutionStackListView();
    }

    @FXML
    private void changeProgState(MouseEvent event) {
        populate();
    }

    private void populateNumberOfProgStatesTextField() {
        List<ProgState> programStates = controller.getRepo().getProgramsList();
        numberOfProgramStatesTextField.setText(String.valueOf(programStates.size()));
    }

    private void populateHeapTableView() {
        ProgState programState = getCurrentProgState();
        MyIHeap heap = Objects.requireNonNull(programState).getHeap();
        ArrayList<Pair<Integer, IValue>> heapEntries = new ArrayList<>();
        for(Map.Entry<Integer, IValue> entry: heap.getContent().entrySet()) {
            heapEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        heapTableView.setItems(FXCollections.observableArrayList(heapEntries));
    }

    private void populateOutputListView() {
        ProgState programState = getCurrentProgState();
        List<String> output = new ArrayList<>();
        List<String> outputList = Objects.requireNonNull(programState).getOutputList().getAll();
        int index;
        for (index = 0; index < outputList.size(); index++){
            output.add(outputList.get(index));
        }
        outputListView.setItems(FXCollections.observableArrayList(output));
    }

    private void populateFileTableListView() {
        ProgState programState = getCurrentProgState();
        List<String> files = new ArrayList<>(Objects.requireNonNull(programState).getFileTable().getKeys().stream()
            .map(StringValue::getValue).toList()
        );
        fileTableListView.setItems(FXCollections.observableList(files));
    }

    private void populateProgStateIdentifiersListView() {
        List<ProgState> programStates = controller.getRepo().getProgramsList();
        List<Integer> idList = programStates.stream().map(ProgState::getId).collect(Collectors.toList());
        programStateIdentifiersListView.setItems(FXCollections.observableList(idList));
        populateNumberOfProgStatesTextField();
    }

    private void populateSymbolTableView() {
        ProgState programState = getCurrentProgState();
        MyIDictionary<String, IValue> symbolTable = Objects.requireNonNull(programState).getSymTable();
        ArrayList<Pair<String, IValue>> symbolTableEntries = new ArrayList<>();
        for (Map.Entry<String, IValue> entry: symbolTable.getDictionary().entrySet()) {
            symbolTableEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        symbolTableView.setItems(FXCollections.observableArrayList(symbolTableEntries));
    }

    private void populateExecutionStackListView() {
        ProgState programState = getCurrentProgState();
        List<String> executionStackToString = new ArrayList<>();
        if (programState != null)
            for (IStatement statement: programState.getStack().getValues()) {
                executionStackToString.add(statement.toString());
            }
        Collections.reverse(executionStackToString);
        executionStackListView.setItems(FXCollections.observableList(executionStackToString));
    }

    @FXML
    private void runOneStep(MouseEvent mouseEvent) throws Exception {
        if(controller==null)
        {
            Alert error=new Alert(Alert.AlertType.ERROR,"No Program Selected!");
            error.show();
            runOneStepButton.setDisable(true);
            return;
        }

        ProgState program_state=getCurrentProgState();
        if(program_state!=null && !program_state.isNotCompleted())
        {
            Alert error=new Alert(Alert.AlertType.ERROR,"Nothing To Execute!");
            error.show();
            return;
        }

        try
        {
            controller.executeOneStepForAll();
            populate();

            if(controller.getProgramsList().isEmpty())
            {
                runOneStepButton.setDisable(true);
            }
        }
        catch (InterruptedException e)
        {
            Alert error=new Alert(Alert.AlertType.ERROR,e.getMessage());
            error.show();
        }
    }
}
