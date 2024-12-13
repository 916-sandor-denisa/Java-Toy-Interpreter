package model.statements;

import exceptions.MyADTException;
import exceptions.MyStatementException;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.state.ProgState;
import model.type.IType;

public class CompStatement implements IStatement {
    private IStatement firstStatement;
    private IStatement secondStatement;

    public CompStatement(IStatement firstStatement, IStatement secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException, MyStatementException {
        return secondStatement.typecheck(firstStatement.typecheck(typeEnv));
    }

    @Override
    public ProgState execute(ProgState state) {
        MyIStack<IStatement> stack = state.getStack();
        stack.push(secondStatement);
        stack.push(firstStatement);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CompStatement(firstStatement.deepCopy(), secondStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "(" + firstStatement.toString() + ";" + secondStatement.toString() + ")";
    }
}
