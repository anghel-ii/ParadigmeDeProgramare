package model.statements;

import adt.MyIDictionary;
import exceptions.MyException;
import model.expressions.Exp;
import model.types.Type;
import model.values.Value;
import state.PrgState;

public final class AssignStmt implements IStmt{
    private final String id;
    private final Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (!symTbl.isDefined(this.id))
            throw new MyException("AssignStmt ERRPR: Variable " + this.id + " is not defined");
        Value v = exp.eval(symTbl);
        Type typeId = symTbl.lookup(id).getType();
        if(!v.getType().equals(typeId))
            throw new MyException("AssignStmt ERRPR: Variable " + this.id + " is not of type " + typeId);
        symTbl.update(id, v);
        return state;
    }

    @Override
    public String toString() {
        return id + " = " + exp;
    }
}
