package model.expressions;

import adt.MyIDictionary;
import adt.MyIHeap;
import exceptions.MyException;
import model.types.Type;
import model.values.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl , MyIHeap<Integer,Value> hp) throws MyException;
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
