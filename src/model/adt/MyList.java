package model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {

    private List<T> list;

    public MyList() {
        list = new ArrayList<>();
    }

    @Override
    public void add(T element) {
        this.list.add(element);
    }

    @Override
    public List<T> getAll() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public void remove(T element) {
        list.remove(element);
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();

        for(T el: list){
            str.append(el).append("\n");
        }

        return "My output list contains:\n" + str;
    }
}
