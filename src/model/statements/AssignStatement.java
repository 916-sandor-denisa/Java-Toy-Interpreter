package model.statements;

import exceptions.MyADTException;
import exceptions.MyStatementException;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.IExpression;
import model.state.ProgState;
import model.type.IType;
import model.value.IValue;

public class AssignStatement implements IStatement {

    private String id;
    private IExpression expression;

    public AssignStatement(String id, IExpression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException, MyStatementException {
        IType typeVar = typeEnv.get(id);
        IType typeExpr = expression.typecheck(typeEnv);
        if(typeVar.equals(typeExpr)) {
            return typeEnv;
        }
        else{
            throw new MyStatementException("Variable '" + id + "' is not assignable to '" + typeExpr + "'");
        }
    }

    @Override
    public ProgState execute(ProgState state) throws MyStatementException, MyADTException {
        MyIStack<IStatement> stack = state.getStack();
        MyIDictionary<String, IValue> symTable = state.getSymTable();

        if(symTable.contains(this.id)){
            IValue val = expression.evaluate(symTable,state.getHeap());
            IType type = symTable.get(this.id).getType();
            if(val.getType().equals(type)){
                symTable.put(this.id, val);
            }
            else{
                throw new MyStatementException("Declared type of variable " + this.id + "and type of the assigned expression do not match");
            }
        }
        else{
            throw new MyStatementException("Variable " + this.id + " not found");
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(this.id, expression.deepCopy());
    }

    @Override
    public String toString() {
        return this.id + " = " + expression.toString();
    }
}
