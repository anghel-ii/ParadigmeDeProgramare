package model.statements;

import adt.MyDictionary;
import adt.MyIDictionary;
import adt.MyIStack;
import adt.MyStack;
import exceptions.MyException;
import model.types.Type;
import model.values.Value;
import state.PrgState;

import java.util.Map;

public class ForkStmt implements IStmt {
    private final IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> newStack = new MyStack<>();
        MyIDictionary<String, Value> newSymTable = new MyDictionary<>();
        for (Map.Entry<String, Value> entry : state.getSymTable().getContent().entrySet()) {
            newSymTable.update(entry.getKey(), entry.getValue());
        }

        return new PrgState(
                newStack,
                newSymTable,
                state.getOut(),
                stmt,
                state.getFileTable(),
                state.getHeap()
        );
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        MyIDictionary<String, Type> newEnv = new MyDictionary<>();
        for (Map.Entry<String, Type> entry : typeEnv.getContent().entrySet()) {
            newEnv.update(entry.getKey(), entry.getValue());
        }
        stmt.typecheck(newEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + stmt + ")";
    }
}
