package de.seliger.sample.treetable.second;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Inventory {

	private final StringProperty name;
	private final ObjectProperty<Data> data;
	private final StringProperty notes;
	private boolean isSelected = false;

	public Inventory(String name, Data data, String notes) {
		this.name = new SimpleStringProperty(name);
		this.data = new SimpleObjectProperty<Data>(data);
		this.notes = new SimpleStringProperty(notes);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public StringProperty notesProperty() {
		return notes;
	}

	public ObjectProperty<Data> dataProperty() {
		return data;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

}
