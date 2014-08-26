package de.seliger.sample.treetable.second;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;

public class ObservableInventoryValue implements ObservableObjectValue<Inventory> {

	private final Inventory inventory;

	public ObservableInventoryValue(Inventory inventory) {
		this.inventory = inventory;
	}

	@Override
	public void addListener(ChangeListener<? super Inventory> listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListener(ChangeListener<? super Inventory> listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Inventory getValue() {
		return get();
	}

	@Override
	public void addListener(InvalidationListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Inventory get() {
		return inventory;
	}

}
