package model.statements;

import adt.MyIDictionary;
import exceptions.MyException;
import model.types.Type;
import state.PrgState;

public class NopStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) {
        return null;
    }
    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
    @Override
    public String toString() {
        return "nop";
    }
}
