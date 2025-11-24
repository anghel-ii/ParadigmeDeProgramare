package model.statements;

import adt.MyIDictionary;
import adt.MyIList;
import model.expressions.Exp;
import model.values.Value;
import state.PrgState;

public final class PrintStmt implements IStmt {
    private final Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIList<Value> out = state.getOut();
        Value v = exp.eval(tbl);
        out.add(v);
        return state;
    }

    @Override
    public String toString() {
        return "print(" + exp + ")";
    }
}
