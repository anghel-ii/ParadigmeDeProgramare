package examples;

import model.statements.IStmt;

public class ExampleProgram {
    private final String key;
    private final IStmt stmt;
    private final String logPath;

    public ExampleProgram(String key, IStmt stmt, String logPath) {
        this.key = key;
        this.stmt = stmt;
        this.logPath = logPath;
    }

    public String getKey() {
        return key;
    }

    public IStmt getStmt() {
        return stmt;
    }

    public String getLogPath() {
        return logPath;
    }

    @Override
    public String toString() {
        return stmt.toString();
    }
}
