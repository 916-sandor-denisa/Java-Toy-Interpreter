package model.value;

import model.type.IType;
import model.type.RefType;

public class RefValue implements IValue {

    private int address;
    private IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    public int getAddress() {
        return address;
    }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public boolean equals(IValue other) {
        return other.getType().equals(new RefType(locationType)) && ((RefValue) other).address == address;
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(address, locationType);
    }

    @Override
    public String toString() {
        return String.format("(%d, %s)", address, locationType);
    }
}
