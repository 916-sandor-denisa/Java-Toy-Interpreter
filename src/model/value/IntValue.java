package model.value;

import model.type.IType;
import model.type.IntType;

public class IntValue implements IValue {

    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(IValue other) {
        return other.getType() instanceof IntType && ((IntValue)other).getValue() == this.getValue();
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(value);
    }

    public String toString() {
        return Integer.toString(value);
    }
}
