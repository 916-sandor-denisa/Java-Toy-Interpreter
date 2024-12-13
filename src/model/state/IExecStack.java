package model.state;

import exceptions.MyADTException;
import exceptions.MyEmptyStackException;
import model.statements.IStatement;


public interface IExecStack {
    public void push(IStatement statement);
    public IStatement pop() throws MyEmptyStackException, MyADTException;
    public boolean isEmpty();
    public int size();
}
