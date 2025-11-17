package model.statements;

import adt.MyIDictionary;
import exceptions.MyException;
import model.types.Type;
import model.values.Value;
import state.PrgState;

public final class VarDeclStmt implements IStmt{
    private final String name;
    private final Type type;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if(symTbl.isDefined(name))
            throw new MyException("Variable already declared: " + name);
        symTbl.update(name, type.defaultValue());
        return state;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}