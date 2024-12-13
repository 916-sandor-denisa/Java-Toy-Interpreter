package view;

import controller.Controller;
import exceptions.MyADTException;
import exceptions.MyStatementException;
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
import view.command.ExitCommand;
import view.command.RunExample;

public class Interpreter {
    public static void main(String[] args) throws MyADTException {

        TextMenu menu = new TextMenu();

        //int v; v = 2; Print(v);
        IStatement ex1 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VarExpression("v"))));

        try {
            ex1.typecheck(new MyDictionary<>());
            ProgState prg1 = new ProgState(ex1, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap());
            IRepo repo1 = new Repo("log1.txt");
            repo1.addProgState(prg1);
            Controller controller1 = new Controller(repo1);
            menu.addCommand(new RunExample("1", ex1.toString(), controller1));
        }
        catch (MyStatementException e) {
            System.out.println("1. " + e.getMessage());
        }

        //int a; int b; a=2+3*5; b=a+1; Print(b)
        IStatement ex2 = new CompStatement(new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("b", new IntType()),
                        new CompStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)), new
                                ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), 3), 1)),
                                new CompStatement(new AssignStatement("b", new ArithmeticExpression(new VarExpression("a"), new ValueExpression(new
                                        IntValue(1)), 1)), new PrintStatement(new VarExpression("b"))))));

        try{
            ex2.typecheck(new MyDictionary<>());
            ProgState prg2 = new ProgState(ex2, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap());
            IRepo repo2 = new Repo("log2.txt");
            repo2.addProgState(prg2);
            Controller controller2 = new Controller(repo2);
            menu.addCommand(new RunExample("2", ex2.toString(), controller2));
        }
        catch (MyStatementException e) {
            System.out.println("2. " + e.getMessage());
        }

        //bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v) 
        IStatement ex3 = new CompStatement(new VarDeclStatement("a", new BoolType()),
                new CompStatement(new VarDeclStatement("v", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new IfStatement(new VarExpression("a"),
                                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VarExpression("v"))))));

        try{
            ex3.typecheck(new MyDictionary<>());
            ProgState prg3 = new ProgState(ex3, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap());
            IRepo repo3 = new Repo("log3.txt");
            repo3.addProgState(prg3);
            Controller controller3 = new Controller(repo3);
            menu.addCommand(new RunExample("3", ex3.toString(), controller3));
        }catch (MyStatementException e) {
            System.out.println("3. " + e.getMessage());
        }

        //int a; int b; a=2+5; b=a/7; Print(b);
        IStatement ex4 = new CompStatement(new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("b", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new IntValue(14))),
                                new CompStatement(new AssignStatement("b", new ArithmeticExpression(new VarExpression("a"), new ValueExpression(new
                                        IntValue(7)), 4)), new PrintStatement(new VarExpression("b"))))));


        try{
            ex4.typecheck(new MyDictionary<>());
            ProgState prg4 = new ProgState(ex4, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap());
            IRepo repo4 = new Repo("log4.txt");
            repo4.addProgState(prg4);
            Controller controller4 = new Controller(repo4);
            menu.addCommand(new RunExample("4", ex4.toString(), controller4));
        }catch (MyStatementException e) {
            System.out.println("4. " + e.getMessage());
        }

        /* string varFile; varFile = "test.in"; OpenReadFile("varFile"); int var; ReadFile("varFile", "var"); Print(var);
           ReadFile("varFile", "var"); Print(var); CloseReadFile("varFile")  */
        IStatement ex5 = new CompStatement(new VarDeclStatement("varFile", new StringType()),
                new CompStatement(new AssignStatement("varFile", new ValueExpression(new StringValue("test.in"))),
                        new CompStatement(new OpenReadFileStatement(new VarExpression("varFile")),
                                new CompStatement(new VarDeclStatement("var", new IntType()),
                                        new CompStatement(new ReadFileStatement("var", new VarExpression("varFile")),
                                                new CompStatement(new PrintStatement(new VarExpression("var")),
                                                        new CompStatement(new ReadFileStatement("var", new VarExpression("varFile")),
                                                                new CompStatement(new PrintStatement(new VarExpression("var")),
                                                                        new CloseReadFileStatement(new VarExpression("varFile"))))))))));

        try{
            ex5.typecheck(new MyDictionary<>());
            ProgState prg5 = new ProgState(ex5, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap());
            IRepo repo5 = new Repo("log5.txt");
            repo5.addProgState(prg5);
            Controller controller5 = new Controller(repo5);
            menu.addCommand(new RunExample("5", ex5.toString(), controller5));
        }catch (MyStatementException e) {
            System.out.println("5. " + e.getMessage());
        }

        /* int a; int b; a=5; b=7; (If a>b Then Print(a) Else Print(b)) */
        IStatement ex6 = new CompStatement(new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("b", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new IntValue(5))),
                                new CompStatement(new AssignStatement("b", new ValueExpression(new IntValue(7))),
                                        new IfStatement(new RelationalExpression(new VarExpression("a"),
                                                new VarExpression("b"), ">"), new PrintStatement(new VarExpression("a")),
                                                new PrintStatement(new VarExpression("b")))))));

        try{
            ex6.typecheck(new MyDictionary<>());
            ProgState prg6 = new ProgState(ex6, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap());
            IRepo repo6 = new Repo("log6.txt");
            repo6.addProgState(prg6);
            Controller controller6 = new Controller(repo6);
            menu.addCommand(new RunExample("6", ex6.toString(), controller6));
        }catch (MyStatementException e) {
            System.out.println("6. " + e.getMessage());
        }

        /* int v; v=4; (while (v>0) print(v); v=v-1); print(v) */
        IStatement ex7 = new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                new CompStatement(new WhileStatement(new CompStatement(new PrintStatement(new VarExpression("v")),
                        new AssignStatement("v", new ArithmeticExpression(new VarExpression("v"), new ValueExpression(new IntValue(1)), 2))),
                        new RelationalExpression(new VarExpression("v"), new ValueExpression(new IntValue(0)), ">")), new PrintStatement(new VarExpression("v")))));

        try{
            ex7.typecheck(new MyDictionary<>());
            ProgState prg7 = new ProgState(ex7, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap());
            IRepo repo7 = new Repo("log7.txt");
            repo7.addProgState(prg7);
            Controller controller7 = new Controller(repo7);
            menu.addCommand(new RunExample("7", ex7.toString(), controller7));
        }catch (MyStatementException e) {
            System.out.println("7. " + e.getMessage());
        }

        /* Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a) */
        IStatement ex8 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())), new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))), new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))), new CompStatement(new HeapAllocationStatement("a", new VarExpression("v")), new CompStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new VarExpression("a")))))));

        try{
            ex8.typecheck(new MyDictionary<>());
            ProgState prg8 = new ProgState(ex8, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap());
            IRepo repo8 = new Repo("log8.txt");
            repo8.addProgState(prg8);
            Controller controller8 = new Controller(repo8);
            menu.addCommand(new RunExample("8", ex8.toString(), controller8));
        }catch (MyStatementException e) {
            System.out.println("8. " + e.getMessage());
        }

        /* Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a))+5) */
        IStatement ex9 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new HeapAllocationStatement("a", new VarExpression("v")),
                                        new CompStatement(new PrintStatement(new HeapReadingExpression(new VarExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression(new HeapReadingExpression(new HeapReadingExpression(new VarExpression("a"))), new ValueExpression(new IntValue(5)), 1)))))));

        try{
            ex9.typecheck(new MyDictionary<>());
            ProgState prg9 = new ProgState(ex9, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap());
            IRepo repo9 = new Repo("log9.txt");
            repo9.addProgState(prg9);
            Controller controller9 = new Controller(repo9);
            menu.addCommand(new RunExample("9", ex9.toString(), controller9));
        }catch (MyStatementException e) {
            System.out.println("9. " + e.getMessage());
        }

        /*Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a))) */
        IStatement ex10 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new HeapAllocationStatement("a", new VarExpression("v")),
                                        new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VarExpression("a")))))))));

        try{
            ex10.typecheck(new MyDictionary<>());
            ProgState prg10 = new ProgState(ex10, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap());
            IRepo repo10 = new Repo("log10.txt");
            repo10.addProgState(prg10);
            Controller controller10 = new Controller(repo10);
            menu.addCommand(new RunExample("10", ex10.toString(), controller10));
        }catch (MyStatementException e) {
            System.out.println("10. " + e.getMessage());
        }

        /* int v; Ref int a; v=10; new(a,22); fork(wH(a,30); v=32; print(v); print(rH(a))); print(v); print(rH(a)) */
        IStatement ex11 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new VarDeclStatement("a", new RefType(new IntType())),
                        new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompStatement(new ForkStatement(new CompStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new HeapReadingExpression(new VarExpression("a"))))))),
                                                new CompStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new HeapReadingExpression(new VarExpression("a")))))))));

        try{
            ex11.typecheck(new MyDictionary<>());
            ProgState prg11 = new ProgState(ex11, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap());
            IRepo repo11 = new Repo("log11.txt");
            repo11.addProgState(prg11);
            Controller controller11 = new Controller(repo11);
            menu.addCommand(new RunExample("11", ex11.toString(), controller11));
        }catch (MyStatementException e){
            System.out.println("11. " + e.getMessage());
        }

        // Reference int a; int counter; while (counter < 10) { fork(fork(new(a, counter);print(readHeap(a));))counter++;}
        IStatement ex12 = new CompStatement(
                new VarDeclStatement("a", new RefType(new IntType())),
                new CompStatement(new VarDeclStatement("counter", new IntType()), new WhileStatement(new CompStatement(new ForkStatement(new CompStatement(new HeapAllocationStatement("a", new VarExpression("counter")),
                        new PrintStatement(new HeapReadingExpression(new VarExpression("a"))))),
                        new AssignStatement("counter", new ArithmeticExpression(new VarExpression("counter"), new ValueExpression(new IntValue(1)), 1))),
                        new RelationalExpression(new VarExpression("counter"), new ValueExpression(new IntValue(4)), "<"))));

        try{
            ex12.typecheck(new MyDictionary<>());
            ProgState prg12 = new ProgState(ex12, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap());
            IRepo repo12 = new Repo("log12.txt");
            repo12.addProgState(prg12);
            Controller controller12 = new Controller(repo12);
            menu.addCommand(new RunExample("12", ex12.toString(), controller12));
        }catch (MyStatementException e){
            System.out.println("12. " + e.getMessage());
        }

        menu.addCommand(new ExitCommand("0", "Exit."));
        menu.show();
    }
}
