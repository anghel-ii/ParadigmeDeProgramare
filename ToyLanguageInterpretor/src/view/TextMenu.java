package view;


import adt.*;
import controller.Controller;
import model.expressions.*;
import model.statements.*;
import model.types.*;
import model.values.*;
import repo.Repository;
import state.PrgState;

import java.util.Scanner;

public class TextMenu {

    private Controller controllerForExample(IStmt program) {
        MyIStack<IStmt> stk = new MyStack<>();
        MyIDictionary<String, Value> symTbl = new MyDictionary<>();
        MyIList<Value> out = new MyList<>();
        PrgState state = new PrgState(stk, symTbl, out, program);
        Repository repo = new Repository(state);
        return new Controller(repo);
    }

    private IStmt example1() {
        // int v; v=2; Print(v)
        return new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );
    }

    private IStmt example2() {
        // int a; int b; a = 2 + 3 * 5; b = a - 4 / 2 + 7; Print(b)
        return new CompStmt(
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
    }

    private IStmt example3() {
        // bool a; a=false; int v; If a Then v=2 Else v=3; Print(v)
        return new CompStmt(
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
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("===== Toy Language Menu =====");
            System.out.println("1) Run Example 1: int v; v=2; Print(v)");
            System.out.println("2) Run Example 2: arithmetic");
            System.out.println("3) Run Example 3: if/else");
            System.out.println("0) Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();
            if (choice.equals("0")) return;
            IStmt program;
            switch (choice) {
                case "1": program = example1(); break;
                case "2": program = example2(); break;
                case "3": program = example3(); break;
                default: System.out.println("Invalid."); continue;
            }
            Controller ctrl = controllerForExample(program);
            try {
                ctrl.allStep();
            } catch (RuntimeException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}