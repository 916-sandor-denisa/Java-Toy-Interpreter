package model.type;

import model.value.IValue;
import model.value.IntValue;

public class IntType implements IType {

    @Override
    public boolean equals(IType type) {
        return type instanceof IntType;
    }

    @Override
    public IValue getDefaultValue() {
        return new IntValue(0);
    }

    public String toString() {
        return "int";
    }
}
