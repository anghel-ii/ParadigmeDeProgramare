package model.statements;

import adt.MyIStack;
import state.PrgState;

public final class CompStmt implements IStmt {
    private final IStmt first, second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(second);
        stk.push(first);
        return state;
    }

    @Override
    public String toString() {
        return "(" + first + " ; " + second + ")";
    }
}
