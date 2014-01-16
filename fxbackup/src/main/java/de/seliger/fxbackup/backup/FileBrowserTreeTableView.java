package de.seliger.fxbackup.backup;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
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

        TreeTableColumn<File, Boolean> nameColumn = createNameColumn();
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

    private TreeTableColumn<File, Boolean> createNameColumn() {
        TreeTableColumn<File, Boolean> nameColumn = new TreeTableColumn<File, Boolean>("Name");
        nameColumn.setPrefWidth(WIDTH_NAME_COLUMN);


        Callback<CellDataFeatures<File, Boolean>, ObservableValue<Boolean>> callbackForCellValue = new FileCellCallback();
        nameColumn.setCellValueFactory(callbackForCellValue);

        Callback<TreeTableColumn<File, Boolean>, TreeTableCell<File, Boolean>> callbackForTreeTableColumn = CheckBoxTreeTableCell.forTreeTableColumn(nameColumn);
        nameColumn.setCellFactory(callbackForTreeTableColumn);
        //        nameColumn.setCellFactory(new NameCellFactory());
        return nameColumn;
    }

    //    private final Callback<TreeTableColumn<File, String>, TreeTableCell<File, String>> nameCellFactory = new NameCellFactory();

    //    lastModifiedColumn.setCellValueFactory(new Callback<CellDataFeatures<File, Date>, ObservableValue<Date>>() {
    //
    //        public ObservableValue<Date> call(CellDataFeatures<File, Date> p) {
    //            return new ReadOnlyObjectWrapper<Date>(new Date(p.getValue().getValue().lastModified()));
    //        }
    //    });

}