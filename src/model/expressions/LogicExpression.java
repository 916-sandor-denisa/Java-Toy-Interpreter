package model.expressions;

import exceptions.MyADTException;
import exceptions.MyDivisionByZeroException;
import exceptions.MyExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.BoolType;
import model.type.IType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IValue;


public class LogicExpression implements IExpression {

    private IExpression left, right;
    private int operator; // 1->and, 2->or

    public LogicExpression(IExpression left, IExpression right, int operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyADTException {
        IType leftType = left.typecheck(typeEnv);
        IType rightType = right.typecheck(typeEnv);
        if(leftType.equals(new BoolType())){
            if(rightType.equals(new BoolType())){
                return new BoolType();
            }
            else{
                throw new MyExpressionException("Second operand is not an boolean");
            }
        }
        else{
            throw new MyExpressionException("First operand is not an boolean");
        }
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyExpressionException, MyADTException {
        IValue value1, value2;
        value1 = this.left.evaluate(table,heap);
        if(value1.getType().equals(new BoolType())){
            value2 = this.right.evaluate(table,heap);
            if(value1.getType().equals(new BoolType())){
                BoolValue b1, b2;
                b1 = (BoolValue) value1;
                b2 = (BoolValue) value2;
                boolean bool1, bool2;
                bool1 = b1.getValue();
                bool2 = b2.getValue();
                if(this.operator == 1){
                    return new BoolValue(bool1 && bool2);
                }
                else if(this.operator == 2) {
                    return new BoolValue(bool1 || bool2);
                }
            }
            else{
                throw new MyExpressionException("Second operator is not a boolean");
            }
        }
        else throw new MyExpressionException("First operator is not a boolean");

        return null;
    }

    @Override
    public IExpression deepCopy() {
        return new LogicExpression(left.deepCopy(), right.deepCopy(), operator);
    }

    public String toString() {
        if (this.operator==1)
            return this.left.toString() + "and" + this.right.toString();
        else
            return this.left.toString() + "or" + this.right.toString();
    }
}
