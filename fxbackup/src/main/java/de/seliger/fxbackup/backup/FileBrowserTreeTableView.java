package de.seliger.fxbackup.backup;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;

public class FileBrowserTreeTableView {

    private static final int WIDTH_MODIFIED_COLUMN = 170;
    private static final int WIDTH_SIZE_COLUMN = 130;
    private static final int WIDTH_NAME_COLUMN = 400;

    private final DateFormat dateformat = SimpleDateFormat.getDateTimeInstance();

    private final TreeTableView<File> treeTableView;

    public FileBrowserTreeTableView(TreeTableView<File> treeTableView) {
        this.treeTableView = treeTableView;
    }

    @SuppressWarnings("unchecked")
    public void build() {
        CheckBoxTreeItem<File> root = TreeItemFactory.createNode(new File("/"));
        root.setExpanded(true);

        treeTableView.setShowRoot(true);
        treeTableView.setRoot(root);

        TreeTableColumn<File, String> nameColumn = createNameColumn();
        TreeTableColumn<File, File> sizeColumn = createSizeColumn();
        TreeTableColumn<File, Date> lastModifiedColumn = createModifiedColumn();

        treeTableView.getColumns().clear();
        treeTableView.getColumns().setAll(nameColumn, sizeColumn, lastModifiedColumn);

    }

    private TreeTableColumn<File, Date> createModifiedColumn() {
        TreeTableColumn<File, Date> lastModifiedColumn = new TreeTableColumn<File, Date>("Last Modified");
        lastModifiedColumn.setPrefWidth(WIDTH_MODIFIED_COLUMN);
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
                            setText(dateformat.format(item));
                        }
                    }
                };
            }
        });
        return lastModifiedColumn;
    }

    private TreeTableColumn<File, File> createSizeColumn() {
        TreeTableColumn<File, File> sizeColumn = new TreeTableColumn<File, File>("Size");
        sizeColumn.setPrefWidth(WIDTH_SIZE_COLUMN);
        sizeColumn.setCellValueFactory(new Callback<CellDataFeatures<File, File>, ObservableValue<File>>() {

            public ObservableValue<File> call(CellDataFeatures<File, File> p) {
                return new ReadOnlyObjectWrapper<File>(p.getValue().getValue());
            }
        });
        sizeColumn.setCellFactory(new Callback<TreeTableColumn<File, File>, TreeTableCell<File, File>>() {

            public TreeTableCell<File, File> call(final TreeTableColumn<File, File> column) {
                return new TreeTableCell<File, File>() {

                    @Override
                    protected void updateItem(File item, boolean empty) {
                        super.updateItem(item, empty);

                        TreeTableView<File> treeTable = column.getTreeTableView();

                        // if the File is a directory, it has no size...
                        int cellIndexInParent = getIndex();
                        //                        System.out.println("cellIndexInParent: " + cellIndexInParent);
                        TreeItem<File> treeItem = treeTable.getTreeItem(cellIndexInParent);
                        //                        System.out.println("item: " + item);
                        //                        System.out.println("empty: " + empty);
                        //                        System.out.println("treeItem: " + treeItem);
                        //                            System.out.println("treeItem.getValue(): " + treeItem.getValue());
                        if (item == null || empty || treeItem == null || treeItem.getValue() == null || treeItem.getValue().isDirectory()) {
                            setText(null);
                        } else {
                            long length = item.length();
                            setText(new FileSizeFormatter().format(length));
                        }
                        //                        }
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
        return sizeColumn;
    }

    private TreeTableColumn<File, String> createNameColumn() {
        TreeTableColumn<File, String> nameColumn = new TreeTableColumn<File, String>("Name");
        nameColumn.setPrefWidth(WIDTH_NAME_COLUMN);

        Callback<CellDataFeatures<File, String>, ObservableValue<String>> callbackForCellValue = new FileCellCallback();
        nameColumn.setCellValueFactory(callbackForCellValue);

        //        Callback<Integer, ObservableValue<Boolean>> callback = createCallback();
        //
        //        Callback<TreeTableColumn<File, String>, TreeTableCell<File, String>> callbackForTreeTableColumn = CheckBoxTreeTableCell.forTreeTableColumn(callback);
        //        nameColumn.setCellFactory(callbackForTreeTableColumn);

        return nameColumn;
    }

    private Callback<Integer, ObservableValue<Boolean>> createCallback() {
        Callback<Integer, ObservableValue<Boolean>> callback = new Callback<Integer, ObservableValue<Boolean>>() {

            public ObservableValue<Boolean> call(Integer param) {
                ObservableValue<Boolean> value = new ObservableValue<Boolean>() {

                    public void addListener(InvalidationListener listener) {
                        // TODO Auto-generated method stub

                    }

                    public void removeListener(InvalidationListener listener) {
                        // TODO Auto-generated method stub

                    }

                    public void addListener(ChangeListener<? super Boolean> listener) {
                        // TODO Auto-generated method stub

                    }

                    public void removeListener(ChangeListener<? super Boolean> listener) {
                        // TODO Auto-generated method stub

                    }

                    public Boolean getValue() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                };
                return value;
            }

        };
        return callback;
    }
}