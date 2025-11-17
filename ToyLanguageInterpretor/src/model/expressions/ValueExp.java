package model.expressions;

import adt.MyIDictionary;
import model.values.Value;

public final class ValueExp implements Exp {
    private final Value v;
    public ValueExp(Value v) {
        this.v = v;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl) {
        return v;
    }
    @Override
    public String toString() {
        return v.toString();
    }
}
