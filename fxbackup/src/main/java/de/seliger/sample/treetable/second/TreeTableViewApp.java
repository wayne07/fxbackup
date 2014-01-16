package de.seliger.sample.treetable.second;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

/**
 * A simple implementation of TreeTableView. The Notes column is editable.
 *
 * @sampleName TreeTableView
 * @preview preview.png
 * @see javafx.scene.control.cell.TextFieldTreeTableCell
 * @see javafx.scene.control.cell.TreeItemPropertyValueFactory
 * @see javafx.scene.control.TreeItem
 * @see javafx.scene.control.TreeTableCell
 * @see javafx.scene.control.TreeTableColumn
 * @see javafx.scene.control.TreeTableView
 * @see javafx.beans.property.SimpleStringProperty
 * @see javafx.beans.property.StringProperty
 * @see javafx.beans.property.ObjectProperty
 * @see javafx.beans.property.SimpleObjectProperty
 * @embedded
 */
public class TreeTableViewApp extends Application {

    private TreeItem<Inventory> getData() {
        final TreeItem<Inventory> rootItem = new TreeItem<>(new Inventory("Root", new Data("Root data"), ""));
        final TreeItem<Inventory> child1Item = new TreeItem<>(new Inventory("Child 1", new Data("Child 1 data"), "My notes"));
        final TreeItem<Inventory> child2Item = new TreeItem<>(new Inventory("Child 2", new Data("Child 2 data"), "Notes"));
        TreeItem<Inventory> child3Item = new TreeItem<>(new Inventory("Child 3", new Data("Child 3 data"), "Observations"));
        rootItem.setExpanded(true);
        rootItem.getChildren().addAll(child1Item, child2Item);
        child1Item.getChildren().add(child3Item);
        return rootItem;
    }

    public Parent createContent() {

        final TreeTableColumn<Inventory, String> nameColumn = new TreeTableColumn<Inventory, String>("Name");
        nameColumn.setEditable(false);
        nameColumn.setMinWidth(130);
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory("name"));

        final TreeTableColumn<Inventory, String> dataColumn = new TreeTableColumn<>("Data");
        dataColumn.setEditable(false);
        dataColumn.setMinWidth(150);
        dataColumn.setCellValueFactory(new TreeItemPropertyValueFactory("data"));

        final TreeTableColumn<Inventory, String> notesColumn = new TreeTableColumn<>("Notes (editable)");
        notesColumn.setEditable(true);
        notesColumn.setMinWidth(150);
        notesColumn.setCellValueFactory(new TreeItemPropertyValueFactory("notes"));
        notesColumn.setCellFactory(TextFieldTreeTableCell.<Inventory> forTreeTableColumn());

        final TreeTableView treeTableView = new TreeTableView(getData());
        treeTableView.setEditable(true);
        treeTableView.setPrefSize(430, 200);
        treeTableView.getColumns().setAll(nameColumn, dataColumn, notesColumn);

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
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
