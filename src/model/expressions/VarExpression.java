package model.expressions;

import exceptions.MyADTException;
import exceptions.MyExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.IType;
import model.value.IValue;

public class VarExpression implements IExpression {

    private String varName;

    public VarExpression(String varName) {
        this.varName = varName;
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException {
        return typeEnv.get(varName);
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyExpressionException, MyADTException {
        return table.get(varName);
    }

    @Override
    public IExpression deepCopy() {
        return new VarExpression(varName);
    }

    @Override
    public String toString() {
        return varName;
    }
}
