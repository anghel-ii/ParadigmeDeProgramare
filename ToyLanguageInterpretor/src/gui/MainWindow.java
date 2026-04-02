package gui;

import controller.Controller;
import exceptions.MyException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.values.StringValue;
import model.values.Value;
import state.PrgState;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainWindow {
    private final Controller controller;

    private final TextField prgCountField = new TextField();
    private final TableView<HeapEntry> heapTable = new TableView<>();
    private final ListView<String> outList = new ListView<>();
    private final ListView<String> fileTableList = new ListView<>();
    private final ListView<Integer> prgIdsList = new ListView<>();
    private final TableView<SymEntry> symTable = new TableView<>();
    private final ListView<String> exeStackList = new ListView<>();
    private final Button oneStepButton = new Button("Run one step");

    public MainWindow(Controller controller) {
        this.controller = controller;
        setupTables();
        setupHandlers();
        refresh();
    }

    public void show(Stage stage) {
        prgCountField.setEditable(false);

        HBox top = new HBox(10, new Label("PrgStates:"), prgCountField, oneStepButton);

        VBox left = new VBox(8, new Label("Program IDs"), prgIdsList, new Label("ExeStack"), exeStackList);
        VBox center = new VBox(8, new Label("Heap"), heapTable, new Label("SymTable"), symTable);
        VBox right = new VBox(8, new Label("Out"), outList, new Label("FileTable"), fileTableList);

        HBox main = new HBox(10, left, center, right);
        VBox root = new VBox(10, top, main);
        root.setPadding(new Insets(10));

        VBox.setVgrow(heapTable, Priority.ALWAYS);
        VBox.setVgrow(symTable, Priority.ALWAYS);
        VBox.setVgrow(outList, Priority.ALWAYS);
        VBox.setVgrow(fileTableList, Priority.ALWAYS);
        VBox.setVgrow(prgIdsList, Priority.ALWAYS);
        VBox.setVgrow(exeStackList, Priority.ALWAYS);

        stage.setTitle("ToyLanguage Interpreter");
        stage.setScene(new Scene(root, 1200, 700));
        stage.show();
    }

    private void setupTables() {
        TableColumn<HeapEntry, Integer> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<HeapEntry, String> valueCol = new TableColumn<>("Value");
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        heapTable.getColumns().addAll(addressCol, valueCol);

        TableColumn<SymEntry, String> varCol = new TableColumn<>("Variable");
        varCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<SymEntry, String> symValueCol = new TableColumn<>("Value");
        symValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        symTable.getColumns().addAll(varCol, symValueCol);
    }

    private void setupHandlers() {
        prgIdsList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateSelectedPrgData());
        oneStepButton.setOnAction(event -> {
            try {
                controller.oneStepGui();
                refresh();
            } catch (MyException e) {
                showAlert(e.getMessage());
            }
        });
    }

    private void refresh() {
        List<PrgState> prgList = controller.getPrgList();
        prgCountField.setText(String.valueOf(prgList.size()));

        if (prgList.isEmpty()) {
            heapTable.getItems().clear();
            outList.getItems().clear();
            fileTableList.getItems().clear();
            prgIdsList.getItems().clear();
            symTable.getItems().clear();
            exeStackList.getItems().clear();
            oneStepButton.setDisable(true);
            return;
        }

        oneStepButton.setDisable(false);
        PrgState first = prgList.get(0);

        heapTable.setItems(FXCollections.observableArrayList(
                first.getHeap().getContent().entrySet().stream()
                        .map(e -> new HeapEntry(e.getKey(), e.getValue().toString()))
                        .collect(Collectors.toList())
        ));

        outList.setItems(FXCollections.observableArrayList(
                first.getOut().getAll().stream()
                        .map(Value::toString)
                        .collect(Collectors.toList())
        ));

        fileTableList.setItems(FXCollections.observableArrayList(
                first.getFileTable().keys().stream()
                        .map(StringValue::toString)
                        .collect(Collectors.toList())
        ));

        Integer selectedId = prgIdsList.getSelectionModel().getSelectedItem();
        prgIdsList.setItems(FXCollections.observableArrayList(
                prgList.stream().map(PrgState::getId).collect(Collectors.toList())
        ));
        if (selectedId != null && prgIdsList.getItems().contains(selectedId)) {
            prgIdsList.getSelectionModel().select(selectedId);
        } else if (!prgIdsList.getItems().isEmpty()) {
            prgIdsList.getSelectionModel().select(0);
        }

        updateSelectedPrgData();
    }

    private void updateSelectedPrgData() {
        List<PrgState> prgList = controller.getPrgList();
        if (prgList.isEmpty()) {
            symTable.getItems().clear();
            exeStackList.getItems().clear();
            return;
        }
        Integer selectedId = prgIdsList.getSelectionModel().getSelectedItem();
        PrgState selected = prgList.stream()
                .filter(p -> Objects.equals(p.getId(), selectedId))
                .findFirst()
                .orElse(prgList.get(0));

        symTable.setItems(FXCollections.observableArrayList(
                selected.getSymTable().getContent().entrySet().stream()
                        .map(e -> new SymEntry(e.getKey(), e.getValue().toString()))
                        .collect(Collectors.toList())
        ));

        exeStackList.setItems(FXCollections.observableArrayList(
                selected.getExeStack().toList().stream()
                        .map(Object::toString)
                        .collect(Collectors.toList())
        ));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class HeapEntry {
        private final SimpleIntegerProperty address;
        private final SimpleStringProperty value;

        public HeapEntry(int address, String value) {
            this.address = new SimpleIntegerProperty(address);
            this.value = new SimpleStringProperty(value);
        }

        public int getAddress() {
            return address.get();
        }

        public String getValue() {
            return value.get();
        }
    }

    public static class SymEntry {
        private final SimpleStringProperty name;
        private final SimpleStringProperty value;

        public SymEntry(String name, String value) {
            this.name = new SimpleStringProperty(name);
            this.value = new SimpleStringProperty(value);
        }

        public String getName() {
            return name.get();
        }

        public String getValue() {
            return value.get();
        }
    }
}
