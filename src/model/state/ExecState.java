package model.state;

import exceptions.MyADTException;
import exceptions.MyEmptyStackException;
import model.adt.MyStack;
import model.statements.IStatement;

public class ExecState implements IExecStack {

    private MyStack<IStatement> stack;

    public ExecState() {
        stack = new MyStack<>();
    }

    public MyStack<IStatement> getStack() {
        return stack;
    }

    @Override
    public void push(IStatement statement) {
        this.stack.push(statement);
    }

    @Override
    public IStatement pop() throws MyEmptyStackException, MyADTException {
        if ( this.stack.isEmpty() ) {
            throw new MyEmptyStackException("Stack is empty");
        }
        else
            return this.stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
