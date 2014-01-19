package de.seliger.fxbackup.backup;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;

public class FileBrowserTreeTableView {

	private static final int WIDTH_MODIFIED_COLUMN = 170;
	private static final int WIDTH_SIZE_COLUMN = 130;
	private static final int WIDTH_NAME_COLUMN = 400;

	private final DateFormat dateformat = SimpleDateFormat.getDateTimeInstance();

	private final TreeTableView<FileNode> treeTableView;

	public FileBrowserTreeTableView(TreeTableView<FileNode> treeTableView) {
		this.treeTableView = treeTableView;
	}

	@SuppressWarnings("unchecked")
	public void build() {
		TreeTableColumn<FileNode, String> nameColumn = createNameColumn();
		TreeTableColumn<FileNode, String> sizeColumn = createSizeColumn();
		// TreeTableColumn<FileNode, String> lastModifiedColumn = createModifiedColumn();

		TreeItem<FileNode> root = TreeItemFactory.createNode(new File("/tmp"));
		root.setExpanded(true);
		treeTableView.setShowRoot(true);
		treeTableView.setEditable(true);
		treeTableView.setRoot(root);
		treeTableView.getColumns().clear();
		treeTableView.getColumns().setAll(nameColumn, sizeColumn);
	}

	private TreeTableColumn<File, Date> createModifiedColumn() {
		TreeTableColumn<File, Date> lastModifiedColumn = new TreeTableColumn<File, Date>("Last Modified");
		lastModifiedColumn.setPrefWidth(WIDTH_MODIFIED_COLUMN);
		lastModifiedColumn.setCellValueFactory(new Callback<CellDataFeatures<File, Date>, ObservableValue<Date>>() {

			@Override
			public ObservableValue<Date> call(CellDataFeatures<File, Date> p) {
				return new ReadOnlyObjectWrapper<Date>(new Date(p.getValue().getValue().lastModified()));
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
							setText(dateformat.format(item));
						}
					}
				};
			}
		});
		return lastModifiedColumn;
	}

	private TreeTableColumn<FileNode, String> createSizeColumn() {
		TreeTableColumn<FileNode, String> sizeColumn = new TreeTableColumn<FileNode, String>("Size");
		sizeColumn.setMinWidth(WIDTH_SIZE_COLUMN);
		sizeColumn.setPrefWidth(WIDTH_SIZE_COLUMN);
		sizeColumn.setEditable(false);
		sizeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<FileNode, String>("fileSize"));

		// sizeColumn.setCellValueFactory(new Callback<CellDataFeatures<FileNode, String>, ObservableValue<String>>() {
		//
		// @Override
		// public ObservableValue<String> call(CellDataFeatures<FileNode, String> p) {
		// return new ReadOnlyObjectWrapper<String>(p.getValue().getValue().getFileSize());
		// }
		// });
		// sizeColumn.setCellFactory(new Callback<TreeTableColumn<FileNode, String>, TreeTableCell<FileNode, String>>() {
		//
		// @Override
		// public TreeTableCell<FileNode, String> call(final TreeTableColumn<FileNode, String> column) {
		// return new TreeTableCell<FileNode, String>() {
		//
		// // @Override
		// protected void updateItem(FileNode item, boolean empty) {
		// super.updateItem(item.getFileSize().toString(), empty);
		//
		// // TreeTableView<FileNode> treeTable = column.getTreeTableView();
		// //
		// // // if the File is a directory, it has no size...
		// // int cellIndexInParent = getIndex();
		// // // System.out.println("cellIndexInParent: " + cellIndexInParent);
		// // TreeItem<File> treeItem = treeTable.getTreeItem(cellIndexInParent);
		// // System.out.println("item: " + item);
		// // System.out.println("empty: " + empty);
		// // System.out.println("treeItem: " + treeItem);
		// // System.out.println("treeItem.getValue(): " + treeItem.getValue());
		// // if (item == null || empty || treeItem == null || treeItem.getValue() == null || treeItem.getValue().isDirectory()) {
		// if (item == null || empty || item.isDirectory()) {
		// setText(null);
		// } else {
		// setText(item.getFileSize().toString());
		// }
		// // }
		// }
		// };
		// }
		// });
		// sizeColumn.setComparator(new Comparator<String>() {
		//
		// @Override
		// public int compare(FileNode f1, FileNode f2) {
		// long s1 = f1.isDirectory() ? 0 : f1.getLength();
		// long s2 = f2.isDirectory() ? 0 : f2.getLength();
		// long result = s1 - s2;
		// if (result < 0) {
		// return -1;
		// } else if (result == 0) {
		// return 0;
		// } else {
		// return 1;
		// }
		// }
		// });
		return sizeColumn;
	}

	private TreeTableColumn<FileNode, String> createNameColumn() {
		TreeTableColumn<FileNode, String> nameColumn = new TreeTableColumn<FileNode, String>("Filename");
		nameColumn.setMinWidth(WIDTH_NAME_COLUMN);
		nameColumn.setPrefWidth(WIDTH_NAME_COLUMN);
		nameColumn.setEditable(true);
		nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<FileNode, String>("filename"));

		nameColumn.setCellFactory(CheckBoxTreeTableCell.<FileNode, String> forTreeTableColumn(new MyCellFactory(), true));

		// Callback<CellDataFeatures<File, String>, ObservableValue<String>> callbackForCellValue = new FileCellCallback();
		// nameColumn.setCellValueFactory(callbackForCellValue);

		// Callback<Integer, ObservableValue<Boolean>> callback = createCallback();
		//
		// Callback<TreeTableColumn<File, String>, TreeTableCell<File, String>> callbackForTreeTableColumn = CheckBoxTreeTableCell.forTreeTableColumn(callback);
		// nameColumn.setCellFactory(callbackForTreeTableColumn);

		return nameColumn;
	}

	// private Callback<Integer, ObservableValue<Boolean>> createCallback() {
	// Callback<Integer, ObservableValue<Boolean>> callback = new Callback<Integer, ObservableValue<Boolean>>() {
	//
	// @Override
	// public ObservableValue<Boolean> call(Integer param) {
	// ObservableValue<Boolean> value = new ObservableValue<Boolean>() {
	//
	// @Override
	// public void addListener(InvalidationListener listener) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void removeListener(InvalidationListener listener) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void addListener(ChangeListener<? super Boolean> listener) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void removeListener(ChangeListener<? super Boolean> listener) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public Boolean getValue() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// };
	// return value;
	// }
	//
	// };
	// return callback;
	// }

}