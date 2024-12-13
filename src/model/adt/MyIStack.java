package model.adt;

import exceptions.MyADTException;
import exceptions.MyEmptyStackException;

public interface MyIStack<T> {
    T pop() throws MyADTException;
    void push(T t);
    boolean isEmpty();
    int size();
    String toString();
}
