package gui;

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
import exceptions.MyException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.statements.IStmt;
import model.values.StringValue;
import model.values.Value;
import repo.IRepository;
import repo.Repository;
import state.PrgState;

import java.io.BufferedReader;

public class GuiMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        ListView<ExampleProgram> listView = new ListView<>();
        listView.setItems(FXCollections.observableArrayList(ExampleFactory.buildExamples()));

        Button startButton = new Button("Start");
        startButton.setOnAction(event -> {
            ExampleProgram selected = listView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Select a program to run.");
                return;
            }
            try {
                Controller controller = buildController(selected);
                Stage mainStage = new Stage();
                new MainWindow(controller).show(mainStage);
                primaryStage.hide();
            } catch (MyException ex) {
                showAlert(ex.getMessage());
            }
        });

        VBox root = new VBox(10, listView, startButton);
        root.setPadding(new Insets(10));

        primaryStage.setTitle("Program Selection");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    private Controller buildController(ExampleProgram example) {
        IStmt stmt = example.getStmt();
        stmt.typecheck(new MyDictionary<>());

        MyIStack<IStmt> stk = new MyStack<>();
        MyIDictionary<String, Value> symTbl = new MyDictionary<>();
        MyIList<Value> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTbl = new MyDictionary<>();
        MyIHeap<Integer, Value> heap = new MyHeap<>();

        PrgState prg = new PrgState(stk, symTbl, out, stmt, fileTbl, heap);
        IRepository repo = new Repository(prg, example.getLogPath());
        return new Controller(repo);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
