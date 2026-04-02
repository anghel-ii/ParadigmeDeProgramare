package repo;

import adt.MyIDictionary;
import adt.MyIHeap;
import exceptions.MyException;
import model.statements.IStmt;
import model.values.StringValue;
import model.values.Value;
import state.PrgState;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> prgList;
    private final String logFilePath;

    public Repository(PrgState state , String logFilePath) {
        this.prgList = new ArrayList<>();
        this.prgList.add(state);
        this.logFilePath = logFilePath;
    }

    @Override
    public List<PrgState> getPrgList() {
        return prgList;
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        this.prgList = prgList;
    }

    @Override
    public void logPrgStateExec(PrgState state) throws MyException {
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));

            logFile.println("Id=" + state.getId());
            logFile.println("ExeStack:");

            for (IStmt s : state.getExeStack().toList()) {
                logFile.println(s.toString());
            }

            logFile.println("SymTable:");
            MyIDictionary<String, Value> symTable = state.getSymTable();

            for (String key : symTable.keys()) {
                logFile.println(key + " --> " + symTable.lookup(key));
            }

            logFile.println("Out:");
            for (Value v : state.getOut().getAll()) {
                logFile.println(v.toString());
            }

            logFile.println("FileTable:");
            MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

            for (StringValue key : fileTable.keys()) {
                logFile.println(key + " --> " + fileTable.lookup(key));
            }

            logFile.println("Heap:");
            MyIHeap<Integer,Value> heap = state.getHeap();

            for (Integer key : heap.keys()) {
                logFile.println(key + " --> " + heap.lookup(key));
            }

            logFile.println("------------------------------");
            logFile.close();
        }
        catch (Exception e) {
            throw new MyException("File writing error: " + e.getMessage());
        }
    }


}
