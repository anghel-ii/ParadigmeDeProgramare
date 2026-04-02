package repo;

import exceptions.MyException;
import state.PrgState;

import java.util.List;

public interface IRepository {
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
    void logPrgStateExec(PrgState state) throws MyException;
}
