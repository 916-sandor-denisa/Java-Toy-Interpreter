package model.expressions;

import exceptions.MyADTException;
import exceptions.MyDivisionByZeroException;
import exceptions.MyExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.IType;
import model.value.IValue;

public interface IExpression {

    IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException;
    IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyExpressionException, MyADTException;
    IExpression deepCopy();
}
