package de.seliger.fxbackup.backup;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;

public class FileBrowserTreeTableView {

    private static final int WIDTH_MODIFIED_COLUMN = 170;
    private static final int WIDTH_SIZE_COLUMN = 130;
    private static final int WIDTH_NAME_COLUMN = 400;

    private final TreeTableView<FileNode> treeTableView;

    public FileBrowserTreeTableView(TreeTableView<FileNode> treeTableView) {
        this.treeTableView = treeTableView;
    }

    @SuppressWarnings("unchecked")
    public void build() {
        TreeTableColumn<FileNode, String> nameColumn = createNameColumn();
        TreeTableColumn<FileNode, String> sizeColumn = createSizeColumn();
        TreeTableColumn<FileNode, String> lastModifiedColumn = createModifiedColumn();
        TreeTableColumn<FileNode, Boolean> selectedColumn = createSelectedColumn();

        TreeItem<FileNode> root = TreeItemFactory.createNode(new File("/tmp"));
        root.setExpanded(true);

        // Set a ChangeListener to handle events that occur with a Treeitem
        // is selected
        //        treeTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
        //
        //            public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> oldItem, TreeItem<String> newItem) {
        //                // Gets fired only on selection of tree item
        //                // Need to get fired on selection of check box too
        //                // Select the respective checkbox on selection of tree item
        //            }
        //        });
        treeTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<FileNode>>() {

            @Override
            public void changed(ObservableValue<? extends TreeItem<FileNode>> observable, TreeItem<FileNode> oldValue, TreeItem<FileNode> newValue) {
                System.out.println("Node '" + newValue.getValue().getFilename() + "' selected");
            }
        });

        treeTableView.setShowRoot(true);
        treeTableView.setEditable(true);
        treeTableView.setRoot(root);
        treeTableView.getColumns().clear();
        treeTableView.getColumns().setAll(nameColumn, sizeColumn, lastModifiedColumn, selectedColumn);
    }

    private TreeTableColumn<FileNode, String> createNameColumn() {
        TreeTableColumn<FileNode, String> nameColumn = new TreeTableColumn<FileNode, String>("Filename");
        nameColumn.setMinWidth(WIDTH_NAME_COLUMN);
        nameColumn.setPrefWidth(WIDTH_NAME_COLUMN);
        nameColumn.setEditable(true);
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<FileNode, String>("filename"));
        nameColumn.setCellFactory(CheckBoxTreeTableCell.<FileNode, String> forTreeTableColumn(new MyCellFactory(treeTableView), true));

        return nameColumn;
    }

    private TreeTableColumn<FileNode, String> createModifiedColumn() {
        TreeTableColumn<FileNode, String> lastModifiedColumn = new TreeTableColumn<FileNode, String>("Last Modified");
        lastModifiedColumn.setPrefWidth(WIDTH_MODIFIED_COLUMN);
        lastModifiedColumn.setEditable(false);
        lastModifiedColumn.setCellValueFactory(new TreeItemPropertyValueFactory<FileNode, String>("modifiedDate"));

        return lastModifiedColumn;
    }

    private TreeTableColumn<FileNode, String> createSizeColumn() {
        TreeTableColumn<FileNode, String> sizeColumn = new TreeTableColumn<FileNode, String>("Size");
        sizeColumn.setMinWidth(WIDTH_SIZE_COLUMN);
        sizeColumn.setPrefWidth(WIDTH_SIZE_COLUMN);
        sizeColumn.setEditable(false);
        //        sizeColumn.setStyle("RIGHT");
        sizeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<FileNode, String>("fileSize"));

        return sizeColumn;
    }

    private TreeTableColumn<FileNode, Boolean> createSelectedColumn() {
        TreeTableColumn<FileNode, Boolean> column = new TreeTableColumn<FileNode, Boolean>("Selected");
        column.setPrefWidth(WIDTH_SIZE_COLUMN);
        column.setEditable(true);
        column.setCellValueFactory(new TreeItemPropertyValueFactory<FileNode, Boolean>("selected"));
        Callback<Integer, ObservableValue<Boolean>> getSelectedProperty = new Callback<Integer, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(Integer param) {
                // TODO Auto-generated method stub
                return null;
            }
        };
        column.setCellFactory(CheckBoxTreeTableCell.<FileNode, Boolean> forTreeTableColumn(getSelectedProperty, false));

        //        invitedColumn.setCellValueFactory(new PropertyValueFactory<Person, Boolean>("invited"));
        //        invitedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(invitedColumn));
        return column;
    }

}
