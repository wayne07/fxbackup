package de.seliger.fxbackup.backup;

import java.io.File;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.util.Callback;


public class FileCellCallback implements Callback<CellDataFeatures<File, String>, ObservableValue<String>> {

    public ObservableValue<String> call(CellDataFeatures<File, String> param) {
        File file = param.getValue().getValue();
        final String text = file.getParentFile() == null ? "/" : file.getName();

        //        param.getValue().

        ObservableValue<String> value = new ObservableValue<String>() {

            public void addListener(InvalidationListener listener) {
                // TODO Auto-generated method stub

            }

            public void removeListener(InvalidationListener listener) {
                // TODO Auto-generated method stub

            }

            public void addListener(ChangeListener<? super String> listener) {
                // TODO Auto-generated method stub

            }

            public void removeListener(ChangeListener<? super String> listener) {
                // TODO Auto-generated method stub

            }

            public String getValue() {
                return text;
            }
        };
        return value;
        //        return new ReadOnlyObjectWrapper<Boolean>(text);
    }

}
