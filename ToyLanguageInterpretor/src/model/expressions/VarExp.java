package model.expressions;

import adt.MyIDictionary;
import exceptions.MyException;
import model.values.Value;

public final class VarExp implements Exp {
    private final String id;
    public VarExp(String id) {
        this.id = id;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        return tbl.lookup(id);
    }
    @Override
    public String toString(){
        return id;
    }
}
