package model.expressions;

import adt.MyIDictionary;
import adt.MyIHeap;
import model.types.Type;
import model.values.Value;

public class ValueExp implements Exp {
    private final Value v;
    public ValueExp(Value v) {
        this.v = v;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) {
        return v;
    }
    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) {
        return v.getType();
    }
    @Override
    public String toString() {
        return v.toString();
    }
}
