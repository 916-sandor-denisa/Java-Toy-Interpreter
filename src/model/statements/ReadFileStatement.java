package model.statements;

import exceptions.*;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.ProgState;
import model.type.IType;
import model.type.IntType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement{

    private String varName;
    private IExpression expression;

    public ReadFileStatement(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException, MyStatementException {
        IType typExp = this.expression.typecheck(typeEnv);
        IType typeVar = typeEnv.get(this.varName);
        if (typExp.equals(new StringType()))
            if (typeVar.equals(new IntType()))
                return typeEnv;
            else
                throw new MyStatementException("Variable not of type int");
        else
            throw new MyStatementException("Read file statement: expression not of type string");
    }

    @Override
    public ProgState execute(ProgState state) throws MyStatementException, MyExpressionException, MyADTException {
        if(!state.getSymTable().get(varName).getType().equals(new IntType())) {
            throw new MyStatementException("Variable " + varName + " does not have integer type");
        }
        IValue eval = expression.evaluate(state.getSymTable(), state.getHeap());
        if(!eval.getType().equals(new StringType())) {
            throw new MyStatementException("The evaluated expression does not have string type");
        }
        BufferedReader br = state.getFileTable().get((StringValue)eval);
        try {
            String line = br.readLine();
            if(line == null) {
                line = "0";
            }
            int intFileLine = Integer.parseInt(line);
            state.getSymTable().insert(varName, new IntValue(intFileLine));
        } catch (IOException e) {
            throw new MyStatementException(e.getMessage());
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    public String toString() {
        return String.format("ReadFile (%s, %s)", expression, varName);
    }

}
