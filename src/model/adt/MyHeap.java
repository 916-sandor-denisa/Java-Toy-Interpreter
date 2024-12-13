package model.adt;

import exceptions.MyADTException;
import model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap{

    private Map<Integer, IValue> map;
    private Integer freeLocation;

    public MyHeap() {
        map = new HashMap<Integer, IValue>();
        freeLocation = 1;
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return map;
    }

    @Override
    public void set(Map<Integer, IValue> value) {
        this.map = value;
    }

    @Override
    public Integer getFreeLocation() {
        return freeLocation;
    }

    @Override
    public boolean contains(Integer address) {
        return map.containsKey(address);
    }

    public Integer newValue() {
        freeLocation += 1;
        if (map.containsKey(freeLocation)) {
            freeLocation += 1;
        }
        return freeLocation;
    }

    @Override
    public Integer allocate(IValue value) {
        map.put(freeLocation, value);
        Integer toReturn = freeLocation;
        freeLocation = newValue();
        return toReturn;
    }

    @Override
    public void update(Integer pos, IValue value) throws MyADTException {
        if(!map.containsKey(pos)){
            throw new MyADTException(String.format("%d is not in the heap.", pos));
        }
        map.put(pos,value);
    }

    @Override
    public IValue get(Integer pos) throws MyADTException {
        if(!map.containsKey(pos)){
            throw new MyADTException(String.format("%d is not in the heap.", pos));
        }
        return map.get(pos);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Heap: ");
        for(Integer key: map.keySet()){
            sb.append(key).append(" -> ").append(map.get(key)).append("\n");
        }
        return sb.toString();
    }
}
