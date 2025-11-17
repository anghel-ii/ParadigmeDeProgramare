package model.expressions;

import adt.MyIDictionary;
import exceptions.MyException;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.Value;

public final class LogicExp implements Exp {
    private final Exp e1, e2;
    private final String op; // and or

    public LogicExp(String op, Exp e1, Exp e2) {
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        Value v1 = e1.eval(tbl);
        if (!v1.getType().equals(new BoolType()))
            throw new MyException("LogicExp ERROR: First operand type mismatch");
        Value v2 = e2.eval(tbl);
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
    public String toString(){
        return "(" + e1 + " " + op + " " + e2 + ")";
    }
}
