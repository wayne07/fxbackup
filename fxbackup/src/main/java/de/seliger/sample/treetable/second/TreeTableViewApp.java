package de.seliger.sample.treetable.second;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TreeTableViewApp extends Application {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Parent createContent() {
        final TreeTableColumn<Inventory, String> nameColumn = createColumn("Name", true, 200, new TreeItemPropertyValueFactory("name"), TriStateCheckBoxTreeTableCell.create());
        final TreeTableColumn<Inventory, String> dataColumn = createColumn("Data", false, 150, new TreeItemPropertyValueFactory("data"), null);
        final TreeTableColumn<Inventory, String> notesColumn = createColumn("Notes", false, 200, new TreeItemPropertyValueFactory("notes"), TextFieldTreeTableCell.<Inventory>forTreeTableColumn());

        final TreeTableView treeTableView = new TreeTableView(getData());
        treeTableView.setEditable(true);
        treeTableView.setPrefSize(600, 400);
        ObservableList observableList = treeTableView.getColumns();
        observableList.setAll(nameColumn, dataColumn, notesColumn);
        observableList.addListener((ListChangeListener.Change c) -> {
            if(c.wasUpdated()) {
                int from = c.getFrom();
                System.out.println("from:"+from);
            }
        });

        return treeTableView;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TreeTableViewApp");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private TreeTableColumn<Inventory, String> createColumn(String columnName, boolean isEditable, int minWidth,
                                                            TreeItemPropertyValueFactory valueFactory,
                                                            Callback<TreeTableColumn<Inventory, String>, TreeTableCell<Inventory, String>> cellFactory) {
        final TreeTableColumn<Inventory, String> nameColumn = new TreeTableColumn<>(columnName);
        nameColumn.setEditable(isEditable);
        nameColumn.setMinWidth(minWidth);
        nameColumn.setCellValueFactory(valueFactory);
        if (null != cellFactory) {
            nameColumn.setCellFactory(cellFactory);
        }
        return nameColumn;
    }

    @SuppressWarnings("unchecked")
    private TreeItem<Inventory> getData() {
        final TreeItem<Inventory> rootItem = new TreeItem<>(new Inventory("Root", new Data("Root data"), ""));
        final TreeItem<Inventory> child1Item = new TreeItem<>(new Inventory("Child 1", new Data("Child 1 data"), "My notes"));
        final TreeItem<Inventory> child2Item = new TreeItem<>(new Inventory("Child 2", new Data("Child 2 data"), "Notes"));
        TreeItem<Inventory> child3Item = new TreeItem<>(new Inventory("Child 3", new Data("Child 3 data"), "Observations"));
        TreeItem<Inventory> child4Item = new TreeItem<>(new Inventory("Child 4", new Data("Child 4 data"), "Observations4"));
        rootItem.setExpanded(true);
        rootItem.getChildren().addAll(child1Item, child2Item);
        child1Item.getChildren().add(child3Item);
        child1Item.getChildren().add(child4Item);
        return rootItem;
    }

}
