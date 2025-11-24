package repo;

import exceptions.MyException;
import state.PrgState;

public interface IRepository {
    PrgState getCrtPrg();
    void logPrgStateExec() throws MyException;
}
