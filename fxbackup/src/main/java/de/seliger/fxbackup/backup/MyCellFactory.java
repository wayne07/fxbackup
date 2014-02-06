package de.seliger.fxbackup.backup;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;

class MyCellFactory implements Callback<Integer, ObservableValue<Boolean>> {

    private final ObservableBooleanValue observableValue = createObservable();

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
        }
    };


}