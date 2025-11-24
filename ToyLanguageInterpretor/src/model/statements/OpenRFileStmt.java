package model.statements;

import adt.MyIDictionary;
import exceptions.MyException;
import model.expressions.Exp;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;
import state.PrgState;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public final class OpenRFileStmt implements IStmt {
    private final Exp exp;

    public OpenRFileStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();

        Value val = exp.eval(symTbl);

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

        return state;
    }

    @Override
    public String toString() {
        return "openRFile("+exp.toString()+")";
    }
}
