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

public class WHStmt implements IStmt {
    private final String varName;
    private final Exp exp;

    public WHStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        //MyIDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();
        MyIHeap<Integer,Value> heap = state.getHeap();

        if (!symTbl.isDefined(this.varName))
            throw new MyException("WHStmt ERROR: Variable " + this.varName + " is not defined");
        Type varType = symTbl.lookup(varName).getType();
        if(!(varType instanceof RefType))
            throw new MyException("WHStmt ERROR: Variable " + this.varName + " is not reftype");

        Value ref = symTbl.lookup(varName);
        int address = ((RefValue) ref).getAddr();

        if(!heap.isDefined(address))
            throw new MyException("WHStmt ERROR: Address " + this.varName + " is not defined");

        Value val = exp.eval(symTbl,heap);

        if(!val.getType().equals(((RefType) ref.getType()).getInner()))
            throw  new MyException("WHStmt ERROR: Variable " + this.varName + " is not the refernced type");

        heap.update(address, val);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(varName);
        Type typeExp = exp.typecheck(typeEnv);
        if (!(typeVar instanceof RefType))
            throw new MyException("WHStmt ERROR: " + varName + " is not a RefType");
        RefType refType = (RefType) typeVar;
        if (refType.getInner().equals(typeExp))
            return typeEnv;
        throw new MyException("WHStmt ERROR: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return "wH(" + this.varName +","+ this.exp.toString() + ")";
    }
}
