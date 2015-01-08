package de.seliger.treetable.demo;

import java.io.File;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Date;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TreeTableFxDemo extends Application {

    private DateFormat df = DateFormat.getDateInstance();
    private NumberFormat nf = NumberFormat.getNumberInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TreeTableViewDemoApp");
        primaryStage.setScene(new Scene(buildFileBrowserTreeTableView()));
        primaryStage.show();
    }

    private TreeTableView buildFileBrowserTreeTableView() {
        TreeItem<File> root = createNode(new File("/home/js/tmp"));
        root.setExpanded(true);

        final TreeTableView<File> treeTableView = new TreeTableView<>();
        treeTableView.setShowRoot(true);
        treeTableView.setRoot(root);
        treeTableView.setEditable(true);
        treeTableView.setPrefSize(800, 600);

        TreeTableColumn<File, String> nameColumn = createNameColumn();
        TreeTableColumn<File, File> sizeColumn = createSizeColumn();
        TreeTableColumn<File, Date> lastModifiedColumn = createModifiedColumn();
        TreeTableColumn<File, Boolean> selectedColumn = createSelectedColumn();

        treeTableView.getColumns().setAll(nameColumn, sizeColumn, lastModifiedColumn, selectedColumn);

        return treeTableView;
    }

    private TreeTableColumn<File, Boolean> createSelectedColumn() {
        TreeTableColumn<File, Boolean> column = new TreeTableColumn<>("Selected");
        column.setPrefWidth(100);
        column.setEditable(true);
//        column.setCellValueFactory(new TreeItemPropertyValueFactory<File, Boolean>("selected"));
        Callback<Integer, ObservableValue<Boolean>> getSelectedProperty = new Callback<Integer, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(Integer param) {
                // TODO Auto-generated method stub
                return new ObservableValue<Boolean>() {
                    @Override
                    public void addListener(ChangeListener<? super Boolean> listener) {

                    }

                    @Override
                    public void removeListener(ChangeListener<? super Boolean> listener) {

                    }

                    @Override
                    public Boolean getValue() {
                        return false;
                    }

                    @Override
                    public void addListener(InvalidationListener listener) {

                    }

                    @Override
                    public void removeListener(InvalidationListener listener) {

                    }
                };
            }
        };
        column.setCellFactory(CheckBoxTreeTableCell.<File, Boolean> forTreeTableColumn(getSelectedProperty, true));

        return column;
    }

    private TreeTableColumn<File, String> createNameColumn() {
        // --- name column
        TreeTableColumn<File, String> nameColumn = new TreeTableColumn<>("Name");
        nameColumn.setPrefWidth(300);
        nameColumn.setCellValueFactory(getCellValueFactory());
        return nameColumn;
    }

    private TreeTableColumn<File, Date> createModifiedColumn() {
        // --- modified column
        TreeTableColumn<File, Date> lastModifiedColumn = new TreeTableColumn<>("Last Modified");
        lastModifiedColumn.setPrefWidth(130);
        lastModifiedColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<File, Date>, ObservableValue<Date>>() {
            @Override
            public ObservableValue<Date> call(TreeTableColumn.CellDataFeatures<File, Date> p) {
                return new ReadOnlyObjectWrapper<>(new Date(p.getValue().getValue().lastModified()));
            }
        });
        lastModifiedColumn.setCellFactory(new Callback<TreeTableColumn<File, Date>, TreeTableCell<File, Date>>() {
            @Override
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
        return lastModifiedColumn;
    }

    private TreeTableColumn<File, File> createSizeColumn() {
        // --- size column
        TreeTableColumn<File, File> sizeColumn = new TreeTableColumn<>("Size");
        sizeColumn.setPrefWidth(100);
        sizeColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<File, File>, ObservableValue<File>>() {
            @Override
            public ObservableValue<File> call(TreeTableColumn.CellDataFeatures<File, File> p) {
                return new ReadOnlyObjectWrapper<>(p.getValue().getValue());
            }
        });
        sizeColumn.setCellFactory(new Callback<TreeTableColumn<File, File>, TreeTableCell<File, File>>() {
            @Override
            public TreeTableCell<File, File> call(final TreeTableColumn<File, File> p) {
                return new TreeTableCell<File, File>() {
                    @Override
                    protected void updateItem(File item, boolean empty) {
                        super.updateItem(item, empty);

                        TreeTableView treeTable = p.getTreeTableView();

                        int treeItemCount = treeTable.getExpandedItemCount();

                        // if the File is a directory, it has no size...
//                        if (getIndex() >= treeTable.impl_getTreeItemCount()) {
                        if (getIndex() >= treeItemCount) {
                            setText(null);
                        } else {
                            TreeItem<File> treeItem = treeTable.getTreeItem(getIndex());
                            if (item == null || empty || treeItem == null ||
                                    treeItem.getValue() == null || treeItem.getValue().isDirectory()) {
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
            @Override
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
        return sizeColumn;
    }

    private Callback<TreeTableColumn.CellDataFeatures<File, String>, ObservableValue<String>> getCellValueFactory() {
        return new Callback<TreeTableColumn.CellDataFeatures<File, String>, ObservableValue<String>>() {

            @Override public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<File, String> p) {
                File f = p.getValue().getValue();
                String text = f.getParentFile() == null ? "/" : f.getName();
                return new ReadOnlyObjectWrapper<>(text);
            }

        };
    }

    private TreeItem<File> createNode(final File f) {
        final TreeItem<File> node = new TreeItem<File>(f) {
            private boolean isLeaf;
            private boolean isFirstTimeChildren = true;
            private boolean isFirstTimeLeaf = true;

            @Override public ObservableList<TreeItem<File>> getChildren() {
                if (isFirstTimeChildren) {
                    isFirstTimeChildren = false;
                    super.getChildren().setAll(buildChildren(this));
                }
                return super.getChildren();
            }

            @Override public boolean isLeaf() {
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
