package de.seliger.fxbackup.backup;

import javafx.collections.ObservableList;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;

public class FileCheckBoxTreeItem extends CheckBoxTreeItem<FileNode> {

	private boolean isLeaf;
	private boolean isFirstTimeChildren = true;
	private boolean isFirstTimeLeaf = true;

	public FileCheckBoxTreeItem(FileNode file) {
		super(file);
		// super.setValue(file);
	}

	@Override
	public ObservableList<TreeItem<FileNode>> getChildren() {
		if (isFirstTimeChildren) {
			isFirstTimeChildren = false;
			super.getChildren().setAll(TreeItemFactory.buildChildren(this));
		}
		return super.getChildren();
	}

	@Override
	public boolean isLeaf() {
		if (isFirstTimeLeaf) {
			isFirstTimeLeaf = false;
			FileNode fileNode = getValue();
			isLeaf = !fileNode.isDirectory();
		}

		return isLeaf;
	}

}
