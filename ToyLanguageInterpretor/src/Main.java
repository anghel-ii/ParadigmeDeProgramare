import controller.Controller;
import model.expressions.*;
import model.statements.*;
import model.types.*;
import model.values.*;
import repo.IRepository;
import repo.Repository;
import state.PrgState;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

import java.io.BufferedReader;
import adt.MyDictionary;
import adt.MyIDictionary;
import adt.MyIList;
import adt.MyList;
import adt.MyStack;
import adt.MyIStack;


public class Main { // Renamed from Interpreter for consistency with existing file name

    public static void main(String[] args) {
        // Example 1: int v; v=2; Print(v)
        IStmt ex1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );
        MyIStack<IStmt> stk1 = new MyStack<>();
        MyIDictionary<String, Value> symTbl1 = new MyDictionary<>();
        MyIList<Value> out1 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTbl1 = new MyDictionary<>();
        PrgState prg1 = new PrgState(stk1, symTbl1, out1, ex1, fileTbl1);
        IRepository repo1 = new Repository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);

        // Example 2: int a; int b; a = 2 + 3 * 5; b = a - 4 / 2 + 7; Print(b)
        IStmt ex2 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt("a",
                                        new ArithExp('+', new ValueExp(new IntValue(2)),
                                                new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(
                                        new AssignStmt("b",
                                                new ArithExp('+',
                                                        new ArithExp('-',
                                                                new VarExp("a"),
                                                                new ArithExp('/',
                                                                        new ValueExp(new IntValue(4)),
                                                                        new ValueExp(new IntValue(2))
                                                                )
                                                        ),
                                                        new ValueExp(new IntValue(7))
                                                )
                                        ),
                                        new PrintStmt(new VarExp("b"))
                                )
                        )
                )
        );
        MyIStack<IStmt> stk2 = new MyStack<>();
        MyIDictionary<String, Value> symTbl2 = new MyDictionary<>();
        MyIList<Value> out2 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTbl2 = new MyDictionary<>();
        PrgState prg2 = new PrgState(stk2, symTbl2, out2, ex2, fileTbl2);
        IRepository repo2 = new Repository(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2);

        // Example 3: bool a; a=false; int v; If a Then v=2 Else v=3; Print(v)
        IStmt ex3 = new CompStmt(
                new VarDeclStmt("a", new BoolType()),
                new CompStmt(
                        new AssignStmt("a", new ValueExp(new BoolValue(false))),
                        new CompStmt(
                                new VarDeclStmt("v", new IntType()),
                                new CompStmt(
                                        new IfStmt(new VarExp("a"),
                                                new AssignStmt("v", new ValueExp(new IntValue(2))),
                                                new AssignStmt("v", new ValueExp(new IntValue(3)))
                                        ),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                )
        );
        MyIStack<IStmt> stk3 = new MyStack<>();
        MyIDictionary<String, Value> symTbl3 = new MyDictionary<>();
        MyIList<Value> out3 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTbl3 = new MyDictionary<>();
        PrgState prg3 = new PrgState(stk3, symTbl3, out3, ex3, fileTbl3);
        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3);

        // Example 4: File operations
        IStmt ex4 = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf",new ValueExp(new StringValue("test.in"))),
                        new CompStmt(
                                new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc",new IntType()),
                                        new CompStmt(
                                                new ReadFileStmt(new VarExp("varf"),"varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(
                                                                new ReadFileStmt(new VarExp("varf"),"varc"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        MyIStack<IStmt> stk4 = new MyStack<>();
        MyIDictionary<String, Value> symTbl4 = new MyDictionary<>();
        MyIList<Value> out4 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTbl4 = new MyDictionary<>();
        PrgState prg4 = new PrgState(stk4, symTbl4, out4, ex4, fileTbl4);
        IRepository repo4 = new Repository(prg4, "log4.txt");
        Controller ctr4 = new Controller(repo4);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),ctr1));
        menu.addCommand(new RunExample("2",ex2.toString(),ctr2));
        menu.addCommand(new RunExample("3",ex3.toString(),ctr3));
        menu.addCommand(new RunExample("4",ex4.toString(),ctr4));
        menu.show();
    }
}