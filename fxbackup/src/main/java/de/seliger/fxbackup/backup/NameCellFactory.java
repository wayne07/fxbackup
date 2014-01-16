package de.seliger.fxbackup.backup;

import java.io.File;

import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.util.Callback;


public class NameCellFactory implements Callback<TreeTableColumn<File, String>, TreeTableCell<File, String>> {

    public TreeTableCell<File, String> call(TreeTableColumn<File, String> param) {
        return createTreeTableCell();

        //        return null;
    }

    private TreeTableCell<File, String> createTreeTableCell() {
        TreeTableCell<File, String> cell = new NameTreeTableCell();
        return cell;
    }

    private class NameTreeTableCell extends CheckBoxTreeTableCell<File, String> {

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            setText(item);
        }

    }

    //    lastModifiedColumn.setCellFactory(new Callback<TreeTableColumn<File, Date>, TreeTableCell<File, Date>>() {
    //
    //        public TreeTableCell<File, Date> call(TreeTableColumn<File, Date> p) {
    //            return new TreeTableCell<File, Date>() {
    //
    //                @Override
    //                protected void updateItem(Date item, boolean empty) {
    //                    super.updateItem(item, empty);
    //
    //                    if (item == null || empty) {
    //                        setText(null);
    //                    } else {
    //                        setText(dateformat.format(item));
    //                    }
    //                }
    //            };
    //        }
    //    });

}
