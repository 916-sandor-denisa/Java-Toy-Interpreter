package GUI;

import controller.Controller;
import exceptions.MyADTException;
import exceptions.MyExpressionException;
import exceptions.MyStatementException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import model.adt.MyDictionary;
import model.adt.MyHeap;
import model.adt.MyList;
import model.adt.MyStack;
import model.expressions.*;
import model.state.ProgState;
import model.statements.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import repo.IRepo;
import repo.Repo;
import view.command.RunExample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class SelectController {

    private MainController programExecutor;

    public void setProgramExecutor(MainController programExecutor) {
        this.programExecutor = programExecutor;
    }
    @FXML
    private ListView<IStatement> programsListView;

    @FXML
    private Button displayButton;

    @FXML
    public void initialize() {
        programsListView.setItems(getAllStatements());
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private ObservableList<IStatement> getAllStatements() {
        List<IStatement> allStatements = new ArrayList<>();

        IStatement ex1 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VarExpression("v"))));

        allStatements.add(ex1);

        IStatement ex2 = new CompStatement(new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("b", new IntType()),
                        new CompStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)), new
                                ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), 3), 1)),
                                new CompStatement(new AssignStatement("b", new ArithmeticExpression(new VarExpression("a"), new ValueExpression(new
                                        IntValue(1)), 1)), new PrintStatement(new VarExpression("b"))))));

        allStatements.add(ex2);

        IStatement ex3 = new CompStatement(new VarDeclStatement("a", new BoolType()),
                new CompStatement(new VarDeclStatement("v", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new IfStatement(new VarExpression("a"),
                                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VarExpression("v"))))));

        allStatements.add(ex3);

        IStatement ex4 = new CompStatement(new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("b", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new IntValue(14))),
                                new CompStatement(new AssignStatement("b", new ArithmeticExpression(new VarExpression("a"), new ValueExpression(new
                                        IntValue(7)), 4)), new PrintStatement(new VarExpression("b"))))));

        allStatements.add(ex4);

        IStatement ex5 = new CompStatement(new VarDeclStatement("varFile", new StringType()),
                new CompStatement(new AssignStatement("varFile", new ValueExpression(new StringValue("test.in"))),
                        new CompStatement(new OpenReadFileStatement(new VarExpression("varFile")),
                                new CompStatement(new VarDeclStatement("var", new IntType()),
                                        new CompStatement(new ReadFileStatement("var", new VarExpression("varFile")),
                                                new CompStatement(new PrintStatement(new VarExpression("var")),
                                                        new CompStatement(new ReadFileStatement("var", new VarExpression("varFile")),
                                                                new CompStatement(new PrintStatement(new VarExpression("var")),
                                                                        new CloseReadFileStatement(new VarExpression("varFile"))))))))));

        allStatements.add(ex5);

        IStatement ex6 = new CompStatement(new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("b", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new IntValue(5))),
                                new CompStatement(new AssignStatement("b", new ValueExpression(new IntValue(7))),
                                        new IfStatement(new RelationalExpression(new VarExpression("a"),
                                                new VarExpression("b"), ">"), new PrintStatement(new VarExpression("a")),
                                                new PrintStatement(new VarExpression("b")))))));

        allStatements.add(ex6);

        IStatement ex7 = new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                new CompStatement(new WhileStatement(new CompStatement(new PrintStatement(new VarExpression("v")),
                        new AssignStatement("v", new ArithmeticExpression(new VarExpression("v"), new ValueExpression(new IntValue(1)), 2))),
                        new RelationalExpression(new VarExpression("v"), new ValueExpression(new IntValue(0)), ">")), new PrintStatement(new VarExpression("v")))));

        allStatements.add(ex7);

        IStatement ex8 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())), new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))), new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))), new CompStatement(new HeapAllocationStatement("a", new VarExpression("v")), new CompStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new VarExpression("a")))))));

        allStatements.add(ex8);

        IStatement ex9 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new HeapAllocationStatement("a", new VarExpression("v")),
                                        new CompStatement(new PrintStatement(new HeapReadingExpression(new VarExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression(new HeapReadingExpression(new HeapReadingExpression(new VarExpression("a"))), new ValueExpression(new IntValue(5)), 1)))))));
        allStatements.add(ex9);

        IStatement ex10 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new HeapAllocationStatement("a", new VarExpression("v")),
                                        new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VarExpression("a")))))))));

        allStatements.add(ex10);

        IStatement ex11 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new VarDeclStatement("a", new RefType(new IntType())),
                        new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompStatement(new ForkStatement(new CompStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new HeapReadingExpression(new VarExpression("a"))))))),
                                                new CompStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new HeapReadingExpression(new VarExpression("a")))))))));

        allStatements.add(ex11);

        IStatement ex12 = new CompStatement(
                new VarDeclStatement("a", new RefType(new IntType())),
                new CompStatement(new VarDeclStatement("counter", new IntType()), new WhileStatement(new CompStatement(new ForkStatement(new CompStatement(new HeapAllocationStatement("a", new VarExpression("counter")),
                        new PrintStatement(new HeapReadingExpression(new VarExpression("a"))))),
                        new AssignStatement("counter", new ArithmeticExpression(new VarExpression("counter"), new ValueExpression(new IntValue(1)), 1))),
                        new RelationalExpression(new VarExpression("counter"), new ValueExpression(new IntValue(4)), "<"))));

        allStatements.add(ex12);

        return FXCollections.observableArrayList(allStatements);
    }

    @FXML
    private void displayProgram(ActionEvent actionEvent){
        IStatement selectedStatement = programsListView.getSelectionModel().getSelectedItem();
        if (selectedStatement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText("No statement selected!");
            alert.showAndWait();
        } else {
            int id = programsListView.getSelectionModel().getSelectedIndex();
            try {
                selectedStatement.typecheck(new MyDictionary<>());
                IRepo repository = new Repo("log" + (id + 1) + ".txt");
                ProgState prg = new ProgState(selectedStatement, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap());
                repository.addProgState(prg);
                Controller controller = new Controller(repository);
                programExecutor.setController(controller);
            } catch (MyStatementException | MyExpressionException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error encountered!");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            } catch (MyADTException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
