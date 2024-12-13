package model.value;

import model.type.IType;
import model.type.StringType;

public class StringValue implements IValue {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public boolean equals(IValue other) {
        return other instanceof StringValue && ((StringValue) other).value.equals(this.value);
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(this.value);
    }

    public String getValue() {
        return String.valueOf(value);
    }

    public String toString() {
        return value;
    }
}
