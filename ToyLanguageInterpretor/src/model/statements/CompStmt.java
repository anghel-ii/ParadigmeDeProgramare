package model.statements;

import adt.MyIDictionary;
import adt.MyIStack;
import exceptions.MyException;
import model.types.Type;
import state.PrgState;

public class CompStmt implements IStmt {
    private final IStmt first, second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return  first + "; " + second ;
    }
}
