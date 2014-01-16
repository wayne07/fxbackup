package de.seliger.sample.treetable.second;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TreeTableViewApp extends Application {

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Parent createContent() {

        final TreeTableColumn<Inventory, String> nameColumn = new TreeTableColumn<Inventory, String>("Name");
        nameColumn.setEditable(true);
        nameColumn.setMinWidth(130);
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory("name"));

        nameColumn.setCellFactory(CheckBoxTreeTableCell.<Inventory, String> forTreeTableColumn(new MyCellFactory(), true));

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
        treeTableView.setPrefSize(600, 400);
        treeTableView.getColumns().setAll(nameColumn, dataColumn, notesColumn);

        return treeTableView;
    }

    class MyCellFactory implements Callback<Integer, ObservableValue<Boolean>> {

        private ObservableBooleanValue observableValue;

        @Override
        public ObservableValue<Boolean> call(Integer param) {
            return new ObservableBooleanValue() {

                @Override
                public void removeListener(InvalidationListener listener) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void addListener(InvalidationListener listener) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void removeListener(ChangeListener<? super Boolean> listener) {
                    // TODO Auto-generated method stub

                }

                @Override
                public Boolean getValue() {
                    // TODO Auto-generated method stub
                    return true;
                }

                @Override
                public void addListener(ChangeListener<? super Boolean> listener) {
                    // TODO Auto-generated method stub

                }

                @Override
                public boolean get() {
                    // TODO Auto-generated method stub
                    return false;
                }
            };
        }

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
