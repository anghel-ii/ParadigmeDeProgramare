package state;

import adt.MyIDictionary;
import adt.MyIList;
import adt.MyIStack;
import model.statements.IStmt;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class PrgState {
    private final MyIStack<IStmt> exeStack;
    private final MyIDictionary<String, Value> symTable;
    private final MyIList<Value> out;
    private final MyIDictionary<StringValue, BufferedReader> fileTable;


    public PrgState(
            MyIStack<IStmt> stk,
            MyIDictionary<String, Value> symtbl,
            MyIList<Value> ot,
            IStmt prg,
            MyIDictionary<StringValue, BufferedReader> filetbl
    ) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.fileTable = filetbl;
        this.exeStack.push(prg);
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }
    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }
    public MyIList<Value> getOut() {
        return out;
    }
    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }


    @Override
    public String toString() {
        return "ExeStack=" + exeStack.toList() + "\nSymTable=" + symTable + "\nOut=" + out + "\nFileTable=" + fileTable + "\n";
    }
}
