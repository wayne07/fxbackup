package de.seliger.fxbackup.backup;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;

public class FileCheckBoxTreeItem extends CheckBoxTreeItem<FileNode> {

    private boolean isLeaf;
    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;

    public FileCheckBoxTreeItem(FileNode file) {
        super(file);
        setIndependent(false);

        selectedProperty().addListener(myStateChangeListener);

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


    private final ChangeListener<Boolean> myStateChangeListener = new ChangeListener<Boolean>() {

        @Override
        public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
            System.out.println("TreeItemFactory.myStateChangeListener.new ChangeListener() {...}.changed()  >>>>>>>>>>>>>>>>>>>>>>  ");
        }
    };


}
