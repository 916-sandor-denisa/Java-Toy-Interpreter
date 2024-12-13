package model.expressions;

import exceptions.MyADTException;
import exceptions.MyDivisionByZeroException;
import exceptions.MyExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.IType;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;

public class ArithmeticExpression implements IExpression {
    private IExpression left, right;
    private int operator; // 1->addition, 2->subtraction, 3->multiplication, 4->division

    public ArithmeticExpression(IExpression left, IExpression right, int operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException {
        IType leftType = left.typecheck(typeEnv);
        IType rightType = right.typecheck(typeEnv);
        if(leftType.equals(new IntType())){
            if(rightType.equals(new IntType())){
                return new IntType();
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
        IValue v1, v2;
        v1 = left.evaluate(table,heap);

        if(v1.getType().equals(new IntType())){
            v2 = right.evaluate(table,heap);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if(operator == 1){
                    return new IntValue(n1 + n2);
                }
                if(operator == 2){
                    return new IntValue(n1 - n2);
                }
                if(operator == 3){
                    return new IntValue(n1 * n2);
                }
                if(operator == 4){
                    if(n2 == 0){
                        throw new MyExpressionException("Division by zero");
                    }
                    else{
                        return new IntValue(n1 / n2);
                    }
                }
            }
            else{
                throw new MyExpressionException("Second operand is not an integer");
            }
        }
        else{
            throw new MyExpressionException("First operand is not an integer");
        }
        return null;
    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticExpression(left.deepCopy(), right.deepCopy(), operator);
    }

    public String toString(){
        if(operator == 1){
            return left.toString() + " + " + right.toString();
        }
        else if(operator == 2){
            return left.toString() + " - " + right.toString();
        }
        else if(operator == 3){
            return left.toString() + " * " + right.toString();
        }
        else{
            return left.toString() + " / " + right.toString();
        }
    }
}
