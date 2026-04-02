package state;

import adt.MyIDictionary;
import adt.MyIHeap;
import adt.MyIList;
import adt.MyIStack;
import model.statements.IStmt;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class PrgState {
    private static int lastId = 0;
    private final MyIStack<IStmt> exeStack;
    private final MyIDictionary<String, Value> symTable;
    private final MyIList<Value> out;
    private final MyIDictionary<StringValue, BufferedReader> fileTable;
    private final MyIHeap<Integer,Value> heap;
    private final int id;

    public PrgState(
            MyIStack<IStmt> stk,
            MyIDictionary<String, Value> symtbl,
            MyIList<Value> ot,
            IStmt prg,
            MyIDictionary<StringValue, BufferedReader> filetbl,
            MyIHeap<Integer,Value> heap
    ) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.fileTable = filetbl;
        this.exeStack.push(prg);
        this.heap = heap;
        this.id = getNextId();
    }

    private static synchronized int getNextId() {
        lastId++;
        return lastId;
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
    public MyIHeap<Integer,Value> getHeap() {
        return heap;
    }
    public int getId() {
        return id;
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() {
        if (exeStack.isEmpty())
            throw new exceptions.MyException("prgstate stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }


    @Override
    public String toString() {
        return "Id=" + id + "\nExeStack=" + exeStack.toList() + "\nSymTable=" + symTable + "\nOut=" + out + "\nFileTable=" + fileTable + "\nHeap=" + heap +"\n";
    }
}
