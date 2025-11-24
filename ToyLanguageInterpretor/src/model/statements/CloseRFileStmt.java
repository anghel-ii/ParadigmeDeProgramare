package model.statements;

import adt.MyIDictionary;
import exceptions.MyException;
import model.expressions.Exp;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;
import state.PrgState;

import java.io.BufferedReader;

public class CloseRFileStmt implements IStmt{
    private final Exp exp;

    public CloseRFileStmt(Exp exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();

        Value val = exp.eval(symTbl);
        if(!val.getType().equals(new StringType()))
            throw new MyException("CloseFile ERROR: invalid argument type");

        StringValue fileName = (StringValue) val;
        if(!fileTbl.isDefined(fileName))
            throw new MyException("CloseFile ERROR: File " + fileName + " is not defined");

        try{
            BufferedReader br = fileTbl.lookup(fileName);
            br.close();
            fileTbl.remove(fileName);
        }
        catch (Exception e){
            throw new MyException("CloseFile ERROR: " + e.getMessage());
        }
        return state;
    }

    @Override
    public String toString(){
        return "closeRFile("+exp.toString()+")";
    }
}
