package de.seliger.fxbackup.backup;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class TreeItemFactory {

	public static ObservableList<TreeItem<FileNode>> buildChildren(TreeItem<FileNode> TreeItem) {
		FileNode fileNode = TreeItem.getValue();
		if (fileNode != null && fileNode.isDirectory()) {
			File[] files = fileNode.listFiles();
			if (files != null) {
				ObservableList<TreeItem<FileNode>> children = FXCollections.observableArrayList();

				for (File childFile : files) {
					children.add(createNode(childFile));
				}

				return children;
			}
		}

		return FXCollections.emptyObservableList();
	}

	public static TreeItem<FileNode> createNode(final File file) {
		TreeItem<FileNode> node = new FileCheckBoxTreeItem(new FileNode(file));
		return node;
	}

}