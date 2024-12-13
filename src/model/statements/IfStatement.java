package model.statements;

import exceptions.MyADTException;
import exceptions.MyStatementException;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.ProgState;
import model.type.BoolType;
import model.type.IType;
import model.value.BoolValue;
import model.value.IValue;

import java.util.Map;

public class IfStatement implements IStatement {

    private IExpression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException, MyStatementException {
        IType typeExpr = expression.typecheck(typeEnv);
        if(typeExpr.equals(new BoolType())) {
            thenStatement.typecheck(typeEnv.clone());
            elseStatement.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else{
            throw new MyStatementException("The condition of IF does not have a bool type");
        }
    }

    @Override
    public ProgState execute(ProgState state) throws MyStatementException, MyADTException {
        IValue val = expression.evaluate(state.getSymTable(), state.getHeap());
        if(!val.getType().equals(new BoolType())){
            throw new MyStatementException("The condition should be a boolean!\n");
        }
        if(((BoolValue) val).getValue()){
            state.getStack().push(thenStatement);
        }
        else{
            state.getStack().push(elseStatement);
        }
        return null;
    }

    public IStatement deepCopy() {
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "IF(" + expression.toString() + ") THEN {" + thenStatement.toString() + "} ELSE {" + elseStatement.toString() + "}";
    }
}
