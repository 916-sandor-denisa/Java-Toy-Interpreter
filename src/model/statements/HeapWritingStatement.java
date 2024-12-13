package model.statements;

import exceptions.MyADTException;
import exceptions.MyStatementException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExpression;
import model.state.ProgState;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class HeapWritingStatement implements IStatement {

    private String variable;
    private IExpression expression;

    public HeapWritingStatement(String variable, IExpression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException, MyStatementException {
        IType typeVar = typeEnv.get(variable);
        IType typeExp = expression.typecheck(typeEnv);
        if (typeVar instanceof RefType referenceType)
            if (typeExp.equals(referenceType.getInner()))
                return typeEnv;
            else
                throw new MyStatementException("Variable and expression have different types");
        else
            throw new MyStatementException("Heap writing statement: variable not of reference type");
    }

    @Override
    public ProgState execute(ProgState state) throws MyStatementException, MyADTException {

        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();

        if(!symTable.contains(variable)) {
            throw new MyStatementException("Variable '" + variable + "' does not exist");
        }

        IValue value = symTable.get(variable);
        if(!(value instanceof RefValue)) {
            throw new MyStatementException("Variable '" + variable + "' is not a reference type");
        }

        RefValue refValue = (RefValue) value;
        IValue evaluated = expression.evaluate(symTable,heap);

        if(!evaluated.getType().equals(refValue.getLocationType())) {
            throw new MyStatementException("Variable '" + variable + "' is not a reference type");
        }
        heap.update(refValue.getAddress(), evaluated);
        state.setHeap(heap);
        return null;
    }

    public String toString() {
        return String.format("WriteHeap(%s, %s)", variable, expression);
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }
}
