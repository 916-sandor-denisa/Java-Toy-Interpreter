package model.type;

import model.value.BoolValue;
import model.value.IValue;

public class BoolType implements IType{
    @Override
    public boolean equals(IType type) {
        return type instanceof BoolType;
    }

    @Override
    public IValue getDefaultValue() {
        return new BoolValue(false);
    }

    public String toString() {
        return "bool";
    }
}
