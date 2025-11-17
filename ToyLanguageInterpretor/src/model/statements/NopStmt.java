package model.statements;

import state.PrgState;

public final class NopStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) {
        return state;
    }
    @Override
    public String toString() {
        return "nop";
    }
}
