package model.statements;

import exceptions.MyADTException;
import exceptions.MyStatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.ProgState;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class HeapAllocationStatement implements IStatement {

    private String variable;
    private IExpression expression;

    public HeapAllocationStatement(String variable, IExpression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException, MyStatementException {
        IType typeVariable = typeEnv.get(variable);
        IType typeExpression = expression.typecheck(typeEnv);
        if (typeVariable.equals(new RefType(typeExpression))) {
            return typeEnv;
        }
        else{
            throw new MyStatementException("Heap allocation: Right hand side and left hand side have different types");
        }
    }

    @Override
    public ProgState execute(ProgState state) throws MyStatementException, MyADTException {
        var symTable = state.getSymTable();
        var heap = state.getHeap();
        if(!symTable.contains(variable)) {
            throw new MyStatementException("Variable " + variable + " not found");
        }
        IValue variableValue = symTable.get(variable);
        if(!(variableValue.getType() instanceof RefType)) {
            throw new MyStatementException("Variable " + variable + " is not a reference type");
        }

        IValue value = expression.evaluate(symTable, heap);
        if(!(value.getType().equals(((RefValue)variableValue).getLocationType()))) {
            throw new MyStatementException(String.format("%s is not of type %s.", variable, value.getType()));
        }

        int address = heap.allocate(value);
        symTable.put(variable, new RefValue(address,value.getType()));
        return null;
    }

    public String toString() {
        return String.format("New(%s, %s)", variable, expression);
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }
}
