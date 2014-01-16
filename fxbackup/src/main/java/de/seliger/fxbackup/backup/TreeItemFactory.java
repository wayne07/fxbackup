package de.seliger.fxbackup.backup;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBoxTreeItem;

public class TreeItemFactory {

    public static ObservableList<CheckBoxTreeItem<File>> buildChildren(CheckBoxTreeItem<File> TreeItem) {
        File f = TreeItem.getValue();
        if (f != null && f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                ObservableList<CheckBoxTreeItem<File>> children = FXCollections.observableArrayList();

                for (File childFile : files) {
                    children.add(createNode(childFile));
                }

                return children;
            }
        }

        return FXCollections.emptyObservableList();
    }

    public static CheckBoxTreeItem<File> createNode(final File f) {
        CheckBoxTreeItem<File> node = new FileCheckBoxTreeItem(f);
        return node;
    }

}