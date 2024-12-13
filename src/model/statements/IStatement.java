package model.statements;

import exceptions.MyADTException;
import exceptions.MyStatementException;
import model.adt.MyIDictionary;
import model.state.ProgState;
import model.type.IType;

public interface IStatement {
    MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException, MyStatementException;
    ProgState execute(ProgState state) throws MyStatementException, MyADTException;
    IStatement deepCopy();
}
