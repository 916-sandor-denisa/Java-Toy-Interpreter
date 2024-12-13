package model.state;
import exceptions.MyADTException;
import model.adt.*;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public class ProgState {
    private MyIStack<IStatement> execStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<String> outputList;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private IStatement originalStatement;
    private MyIHeap heap;
    static int nextId = 0;
    private int id;

    public ProgState(MyIStack<IStatement> execStack, MyIDictionary<String, IValue> symTable, MyIList<String> outputList, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap heap) {
        this.execStack = execStack;
        this.symTable = symTable;
        this.outputList = outputList;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = this.getNextId();
    }

    public ProgState(IStatement initState, MyIStack<IStatement> execStack, MyIDictionary<String, IValue> symTable, MyIList<String> outputList, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap heap) {
        this.execStack = execStack;
        this.symTable = symTable;
        this.outputList = outputList;
        this.originalStatement = initState.deepCopy();
        this.fileTable = fileTable;
        this.heap = heap;
        this.execStack.push(initState);
        this.id = this.getNextId();
    }

    private int getNextId() {
        return ++nextId;
    }

    public MyIStack<IStatement> getStack() { return execStack;}
    public void setStack(MyStack<IStatement> execStack) { this.execStack = execStack; }

    public MyIDictionary<String, IValue> getSymTable() {return symTable;}
    public void setSymTable(MyDictionary<String, IValue> symTable) {this.symTable = symTable;}

    public MyIList<String> getOutputList() {return outputList;}
    public void setOutputList(MyList<String> outputList) {this.outputList = outputList;}

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {return fileTable;}
    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {this.fileTable = fileTable;}

    public MyIHeap getHeap() {return heap;}
    public void setHeap(MyIHeap heap) {this.heap = heap;}

    public boolean isNotCompleted(){
        return !execStack.isEmpty();
    }

    public ProgState oneStep() throws MyADTException {
        if(this.execStack.isEmpty()){
            throw new MyADTException("Stack is empty");
        }
        IStatement currStatement = execStack.pop();
        return currStatement.execute(this);
    }

    public String toStringFile(){
        StringBuilder s = new StringBuilder("File table: ");
        if(fileTable.getKeys().isEmpty()){
            return s.append("\n").toString();
        }
        for (StringValue v: fileTable.getKeys()){
            s.append(v.getValue()).append("\n");
        }
        return s.toString();
    }

    @Override
    public String toString() {
        return "Program ID: " + this.id + "\n" + execStack.toString()+ symTable.toString()
                + outputList.toString()+ this.toStringFile() + heap.toString() +"\n";
    }

}
