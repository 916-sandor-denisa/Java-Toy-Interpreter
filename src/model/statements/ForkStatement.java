package model.statements;

import exceptions.MyADTException;
import exceptions.MyStatementException;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.adt.MyStack;
import model.state.ProgState;
import model.type.IType;
import model.value.IValue;

import java.util.Map;

public class ForkStatement implements IStatement {

    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException, MyStatementException {
        statement.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public ProgState execute(ProgState state)
    {
        MyIStack<IStatement> newStack = new MyStack<>();
        newStack.push(statement);

        MyIDictionary<String, IValue> newSymTable = new MyDictionary<>();
        for (Map.Entry<String, IValue> entry: state.getSymTable().getDictionary().entrySet()) {
            newSymTable.insert(entry.getKey(), entry.getValue().deepCopy());
        }

        return new ProgState(newStack, newSymTable, state.getOutputList(), state.getFileTable(), state.getHeap());
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("Fork(%s)", statement.toString());
    }
}
