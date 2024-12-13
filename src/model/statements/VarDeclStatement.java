package model.statements;

import exceptions.MyADTException;
import exceptions.MyStatementException;
import model.adt.MyIDictionary;
import model.state.ProgState;
import model.type.IType;

public class VarDeclStatement implements IStatement {
    private String name;
    private IType type;

    public VarDeclStatement(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException, MyStatementException {
        typeEnv.insert(name, type);
        return typeEnv;
    }

    @Override
    public ProgState execute(ProgState state) {
        if(state.getSymTable().contains(name)) {
            throw new MyStatementException("Variable " + name + " is already declared");
        }
        state.getSymTable().insert(name, this.type.getDefaultValue());
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VarDeclStatement(this.name, this.type);
    }

    @Override
    public String toString() {
        return String.format(name, type.toString());
    }
}
