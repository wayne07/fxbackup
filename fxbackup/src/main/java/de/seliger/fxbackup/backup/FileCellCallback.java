package de.seliger.fxbackup.backup;

import java.io.File;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.util.Callback;


public class FileCellCallback implements Callback<CellDataFeatures<File, Boolean>, ObservableValue<Boolean>> {

    public ObservableValue<Boolean> call(CellDataFeatures<File, Boolean> param) {
        File file = param.getValue().getValue();
        String text = file.getParentFile() == null ? "/" : file.getName();

        //        param.getValue().

        return null;
        //        return new ReadOnlyObjectWrapper<Boolean>(text);
    }

}
