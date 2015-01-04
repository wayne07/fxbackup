package de.seliger.sample.treetable.second;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.awt.*;

public class TriStateCheckBoxTreeTableCell extends TreeTableCell<Inventory, String> {

	private final static StringConverter<String> defaultStringConverter = new StringConverter<String>() {
		@Override
		public String toString(String t) {
			return t == null ? null : t.toString();
		}

		@Override
		public String fromString(String string) {
			return string;
		}
	};

	/***************************************************************************
	 * * Static cell factories *
	 * 
	 * @return *
	 **************************************************************************/

	public static Callback<TreeTableColumn<Inventory, String>, TreeTableCell<Inventory, String>> create() {
		return new Callback<TreeTableColumn<Inventory, String>, TreeTableCell<Inventory, String>>() {
			@Override
			public TreeTableCell<Inventory, String> call(TreeTableColumn<Inventory, String> list) {
				TriStateCheckBoxTreeTableCell treeTableCell = new TriStateCheckBoxTreeTableCell(new InventoryCallback(),
								defaultStringConverter);
				return treeTableCell;
			}
		};
	}

	/***************************************************************************
	 * * Fields * *
	 **************************************************************************/
	private final CheckBox checkBox;

	private boolean showLabel;

	private ObservableObjectValue<Inventory> inventoryObjectValue;

	/***************************************************************************
	 * * Constructors * *
	 **************************************************************************/

	// /**
	// * Creates a CheckBoxTreeTableCell with a custom string converter.
	// *
	// * @param getSelectedProperty
	// * A {@link Callback} that will return a {@link ObservableValue} given an index from the TreeTableColumn.
	// * @param converter
	// * A StringConverter that, given an object of type T, will return a String that can be used to represent the object visually.
	// */
//	public TriStateCheckBoxTreeTableCell(final Callback<Integer, ObservableValue<Boolean>> getSelectedProperty,
//	final StringConverter<String> converter) {
//	this.getStyleClass().add("check-box-tree-table-cell");
//	this.checkBox = new CheckBox();
//	this.checkBox.setAllowIndeterminate(true);
//
//	setGraphic(null);
//	setSelectedStateCallback(getSelectedProperty);
//	setConverter(converter);
//	}

	private TriStateCheckBoxTreeTableCell(final Callback<Integer, ObservableObjectValue<Inventory>> inventoryCallback,
					StringConverter<String> converter) {
		this.getStyleClass().add("check-box-tree-table-cell");
		this.checkBox = new CheckBox();
		this.checkBox.setAllowIndeterminate(false);

		setGraphic(null);
		setSelectedStateCallback(inventoryCallback);
		setConverter(converter);

		addEventHandler(ActionEvent.ACTION, eventHandler -> {
			System.out.println("event from: " + eventHandler.getSource());
			System.out.println("event type: "+eventHandler.getEventType());
		});
	}

	/***************************************************************************
	 * * Properties * *
	 **************************************************************************/

	// --- converter
	private final ObjectProperty<StringConverter<String>> converter = new SimpleObjectProperty<StringConverter<String>>(this, "converter") {
		@Override
		protected void invalidated() {
			updateShowLabel();
		}
	};

	/**
	 * The {@link StringConverter} property.
	 */
	public final ObjectProperty<StringConverter<String>> converterProperty() {
		return converter;
	}

	/**
	 * Sets the {@link StringConverter} to be used in this cell.
	 */
	public final void setConverter(StringConverter<String> value) {
		converterProperty().set(value);
	}

	/**
	 * Returns the {@link StringConverter} used in this cell.
	 */
	public final StringConverter<String> getConverter() {
		return converterProperty().get();
	}

	// --- selected state callback property
	private final ObjectProperty<Callback<Integer, ObservableObjectValue<Inventory>>> selectedStateCallback = new SimpleObjectProperty<Callback<Integer, ObservableObjectValue<Inventory>>>(
					this, "selectedStateCallback");

	/**
	 * Property representing the {@link Callback} that is bound to by the CheckBox shown on screen.
	 */
	public final ObjectProperty<Callback<Integer, ObservableObjectValue<Inventory>>> selectedStateCallbackProperty() {
		return selectedStateCallback;
	}

	/**
	 * Sets the {@link Callback} that is bound to by the CheckBox shown on screen.
	 */
	public final void setSelectedStateCallback(Callback<Integer, ObservableObjectValue<Inventory>> value) {
		selectedStateCallbackProperty().set(value);
	}

	/**
	 * Returns the {@link Callback} that is bound to by the CheckBox shown on screen.
	 */
	public final Callback<Integer, ObservableObjectValue<Inventory>> getSelectedStateCallback() {
		return selectedStateCallbackProperty().get();
	}

	/***************************************************************************
	 * * Public API * *
	 **************************************************************************/

	/** {@inheritDoc} */
	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			StringConverter<String> c = getConverter();

			if (showLabel) {
				setText(c.toString(item));
			}
			setGraphic(checkBox);

			// if (inventoryObjectValue instanceof InventoryProperty) {
			// checkBox.selectedProperty().unbindBidirectional((InventoryProperty) inventoryObjectValue);
			// }
			// ObservableValue<?> obsValue = getSelectedProperty();
			// if (obsValue instanceof InventoryProperty) {
			// inventoryObjectValue = (ObservableObjectValue<Inventory>) obsValue;
			// checkBox.selectedProperty().bindBidirectional((InventoryProperty) inventoryObjectValue);
			// }

			checkBox.disableProperty().bind(
							Bindings.not(getTreeTableView().editableProperty().and(getTableColumn().editableProperty())
											.and(editableProperty())));
		}
	}

	/***************************************************************************
	 * * Private implementation * *
	 **************************************************************************/

	private void updateShowLabel() {
		this.showLabel = converter != null;
		this.checkBox.setAlignment(showLabel ? Pos.CENTER_LEFT : Pos.CENTER);
	}

	private ObservableValue<?> getSelectedProperty() {
		ObservableValue<?> observableValue = getSelectedStateCallback() != null ? getSelectedStateCallback().call(getIndex()) : getTableColumn().getCellObservableValue(
				getIndex());
		return observableValue;
	}

}
