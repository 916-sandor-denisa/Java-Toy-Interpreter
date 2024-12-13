package model.expressions;

import exceptions.MyADTException;
import exceptions.MyExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class HeapReadingExpression implements IExpression {
    private IExpression expression;

    public HeapReadingExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException {
        IType type = expression.typecheck(typeEnv);
        if(type instanceof RefType) {
            RefType refType = (RefType) type;
            return refType.getInner();
        }
        else{
            throw new MyADTException("The argument is not a RefType");
        }
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyExpressionException, MyADTException {
        IValue value = expression.evaluate(table, heap);
        if(!(value instanceof RefValue)){
            throw new MyExpressionException(expression.toString() + " is not a RefValue");
        }
        int address = ((RefValue)value).getAddress();
        if(!heap.contains(address)){
            throw new MyExpressionException(expression.toString() + " is not in the heap");
        }
        return heap.get(address);
    }

    @Override
    public IExpression deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("HeapRead(%s)", expression);
    }
}
