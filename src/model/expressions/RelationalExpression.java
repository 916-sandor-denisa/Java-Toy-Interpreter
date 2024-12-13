package model.expressions;

import exceptions.MyADTException;
import exceptions.MyExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.BoolType;
import model.type.IType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

import java.beans.Expression;

public class RelationalExpression implements IExpression {

    private IExpression e1, e2;
    String operator;

    public RelationalExpression(IExpression e1, IExpression e2, String operator) {
        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException {
        IType e1Type = e1.typecheck(typeEnv);
        IType e2Type = e2.typecheck(typeEnv);
        if(e1Type.equals(new IntType())){
            if(e2Type.equals(new IntType())){
                return new BoolType();
            }
            else{
                throw new MyExpressionException("Second operand is not an integer");
            }
        }
        else{
            throw new MyExpressionException("First operand is not an integer");
        }
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyExpressionException, MyADTException {
        IValue v1 = e1.evaluate(table,heap);
        IValue v2 = e2.evaluate(table,heap);

        if(!v1.getType().equals(new IntType())) {
            throw new MyExpressionException("The first operand must be an integer\n");
        }
        if(!v2.getType().equals(new IntType())) {
            throw new MyExpressionException("The second operand must be an integer\n");
        }

        int intVal1 = ((IntValue)v1).getValue();
        int intVal2 = ((IntValue)v2).getValue();

        return switch (operator) {
            case "==" -> new BoolValue(intVal1 == intVal2);
            case "!=" -> new BoolValue(intVal1 != intVal2);
            case "<" -> new BoolValue(intVal1 < intVal2);
            case "<=" -> new BoolValue(intVal1 <= intVal2);
            case ">" -> new BoolValue(intVal1 > intVal2);
            case ">=" -> new BoolValue(intVal1 >= intVal2);
            default -> throw new MyExpressionException("Invalid operator\n");
        };
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(this.e1, this.e2, this.operator);
    }

    public String toString() {
        return e1.toString() + " " + operator + "  " + e2.toString();
    }
}
