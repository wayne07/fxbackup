package de.seliger.sample.treetable;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Person;


public class CheckBoxTreeTableSample extends Application {

    private final static ObservableList<Person> data;

    static {
        data = Person.getTestList();
    }

    public static void main(String[] args) {
        Application.launch(CheckBoxTreeTableSample.class, args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("CheckBoxCell Samples");
        final Scene scene = new Scene(new Group(), 875, 700);
        scene.setFill(Color.LIGHTGRAY);
        Group root = (Group)scene.getRoot();

        root.getChildren().add(getContent(scene));

        stage.setScene(scene);
        stage.show();
    }

    public Node getContent(Scene scene) {
        // TabPane
        final TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setPrefWidth(scene.getWidth());
        tabPane.setPrefHeight(scene.getHeight());

        tabPane.prefWidthProperty().bind(scene.widthProperty());
        tabPane.prefHeightProperty().bind(scene.heightProperty());

        // tree view examples
        Tab treeViewTab = new Tab("TreeTableView");
        buildTreeTableViewTab(treeViewTab);
        tabPane.getTabs().add(treeViewTab);


        return tabPane;
    }

    private void buildTreeTableViewTab(Tab tab) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.setHgap(5);
        grid.setVgap(5);

        final CheckBoxTreeItem<String> root = new CheckBoxTreeItem<String>("Root node");
        final CheckBoxTreeItem<String> childNode1 = new CheckBoxTreeItem<String>("Child Node 1");
        final CheckBoxTreeItem<String> childNode2 = new CheckBoxTreeItem<String>("Child Node 2");
        final CheckBoxTreeItem<String> childNode3 = new CheckBoxTreeItem<String>("Child Node 3");

        root.setExpanded(true);
        root.getChildren().setAll(childNode1, childNode2, childNode3);

        TreeTableColumn<String, String> column = new TreeTableColumn<String, String>("Column");
        column.setPrefWidth(200);
        column.setCellValueFactory(new Callback<CellDataFeatures<String, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(CellDataFeatures<String, String> param) {
                // TODO Auto-generated method stub
                return null;
            }

        });
        //        column.setCellFactory(CheckBoxTreeCell.forTreeView());

        final TreeTableView<String> treeTableView = new TreeTableView<String>(root);
        treeTableView.getColumns().add(column);

        grid.add(treeTableView, 0, 0);
        GridPane.setVgrow(treeTableView, Priority.ALWAYS);
        GridPane.setHgrow(treeTableView, Priority.ALWAYS);

        tab.setContent(grid);


    }

}
