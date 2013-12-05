package de.seliger.fxbackup.backup;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class BackupViewController implements Initializable {

    @FXML
    private TreeTableView<File> backupTree;

    @FXML
    private AnchorPane treePane;

    public void initialize(URL location, ResourceBundle resources) {
        buildFileBrowserTreeTableView(backupTree);
    }


    @SuppressWarnings("unchecked")
    private TreeTableView<File> buildFileBrowserTreeTableView(TreeTableView<File> backupTree) {
        TreeItem<File> root = createNode(new File("/"));
        root.setExpanded(true);

        //        final TreeTableView<File> treeTableView = new TreeTableView<File>();
        backupTree.setShowRoot(true);
        backupTree.setRoot(root);

        // --- name column
        TreeTableColumn<File, String> nameColumn = new TreeTableColumn<File, String>("Name");
        nameColumn.setPrefWidth(400);
        nameColumn.setCellValueFactory(new Callback<CellDataFeatures<File, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(CellDataFeatures<File, String> p) {
                File f = p.getValue().getValue();
                String text = f.getParentFile() == null ? "/" : f.getName();
                return new ReadOnlyObjectWrapper<String>(text);
            }
        });

        // --- size column
        TreeTableColumn<File, File> sizeColumn = new TreeTableColumn<File, File>("Size");
        sizeColumn.setPrefWidth(130);
        sizeColumn.setCellValueFactory(new Callback<CellDataFeatures<File, File>, ObservableValue<File>>() {

            public ObservableValue<File> call(CellDataFeatures<File, File> p) {
                return new ReadOnlyObjectWrapper<File>(p.getValue().getValue());
            }
        });
        sizeColumn.setCellFactory(new Callback<TreeTableColumn<File, File>, TreeTableCell<File, File>>() {

            public TreeTableCell<File, File> call(final TreeTableColumn<File, File> p) {
                return new TreeTableCell<File, File>() {

                    @Override
                    protected void updateItem(File item, boolean empty) {
                        super.updateItem(item, empty);

                        TreeTableView treeTable = p.getTreeTableView();

                        // if the File is a directory, it has no size...
                        if (getIndex() >= treeTable.getColumns().size()) { //impl_getTreeItemCount()) {
                            setText(null);
                        } else {
                            TreeItem<File> treeItem = treeTable.getTreeItem(getIndex());
                            if (item == null || empty || treeItem == null || treeItem.getValue() == null || treeItem.getValue().isDirectory()) {
                                setText(null);
                            } else {
                                setText(nf.format(item.length()) + " KB");
                            }
                        }
                    }
                };
            }
        });
        sizeColumn.setComparator(new Comparator<File>() {

            public int compare(File f1, File f2) {
                long s1 = f1.isDirectory() ? 0 : f1.length();
                long s2 = f2.isDirectory() ? 0 : f2.length();
                long result = s1 - s2;
                if (result < 0) {
                    return -1;
                } else if (result == 0) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        // --- modified column
        TreeTableColumn<File, Date> lastModifiedColumn = new TreeTableColumn<File, Date>("Last Modified");
        lastModifiedColumn.setPrefWidth(170);
        lastModifiedColumn.setCellValueFactory(new Callback<CellDataFeatures<File, Date>, ObservableValue<Date>>() {

            public ObservableValue<Date> call(CellDataFeatures<File, Date> p) {
                return new ReadOnlyObjectWrapper<Date>(new Date(p.getValue().getValue().lastModified()));
            }
        });
        lastModifiedColumn.setCellFactory(new Callback<TreeTableColumn<File, Date>, TreeTableCell<File, Date>>() {

            public TreeTableCell<File, Date> call(TreeTableColumn<File, Date> p) {
                return new TreeTableCell<File, Date>() {

                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(df.format(item));
                        }
                    }
                };
            }
        });

        backupTree.getColumns().setAll(nameColumn, sizeColumn, lastModifiedColumn);

        return backupTree;
    }

    private final NumberFormat nf = NumberFormat.getInstance();
    private final DateFormat df = SimpleDateFormat.getDateInstance();// new SimpleDateFormat("dd.mm.yyyy");

    private TreeItem<File> createNode(final File f) {
        final TreeItem<File> node = new TreeItem<File>(f) {

            private boolean isLeaf;
            private boolean isFirstTimeChildren = true;
            private boolean isFirstTimeLeaf = true;

            @Override
            public ObservableList<TreeItem<File>> getChildren() {
                if (isFirstTimeChildren) {
                    isFirstTimeChildren = false;
                    super.getChildren().setAll(buildChildren(this));
                }
                return super.getChildren();
            }

            @Override
            public boolean isLeaf() {
                if (isFirstTimeLeaf) {
                    isFirstTimeLeaf = false;
                    File f = getValue();
                    isLeaf = f.isFile();
                }

                return isLeaf;
            }
        };
        return node;
    }

    private ObservableList<TreeItem<File>> buildChildren(TreeItem<File> TreeItem) {
        File f = TreeItem.getValue();
        if (f != null && f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();

                for (File childFile : files) {
                    children.add(createNode(childFile));
                }

                return children;
            }
        }

        return FXCollections.emptyObservableList();
    }
}
