package model.statements;

import adt.MyIDictionary;
import adt.MyIHeap;
import exceptions.MyException;
import model.expressions.Exp;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;
import state.PrgState;

public class NewStmt implements IStmt{
    private final String var_name;
    private final Exp exp;

    public NewStmt(String var_name, Exp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer,Value> heap = state.getHeap();

        if (!symTbl.isDefined(this.var_name))
            throw new MyException("NewStmt ERROR: Variable " + this.var_name + " is not defined");

        Type varType = symTbl.lookup(var_name).getType();
        Value val = exp.eval(symTbl,heap);
        Type expType = val.getType();

        if(!varType.equals(new RefType(expType)))
            throw new MyException("NewStmt ERROR: Variable " + this.var_name + " is not of type RefType or not referancing " + expType);

        int adress = heap.allocate(val);
        symTbl.update(var_name,new RefValue(adress,expType));

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var_name);
        Type typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        throw new MyException("NewStmt ERROR: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return "new(" + this.var_name + "," + this.exp + ")";
    }
}
