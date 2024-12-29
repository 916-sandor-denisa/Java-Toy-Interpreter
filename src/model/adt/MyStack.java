package model.adt;

import exceptions.MyADTException;

import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {

    private Stack<T> stack;

    public MyStack() {
        stack = new Stack<>();
    }

    @Override
    public T pop() throws MyADTException {
        if(stack.isEmpty()) {
            throw new MyADTException("Stack is empty\n");
        }
        return this.stack.pop();
    }

    @Override
    public void push(T t){
        this.stack.push(t);
    }

    @Override
    public boolean isEmpty(){
        return this.stack.isEmpty();
    }

    @Override
    public int size(){
        return this.stack.size();
    }

    @Override
    public List<T> getValues() {
        return this.stack.subList(0,this.stack.size());
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for(T el : this.stack){
            str.append(el);
            str.append("\n");
        }

        return "My execution stack contains:\n" + str;
    }
}
