package model.statements;

import exceptions.MyException;
import state.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
}
