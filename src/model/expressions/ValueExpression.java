package model.expressions;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.state.ProgState;
import model.type.IType;
import model.value.IValue;

import java.beans.Expression;

public class ValueExpression implements IExpression {

    private IValue value;
    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) {
        return value.getType();
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap heap) {
        return this.value;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(value);
    }

    public String toString() {
        return this.value.toString();
    }
}
