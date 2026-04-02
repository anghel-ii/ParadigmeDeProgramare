package model.statements;

import adt.MyDictionary;
import adt.MyIDictionary;
import adt.MyIHeap;
import adt.MyIStack;
import exceptions.MyException;
import model.expressions.Exp;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;
import state.PrgState;

import java.util.Map;

public class WhileStmt implements IStmt{
    private final Exp exp;
    private final IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer,Value> heap = state.getHeap();
        Value val = exp.eval(symTbl,heap);

        if(!val.getType().equals(new BoolType()))
            throw new MyException("While ERROR: Condition is not a boolean value");

        if(((BoolValue) val).getVal()) {
            System.out.println("inside true");
            stk.push(this);
            stk.push(stmt);
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        if (!typeExp.equals(new BoolType()))
            throw new MyException("WhileStmt ERROR: condition is not boolean");
        stmt.typecheck(copyTypeEnv(typeEnv));
        return typeEnv;
    }

    private MyIDictionary<String, Type> copyTypeEnv(MyIDictionary<String, Type> typeEnv) {
        MyIDictionary<String, Type> newEnv = new MyDictionary<>();
        for (Map.Entry<String, Type> entry : typeEnv.getContent().entrySet()) {
            newEnv.update(entry.getKey(), entry.getValue());
        }
        return newEnv;
    }

    @Override
    public String toString() {
        return "while("+exp+") " + stmt;
    }
}
