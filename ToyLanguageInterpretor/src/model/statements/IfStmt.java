package model.statements;

import adt.MyIDictionary;
import adt.MyIStack;
import exceptions.MyException;
import model.expressions.Exp;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.Value;
import state.PrgState;

public final class IfStmt implements IStmt {
    private final Exp condition;
    private final IStmt thenStmt;
    private final IStmt elseStmt;

    public IfStmt(Exp condition, IStmt thenStmt, IStmt elseStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        Value v = condition.eval(symTbl);
        if(!v.getType().equals(new BoolType()))
            throw new MyException("IfStmt ERRROR: invalid condition expression");
        boolean c = ((BoolValue)v).getVal();
        MyIStack<IStmt> stk = state.getExeStack();
        if(c)
            stk.push(thenStmt);
        else
            stk.push(elseStmt);
        return state;
    }

    @Override
    public String toString() {
        return "IF(" + condition + ") THEN(" + thenStmt + ") ELSE(" + elseStmt + ")";
    }
}
