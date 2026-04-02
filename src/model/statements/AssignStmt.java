package model.statements;

import adt.MyIDictionary;
import adt.MyIHeap;
import exceptions.MyException;
import model.expressions.Exp;
import model.types.Type;
import model.values.Value;
import state.PrgState;

public class AssignStmt implements IStmt{
    private final String id;
    private final Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer,Value> heap = state.getHeap();

        if (!symTbl.isDefined(this.id))
            throw new MyException("AssignStmt ERRPR: Variable " + this.id + " is not defined");
        Value v = exp.eval(symTbl,heap);
        Type typeId = symTbl.lookup(id).getType();
        if(!v.getType().equals(typeId))
            throw new MyException("AssignStmt ERRPR: Variable " + this.id + " is not of type " + typeId);
        symTbl.update(id, v);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(id);
        Type typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(typeExp))
            return typeEnv;
        throw new MyException("AssignStmt ERROR: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return id + " = " + exp;
    }
}
