package de.seliger.fxbackup.backup;

import java.io.File;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

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

		TreeItem<FileNode> root = TreeItemFactory.createNode(new File("/tmp"));
		root.setExpanded(true);
		treeTableView.setShowRoot(true);
		treeTableView.setEditable(true);
		treeTableView.setRoot(root);
		treeTableView.getColumns().clear();
		treeTableView.getColumns().setAll(nameColumn, sizeColumn, lastModifiedColumn);
	}

	private TreeTableColumn<FileNode, String> createNameColumn() {
		TreeTableColumn<FileNode, String> nameColumn = new TreeTableColumn<FileNode, String>("Filename");
		nameColumn.setMinWidth(WIDTH_NAME_COLUMN);
		nameColumn.setPrefWidth(WIDTH_NAME_COLUMN);
		nameColumn.setEditable(true);
		nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<FileNode, String>("filename"));
		nameColumn.setCellFactory(CheckBoxTreeTableCell.<FileNode, String> forTreeTableColumn(new MyCellFactory(), true));

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
		// sizeColumn.setStyle("RIGHT");
		sizeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<FileNode, String>("fileSize"));

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

}