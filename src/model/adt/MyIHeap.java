package model.adt;

import exceptions.MyADTException;
import model.value.IValue;

import java.util.Map;

public interface MyIHeap {
    Map<Integer, IValue> getContent();
    void set(Map<Integer, IValue> value);
    public Integer getFreeLocation();
    public boolean contains(Integer address);
    Integer allocate(IValue value);
    void update(Integer pos, IValue value) throws MyADTException;
    IValue get(Integer pos) throws MyADTException;
}
