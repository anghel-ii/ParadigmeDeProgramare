package model.expressions;

import adt.MyIDictionary;
import adt.MyIHeap;
import exceptions.MyException;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class LogicExp implements Exp {
    private final Exp e1, e2;
    private final String op; // and or

    public LogicExp(String op, Exp e1, Exp e2) {
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws MyException {
        Value v1 = e1.eval(tbl,hp);
        if (!v1.getType().equals(new BoolType()))
            throw new MyException("LogicExp ERROR: First operand type mismatch");
        Value v2 = e2.eval(tbl,hp);
        if (!v2.getType().equals(new BoolType()))
            throw new MyException("LogicExp ERROR: Second operand type mismatch");

        boolean b1 = ((BoolValue)v1).getVal();
        boolean b2 = ((BoolValue)v2).getVal();
        return switch (op) {
            case "and" -> new BoolValue(b1 && b2);
            case "or" -> new BoolValue(b1 || b2);
            default -> throw new MyException("LogicExp ERROR: Unknown operation");
        };
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1 = e1.typecheck(typeEnv);
        Type typ2 = e2.typecheck(typeEnv);
        if (!typ1.equals(new BoolType()))
            throw new MyException("LogicExp ERROR: First operand is not boolean");
        if (!typ2.equals(new BoolType()))
            throw new MyException("LogicExp ERROR: Second operand is not boolean");
        return new BoolType();
    }

    @Override
    public String toString(){
        return "(" + e1 + " " + op + " " + e2 + ")";
    }
}
