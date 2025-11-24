package repo;

import adt.MyIDictionary;
import exceptions.MyException;
import model.statements.IStmt;
import model.values.Value;
import state.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Repository implements IRepository {
    private final PrgState state;
    private final String logFilePath;

    public Repository(PrgState state , String logFilePath) {
        this.state = state;
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCrtPrg() {
        return state;
    }

    @Override
    public void logPrgStateExec() throws MyException {
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));

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
            //todo

            logFile.println("------------------------------");
            logFile.close();
        }
        catch (Exception e) {
            throw new MyException("File writing error: " + e.getMessage());
        }
    }


}
