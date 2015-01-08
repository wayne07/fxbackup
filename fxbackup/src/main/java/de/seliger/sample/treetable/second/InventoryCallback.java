package de.seliger.sample.treetable.second;

import javafx.beans.value.ObservableObjectValue;
import javafx.util.Callback;

public class InventoryCallback implements Callback<Integer, ObservableObjectValue<Inventory>> {

	@Override
	public ObservableObjectValue<Inventory> call(Integer param) {
		return new ObservableInventoryValue(null);
	}

}