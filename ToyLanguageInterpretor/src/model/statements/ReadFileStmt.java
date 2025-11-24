package model.statements;

import adt.MyIDictionary;
import exceptions.MyException;
import model.expressions.Exp;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;
import state.PrgState;

import java.io.BufferedReader;

public final class ReadFileStmt implements IStmt {
    private final Exp exp;
    private final String var_name;

    public ReadFileStmt(Exp exp, String var_name) {
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();

        if(!symTbl.isDefined(var_name))
            throw new MyException("ReadFile ERROR: Variable " + var_name + " is not defined");

        Type typeVar = symTbl.lookup(var_name).getType();

        if(!typeVar.equals(new IntType()))
            throw new MyException("ReadFile ERROR: Variable " + var_name + " is not of type Int");

        Value val = exp.eval(symTbl);
        if(!val.getType().equals(new StringType()))
            throw new MyException("ReadFile ERROR: invalid argument type");

        StringValue fileName = (StringValue) val;

        if(!fileTbl.isDefined(fileName))
            throw new MyException("ReadFile ERROR: File " + fileName + " is not defined");

        try{
            BufferedReader br = fileTbl.lookup(fileName);
            String line = br.readLine();
            if(line == null)
                symTbl.update(var_name, typeVar.defaultValue());
            else
                symTbl.update(var_name, new IntValue(Integer.parseInt(line)));
        }
        catch (Exception e){
            throw new MyException("ReadFile ERROR: " + e.getMessage());
        }

        return state;
    }

    @Override
    public String toString() {
        return "readFile("+exp.toString()+","+var_name+")";
    }
}
