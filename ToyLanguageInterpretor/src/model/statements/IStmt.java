package model.statements;

import exceptions.MyException;
import adt.MyIDictionary;
import model.types.Type;
import state.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
