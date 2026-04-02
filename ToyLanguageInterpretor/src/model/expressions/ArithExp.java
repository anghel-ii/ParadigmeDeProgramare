package model.expressions;

import adt.MyIDictionary;
import adt.MyIHeap;
import exceptions.MyException;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class ArithExp implements Exp {
    private final Exp e1, e2;
    private final char op; // + - * /

    public ArithExp(char op, Exp e1, Exp e2) {
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws MyException {
        Value v1 = e1.eval(tbl,hp);
        if(!v1.getType().equals(new IntType()))
            throw new MyException("ArithExp ERROR: First operand type mismatch");
        Value v2 = e2.eval(tbl,hp);
        if(!v2.getType().equals(new IntType()))
            throw new MyException("ArithExp ERROR: Second operand type mismatch");

        int n1 = ((IntValue)v1).getVal();
        int n2 = ((IntValue)v2).getVal();
        return switch (op) {
            case '+' -> new IntValue(n1 + n2);
            case '-' -> new IntValue(n1 - n2);
            case '*' -> new IntValue(n1 * n2);
            case '/' -> {
                if (n2 == 0) throw new MyException("ArithExp ERROR: Cannot divide by zero");
                yield new IntValue(n1 / n2);
            }
            default -> throw new MyException("ArithExp ERROR: Unknown operator");
        };
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1 = e1.typecheck(typeEnv);
        Type typ2 = e2.typecheck(typeEnv);
        if (!typ1.equals(new IntType()))
            throw new MyException("ArithExp ERROR: First operand is not an integer");
        if (!typ2.equals(new IntType()))
            throw new MyException("ArithExp ERROR: Second operand is not an integer");
        return new IntType();
    }

    @Override
    public String toString(){
        return e1 + " " + op + " " + e2;
    }
}
