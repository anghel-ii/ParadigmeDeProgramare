package model.expressions;

import adt.MyIDictionary;
import exceptions.MyException;
import model.values.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl) throws MyException;
}
