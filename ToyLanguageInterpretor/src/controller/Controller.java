package controller;

import exceptions.MyException;
import model.statements.IStmt;
import repo.IRepository;
import state.PrgState;

public class Controller {
    private final IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public PrgState oneStep(PrgState state) {
        if (state.getExeStack().isEmpty())
            throw new MyException("Program state stack is empty");
        IStmt crtStmt = state.getExeStack().pop();
        return crtStmt.execute(state);
    }

    public void allStep() {
        PrgState prg = repo.getCrtPrg();
        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            System.out.println(prg);
        }
        System.out.println("Final state:\n" + prg);
    }
}
