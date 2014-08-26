package de.seliger.sample.treetable.second;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;

public class MyCellFactory implements Callback<Integer, ObservableValue<Boolean>> {

	private ObservableBooleanValue observableValue;

	@Override
	public ObservableValue<Boolean> call(Integer param) {
		return new ObservableBooleanValue() {

			@Override
			public void removeListener(InvalidationListener listener) {
				// TODO Auto-generated method stub

			}

			@Override
			public void addListener(InvalidationListener listener) {
				// TODO Auto-generated method stub

			}

			@Override
			public void removeListener(ChangeListener<? super Boolean> listener) {
				// TODO Auto-generated method stub

			}

			@Override
			public Boolean getValue() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void addListener(ChangeListener<? super Boolean> listener) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean get() {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

}