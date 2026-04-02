package model.expressions;


import adt.MyIDictionary;
import adt.MyIHeap;
import exceptions.MyException;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelExp implements Exp {
    private final Exp e1, e2;
    private final String op;

    public RelExp(String op, Exp e1, Exp e2) {
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws MyException {
        Value v1 = e1.eval(tbl,hp);
        if (!v1.getType().equals(new IntType()))
            throw new MyException("RelExp ERROR: First operand type mismatch");
        Value v2 = e2.eval(tbl,hp);
        if (!v2.getType().equals(new IntType()))
            throw new MyException("RelExp ERROR: Second operand type mismatch");

        int i1 = ((IntValue)v1).getVal();
        int i2 = ((IntValue)v2).getVal();

        return switch (op) {
            case "<" -> new BoolValue(i1 < i2);
            case ">" -> new BoolValue(i1 > i2);
            case "==" -> new BoolValue(i1 == i2);
            case "!=" -> new BoolValue(i1 != i2);
            case ">="  -> new BoolValue(i1 >= i2);
            case "<="  -> new BoolValue(i1 <= i2);
            default -> throw new MyException("RelExp ERROR: Unknown operation");
        };
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1 = e1.typecheck(typeEnv);
        Type typ2 = e2.typecheck(typeEnv);
        if (!typ1.equals(new IntType()))
            throw new MyException("RelExp ERROR: First operand is not an integer");
        if (!typ2.equals(new IntType()))
            throw new MyException("RelExp ERROR: Second operand is not an integer");
        return new BoolType();
    }

    @Override
    public String toString() {
        return e1 + " " + op + " " + e2;
    }
}
