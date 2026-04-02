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

public class IfStmt implements IStmt {
    private final Exp condition;
    private final IStmt thenStmt;
    private final IStmt elseStmt;

    public IfStmt(Exp condition, IStmt thenStmt, IStmt elseStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer,Value> heap = state.getHeap();

        Value v = condition.eval(symTbl,heap);
        if(!v.getType().equals(new BoolType()))
            throw new MyException("IfStmt ERRROR: invalid condition expression");
        boolean c = ((BoolValue)v).getVal();
        MyIStack<IStmt> stk = state.getExeStack();
        if(c)
            stk.push(thenStmt);
        else
            stk.push(elseStmt);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = condition.typecheck(typeEnv);
        if (!typeExp.equals(new BoolType()))
            throw new MyException("IfStmt ERROR: condition is not boolean");
        thenStmt.typecheck(copyTypeEnv(typeEnv));
        elseStmt.typecheck(copyTypeEnv(typeEnv));
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
        return "IF(" + condition + ") THEN(" + thenStmt + ") ELSE(" + elseStmt + ")";
    }
}
