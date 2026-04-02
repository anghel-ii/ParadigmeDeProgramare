package model.expressions;

import adt.MyIDictionary;
import adt.MyIHeap;
import exceptions.MyException;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class RHExp implements Exp{
    private final Exp exp;

    public RHExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> hp) throws MyException {
        Value ref =  exp.eval(tbl,hp);
        if (!(ref instanceof RefValue))
            throw new MyException("RHExp ERROR: Argument not a RefValue");
        int address = ((RefValue) ref).getAddr();
        if(!hp.isDefined(address))
            throw new MyException("RHExp ERROR: Address " + address + " is not defined");
        return hp.lookup(address);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType refType = (RefType) typ;
            return refType.getInner();
        }
        throw new MyException("RHExp ERROR: the rH argument is not a Ref Type");
    }

    @Override
    public String toString(){
        return "rH("+exp.toString()+")";
    }

}
