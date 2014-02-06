package de.seliger.fxbackup.backup;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;

class MyCellFactory implements Callback<Integer, ObservableValue<Boolean>> {

    private final ObservableBooleanValue observableValue = createObservable();
    private static TreeTableView<FileNode> treeTableView;

    public MyCellFactory(TreeTableView<FileNode> treeTableView) {
        this.treeTableView = treeTableView;
    }

    @Override
    public ObservableValue<Boolean> call(Integer index) {
        System.out.println("Zeile im Baum: " + index);
        return observableValue;
    }

    private ObservableBooleanValue createObservable() {
        ObservableBooleanValue observableValue = new SimpleBooleanProperty(false);
        observableValue.addListener(myStateChangeListener);

        return observableValue;
    }

    private static final ChangeListener<Boolean> myStateChangeListener = new ChangeListener<Boolean>() {

        @Override
        public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
            System.out.println(ov);
            System.out.println(oldVal);
            System.out.println(newVal);
            System.out.println("XXXXXXXXXXXXXXX  TreeItemFactory.myStateChangeListener.new ChangeListener() {...}.changed()  ");
            TreeItem<FileNode> treeItem = treeTableView.getTreeItem(2);
            updateState(treeItem);
        }
    };

    private static void updateState(TreeItem<FileNode> treeItem) {
        // toggle parent (recursively up to root)
        updateUpwards(treeItem);

        // toggle children
        //        updateDownwards();
    }

    private static void updateUpwards(TreeItem<FileNode> treeItem) {
        if ( !(treeItem.getParent() instanceof CheckBoxTreeItem))
            return;

        CheckBoxTreeItem<?> parent = (CheckBoxTreeItem<?>)treeItem.getParent();
        int selectCount = 0;
        int indeterminateCount = 0;
        for (TreeItem<?> child : parent.getChildren()) {
            if ( !(child instanceof CheckBoxTreeItem))
                continue;

            CheckBoxTreeItem<?> cbti = (CheckBoxTreeItem<?>)child;

            selectCount += cbti.isSelected() && !cbti.isIndeterminate() ? 1 : 0;
            indeterminateCount += cbti.isIndeterminate() ? 1 : 0;
        }

        if (selectCount == parent.getChildren().size()) {
            parent.setSelected(true);
            parent.setIndeterminate(false);
        } else if (selectCount == 0 && indeterminateCount == 0) {
            parent.setSelected(false);
            parent.setIndeterminate(false);
        } else {
            parent.setIndeterminate(true);
        }
    }

    //    private void updateDownwards() {
    //        // If this node is not a leaf, we also put all
    //        // children into the same state as this branch
    //        if (! isLeaf()) {
    //            for (TreeItem<T> child : getChildren()) {
    //                if (child instanceof CheckBoxTreeItem) {
    //                    CheckBoxTreeItem<T> cbti = ((CheckBoxTreeItem<T>) child);
    //                    cbti.setSelected(isSelected());
    //                }
    //            }
    //        }
    //    }

}