package model.statements;

import exceptions.MyADTException;
import exceptions.MyStatementException;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.IExpression;
import model.state.ProgState;
import model.type.BoolType;
import model.type.IType;
import model.value.BoolValue;
import model.value.IValue;

public class WhileStatement implements IStatement{

    private IStatement statement;
    private IExpression expression;

    public WhileStatement(IStatement statement, IExpression expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException, MyStatementException {
        IType expressionType = this.expression.typecheck(typeEnv);
        if (expressionType.equals(new BoolType()))
        {
            this.statement.typecheck(typeEnv.clone());
            return typeEnv;
        }
        throw new MyStatementException("While: condition not of type bool");
    }

    @Override
    public ProgState execute(ProgState state) throws MyStatementException, MyADTException {

        IValue value = expression.evaluate(state.getSymTable(), state.getHeap());
        if(!value.getType().equals(new BoolType())){
            throw new MyADTException("The expression is not a boolean");
        }
        BoolValue boolValue = (BoolValue)value;
        MyIStack<IStatement> execStack = state.getStack();

        if(boolValue.getValue()){
            execStack.push(this);
            execStack.push(statement);
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(statement.deepCopy(), expression.deepCopy());
    }

    public String toString() {
        return String.format("While(%s) {%s} ", this.expression, this.statement);
    }
}
