package model.type;

import model.value.IValue;
import model.value.RefValue;

public class RefType implements IType {

    private IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }

    @Override
    public boolean equals(IType another) {
        return another instanceof RefType && inner.equals(((RefType) another).getInner());
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public IValue getDefaultValue() {
        return new RefValue(0,inner);
    }

    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }
}
