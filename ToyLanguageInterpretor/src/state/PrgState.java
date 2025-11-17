package state;

import adt.MyIDictionary;
import adt.MyIList;
import adt.MyIStack;
import model.statements.IStmt;
import model.values.Value;

public class PrgState {
    private final MyIStack<IStmt> exeStack;
    private final MyIDictionary<String, Value> symTable;
    private final MyIList<Value> out;
    private final IStmt originalProgram; // optional

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, IStmt prg) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.originalProgram = prg; // shallow, for simplicity
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
    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    @Override
    public String toString() {
        return "ExeStack=" + exeStack.toList() + "\nSymTable=" + symTable + "\nOut=" + out + "\n";
    }
}
