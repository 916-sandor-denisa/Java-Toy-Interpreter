package model.statements;

import exceptions.MyADTException;
import exceptions.MyStatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.ProgState;
import model.type.IType;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenReadFileStatement implements IStatement {

    private IExpression expression;

    public OpenReadFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException, MyStatementException {
        IType type = this.expression.typecheck(typeEnv);
        if (type.equals(new StringType()))
            return typeEnv;
        throw new MyStatementException("Open statement: expression not of type string");
    }

    @Override
    public ProgState execute(ProgState state) throws MyStatementException, MyADTException {
        IValue eval = expression.evaluate(state.getSymTable(), state.getHeap());
        if(!eval.getType().equals(new StringType())) {
            throw new MyStatementException("The evaluated expression does not have string type");
        }

        StringValue fileName = (StringValue) eval;
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        if(fileTable.contains(fileName)){
            throw new MyStatementException("The file is already open");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader((fileName).getValue()));
            fileTable.insert((StringValue) eval, br);
        } catch (IOException e) {
            throw new MyStatementException(e.getMessage());
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("OpenReadFile (%s)", expression);
    }
}
