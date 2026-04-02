package model.statements;

import adt.MyIDictionary;
import adt.MyIHeap;
import adt.MyIList;
import exceptions.MyException;
import model.expressions.Exp;
import model.types.Type;
import model.values.Value;
import state.PrgState;

public class PrintStmt implements IStmt {
    private final Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIList<Value> out = state.getOut();
        MyIHeap<Integer,Value> heap = state.getHeap();
        Value v = exp.eval(tbl,heap);
        out.add(v);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print(" + exp + ")";
    }
}
