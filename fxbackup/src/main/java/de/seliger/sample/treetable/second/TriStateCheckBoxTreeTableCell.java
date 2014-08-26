package de.seliger.sample.treetable.second;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;
import javafx.util.StringConverter;

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
				TriStateCheckBoxTreeTableCell treeTableCell = new TriStateCheckBoxTreeTableCell(new MyCellFactory(), defaultStringConverter);
				return treeTableCell;
			}
		};
	}

	/***************************************************************************
	 * * Fields * *
	 **************************************************************************/
	private final CheckBox checkBox;

	private boolean showLabel;

	private ObservableValue<Boolean> booleanProperty;

	/***************************************************************************
	 * * Constructors * *
	 **************************************************************************/

	/**
	 * Creates a default CheckBoxTreeTableCell.
	 */
	public TriStateCheckBoxTreeTableCell() {
		this(null, null);
	}

	/**
	 * Creates a default CheckBoxTreeTableCell with a custom {@link Callback} to retrieve an ObservableValue for a given cell index.
	 * 
	 * @param getSelectedProperty
	 *            A {@link Callback} that will return an {@link ObservableValue} given an index from the TreeTableColumn.
	 */
	public TriStateCheckBoxTreeTableCell(final Callback<Integer, ObservableValue<Boolean>> getSelectedProperty) {
		this(getSelectedProperty, null);
	}

	/**
	 * Creates a CheckBoxTreeTableCell with a custom string converter.
	 * 
	 * @param getSelectedProperty
	 *            A {@link Callback} that will return a {@link ObservableValue} given an index from the TreeTableColumn.
	 * @param converter
	 *            A StringConverter that, given an object of type T, will return a String that can be used to represent the object visually.
	 */
	public TriStateCheckBoxTreeTableCell(final Callback<Integer, ObservableValue<Boolean>> getSelectedProperty,
					final StringConverter<String> converter) {
		this.getStyleClass().add("check-box-tree-table-cell");
		this.checkBox = new CheckBox();
		this.checkBox.setAllowIndeterminate(true);

		setGraphic(null);
		setSelectedStateCallback(getSelectedProperty);
		setConverter(converter);
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
	private final ObjectProperty<Callback<Integer, ObservableValue<Boolean>>> selectedStateCallback = new SimpleObjectProperty<Callback<Integer, ObservableValue<Boolean>>>(
					this, "selectedStateCallback");

	/**
	 * Property representing the {@link Callback} that is bound to by the CheckBox shown on screen.
	 */
	public final ObjectProperty<Callback<Integer, ObservableValue<Boolean>>> selectedStateCallbackProperty() {
		return selectedStateCallback;
	}

	/**
	 * Sets the {@link Callback} that is bound to by the CheckBox shown on screen.
	 */
	public final void setSelectedStateCallback(Callback<Integer, ObservableValue<Boolean>> value) {
		selectedStateCallbackProperty().set(value);
	}

	/**
	 * Returns the {@link Callback} that is bound to by the CheckBox shown on screen.
	 */
	public final Callback<Integer, ObservableValue<Boolean>> getSelectedStateCallback() {
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

			if (booleanProperty instanceof BooleanProperty) {
				checkBox.selectedProperty().unbindBidirectional((BooleanProperty) booleanProperty);
			}
			ObservableValue obsValue = getSelectedProperty();
			if (obsValue instanceof BooleanProperty) {
				booleanProperty = obsValue;
				checkBox.selectedProperty().bindBidirectional((BooleanProperty) booleanProperty);
			}

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

	private ObservableValue getSelectedProperty() {
		return getSelectedStateCallback() != null ? getSelectedStateCallback().call(getIndex()) : getTableColumn().getCellObservableValue(
						getIndex());
	}

}
