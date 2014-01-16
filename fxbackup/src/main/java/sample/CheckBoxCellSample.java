package sample;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Sample code to demonstrate the {@link CheckBoxCellFactory} API
 * for the ListView, TreeView and TableView controls.
 * 
 * @author Jonathan Giles
 */
public class CheckBoxCellSample extends Application {

    private final static ObservableList<Person> data;

    static {
        data = Person.getTestList();
    }

    public static void main(String[] args) {
        Application.launch(CheckBoxCellSample.class, args);
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

        // list view examples
        Tab listViewTab = new Tab("ListView");
        buildListViewTab(listViewTab);
        tabPane.getTabs().add(listViewTab);

        // tree view examples
        Tab treeViewTab = new Tab("TreeView");
        buildTreeViewTab(treeViewTab);
        tabPane.getTabs().add(treeViewTab);

        // table view examples
        Tab tableViewTab = new Tab("TableView");
        buildTableViewTab(tableViewTab);
        tabPane.getTabs().add(tableViewTab);

        return tabPane;
    }

    private void buildListViewTab(Tab tab) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.setHgap(5);
        grid.setVgap(5);

        // create the listview
        final ListView<Person> listView = new ListView<Person>();
        listView.setItems(data);

        // set the cell factory
        Callback<Person, ObservableValue<Boolean>> getProperty = new Callback<Person, ObservableValue<Boolean>>() {

            //            @Override
            public BooleanProperty call(Person person) {
                // given a person, we return the property that represents
                // whether or not they are invited. We can then bind to this
                // bidirectionally.
                return person.telecommuterProperty();
            }
        };
        listView.setCellFactory(CheckBoxListCell.forListView(getProperty));

        grid.add(listView, 0, 0);
        GridPane.setVgrow(listView, Priority.ALWAYS);
        GridPane.setHgrow(listView, Priority.ALWAYS);
        tab.setContent(grid);
    }

    private void buildTreeViewTab(Tab tab) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.setHgap(5);
        grid.setVgap(5);

        // --- TreeView 1
        // create the tree model
        final TreeItem<String> root = TreeModels.getFamiliesTree();
        root.setExpanded(true);

        // create the treeView
        final TreeView<String> treeView = new TreeView<String>();
        treeView.setRoot(root);

        // set the cell factory
        treeView.setCellFactory(CheckBoxTreeCell.<String> forTreeView());

        grid.add(treeView, 0, 0);
        GridPane.setVgrow(treeView, Priority.ALWAYS);
        GridPane.setHgrow(treeView, Priority.ALWAYS);


        // --- TreeView 2
        // create the tree model
        final TreeItem<String> root1 = TreeModels.getFamiliesTree();
        root1.setExpanded(true);

        // update tree model to be independent
        setIndependent(root1);

        // create the treeView
        final TreeView<String> treeView1 = new TreeView<String>();
        treeView1.setRoot(root1);

        // set the cell factory
        treeView1.setCellFactory(CheckBoxTreeCell.<String> forTreeView());

        grid.add(treeView1, 1, 0);
        GridPane.setVgrow(treeView1, Priority.ALWAYS);
        GridPane.setHgrow(treeView1, Priority.ALWAYS);


        tab.setContent(grid);
    }

    private void setIndependent(TreeItem<?> item) {
        if (item == null || !(item instanceof CheckBoxTreeItem))
            return;

        CheckBoxTreeItem checkItem = (CheckBoxTreeItem)item;
        checkItem.setIndependent(true);
        for (TreeItem child : item.getChildren()) {
            setIndependent(child);
        }
    }

    private void buildTableViewTab(Tab tab) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.setHgap(5);
        grid.setVgap(5);

        // create the tableview
        TableColumn<Person, Boolean> invitedColumn = new TableColumn<Person, Boolean>("Invited");

        invitedColumn.setCellValueFactory(new PropertyValueFactory<Person, Boolean>("invited"));

        TableColumn<Person, String> nameColumn = new TableColumn<Person, String>("First Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

        final TableView<Person> tableView = new TableView<Person>(data);
        tableView.getColumns().setAll(invitedColumn, nameColumn);

        // set the cell factory in the invited TableColumn
        invitedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(invitedColumn));

        grid.add(tableView, 0, 0);
        GridPane.setVgrow(tableView, Priority.ALWAYS);
        GridPane.setHgrow(tableView, Priority.ALWAYS);
        tab.setContent(grid);
    }
}