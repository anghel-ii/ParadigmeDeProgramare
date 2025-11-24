package model.expressions;


import adt.MyIDictionary;
import exceptions.MyException;
import model.types.BoolType;
import model.types.IntType;
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
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        Value v1 = e1.eval(tbl);
        if (!v1.getType().equals(new IntType()))
            throw new MyException("RelExp ERROR: First operand type mismatch");
        Value v2 = e2.eval(tbl);
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
}
