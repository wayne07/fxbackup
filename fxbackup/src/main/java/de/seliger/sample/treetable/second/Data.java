package de.seliger.sample.treetable.second;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Data {

	private final StringProperty data;

	public Data(String data) {
		this.data = new SimpleStringProperty(data);
	}

	public StringProperty dataProperty() {
		return data;
	}

	@Override
	public String toString() {
		return data.get();
	}
}
