package de.seliger.fxbackup.backup;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.CheckBoxTreeItem.TreeModificationEvent;
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

    public static CheckBoxTreeItem<FileNode> createNode(final File file) {
        CheckBoxTreeItem<FileNode> node = new FileCheckBoxTreeItem(new FileNode(file));

        node.addEventHandler(CheckBoxTreeItem.<FileNode> checkBoxSelectionChangedEvent(), new EventHandler<TreeModificationEvent<FileNode>>() {

            @Override
            public void handle(TreeModificationEvent<FileNode> event) {
                System.out.println("TreeItemFactory.createNode(...).new EventHandler() {...}.handle()");

            }
        });

        return node;
    }

}