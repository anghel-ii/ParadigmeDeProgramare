package model.statements;

import adt.MyIDictionary;
import adt.MyIHeap;
import exceptions.MyException;
import model.expressions.Exp;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;
import state.PrgState;
import java.io.BufferedReader;
import java.io.FileReader;

public class OpenRFileStmt implements IStmt {
    private final Exp exp;

    public OpenRFileStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();
        MyIHeap<Integer,Value> heap = state.getHeap();
        Value val = exp.eval(symTbl,heap);

        if(!val.getType().equals(new StringType()))
            throw new MyException("OpenRFile ERROR: invalid argument type");

        StringValue fileName = (StringValue) val;

        if(fileTbl.isDefined(fileName))
            throw new MyException("OpenRFile ERROR: file already opened");

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName.getVal()));
            fileTbl.update(fileName, br);
        }
        catch (Exception e) {
            throw new MyException("OpenRFile ERROR: " + e.getMessage());
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        if (typeExp.equals(new StringType()))
            return typeEnv;
        throw new MyException("OpenRFile ERROR: argument is not a string");
    }

    @Override
    public String toString() {
        return "openRFile("+exp.toString()+")";
    }
}
