package examples;

import model.expressions.ArithExp;
import model.expressions.RHExp;
import model.expressions.RelExp;
import model.expressions.ValueExp;
import model.expressions.VarExp;
import model.statements.AssignStmt;
import model.statements.CloseRFileStmt;
import model.statements.CompStmt;
import model.statements.ForkStmt;
import model.statements.IfStmt;
import model.statements.NewStmt;
import model.statements.OpenRFileStmt;
import model.statements.PrintStmt;
import model.statements.ReadFileStmt;
import model.statements.VarDeclStmt;
import model.statements.WHStmt;
import model.statements.WhileStmt;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;

import java.util.ArrayList;
import java.util.List;

public final class ExampleFactory {
    private ExampleFactory() {}

    public static List<ExampleProgram> buildExamples() {
        List<ExampleProgram> examples = new ArrayList<>();

        // Example 1: int v; v=2; Print(v)
        var ex1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );
        examples.add(new ExampleProgram("1", ex1, "log/log1.txt"));

        // Example 2: int a; int b; a = 2 + 3 * 5; b = a - 4 / 2 + 7; Print(b)
        var ex2 = new CompStmt(
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
        examples.add(new ExampleProgram("2", ex2, "log/log2.txt"));

        // Example 3: bool a; a=false; int v; If a Then v=2 Else v=3; Print(v)
        var ex3 = new CompStmt(
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
        examples.add(new ExampleProgram("3", ex3, "log/log3.txt"));

        // Example 4: File operations
        var ex4 = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(
                                new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(
                                                                new ReadFileStmt(new VarExp("varf"), "varc"),
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
        examples.add(new ExampleProgram("4", ex4, "log/log4.txt"));

        // Example 5: Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)
        var ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a"))
                                        )
                                )
                        )
                )
        );
        examples.add(new ExampleProgram("5", ex5, "log/log5.txt"));

        // Example 6: Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a))+5)
        var ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new RHExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+',
                                                        new RHExp(new RHExp(new VarExp("a"))),
                                                        new ValueExp(new IntValue(5)))
                                                )
                                        )
                                )
                        )
                )
        );
        examples.add(new ExampleProgram("6", ex6, "log/log6.txt"));

        // Example 7: Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        var ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new RHExp(new VarExp("v"))),
                                new CompStmt(new WHStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+',
                                                new RHExp(new VarExp("v")),
                                                new ValueExp(new IntValue(5)))
                                        )
                                )
                        )
                )
        );
        examples.add(new ExampleProgram("7", ex7, "log/log7.txt"));

        // Example 8: Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))
        var ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new RHExp(new RHExp(new VarExp("a")))))
                                )
                        )
                )
        );
        examples.add(new ExampleProgram("8", ex8, "log/log8.txt"));

        // Example 9: int v; v=4; (while (v>0) print(v);v=v-1); print(v)
        var ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(
                                new RelExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1))))
                                )
                        ),
                                new PrintStmt(new VarExp("v"))
                        )
                )
        );
        examples.add(new ExampleProgram("9", ex9, "log/log9.txt"));

        // Example 10: fork with shared heap/out
        var ex10 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new AssignStmt("v", new ValueExp(new IntValue(32))),

                                                                new CompStmt(
                                                                        new WHStmt("a", new ValueExp(new IntValue(30))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new RHExp(new VarExp("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("vv")),
                                                        new PrintStmt(new RHExp(new VarExp("a")))
                                                )
                                        )
                                )
                        )
                )
        );
        examples.add(new ExampleProgram("10", ex10, "log/log10.txt"));

        return examples;
    }
}
