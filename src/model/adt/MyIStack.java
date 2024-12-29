package model.adt;

import exceptions.MyADTException;
import exceptions.MyEmptyStackException;

import java.util.List;

public interface MyIStack<T> {
    T pop() throws MyADTException;
    void push(T t);
    boolean isEmpty();
    int size();
    List<T> getValues();
    String toString();
}
