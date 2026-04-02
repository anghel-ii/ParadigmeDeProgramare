import adt.MyDictionary;
import adt.MyHeap;
import adt.MyIDictionary;
import adt.MyIHeap;
import adt.MyIList;
import adt.MyIStack;
import adt.MyList;
import adt.MyStack;
import controller.Controller;
import examples.ExampleFactory;
import examples.ExampleProgram;
import model.statements.IStmt;
import model.values.StringValue;
import model.values.Value;
import repo.IRepository;
import repo.Repository;
import state.PrgState;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

import java.io.BufferedReader;

void main() {
    TextMenu menu = new TextMenu();
    menu.addCommand(new ExitCommand("0", "exit"));

    for (ExampleProgram example : ExampleFactory.buildExamples()) {
        IStmt stmt = example.getStmt();
        stmt.typecheck(new MyDictionary<>());

        MyIStack<IStmt> stk = new MyStack<>();
        MyIDictionary<String, Value> symTbl = new MyDictionary<>();
        MyIList<Value> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTbl = new MyDictionary<>();
        MyIHeap<Integer, Value> heap = new MyHeap<>();

        PrgState prg = new PrgState(stk, symTbl, out, stmt, fileTbl, heap);
        IRepository repo = new Repository(prg, example.getLogPath());
        Controller ctr = new Controller(repo);
        menu.addCommand(new RunExample(example.getKey(), stmt.toString(), ctr));
    }

    menu.show();
}
