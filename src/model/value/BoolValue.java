package model.value;

import model.type.BoolType;
import model.type.IType;

public class BoolValue implements IValue {

    private boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(IValue other) {
        return other.getType() instanceof BoolType && this.value == ((BoolValue)other).getValue();
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(value);
    }

    public String toString(){
        return Boolean.toString(value);
    }

}
