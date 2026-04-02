package model.expressions;

import adt.MyIDictionary;
import adt.MyIHeap;
import exceptions.MyException;
import model.types.Type;
import model.values.Value;

public class VarExp implements Exp {
    private final String id;
    public VarExp(String id) {
        this.id = id;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws MyException {
        return tbl.lookup(id);
    }
    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }
    @Override
    public String toString(){
        return id;
    }
}
