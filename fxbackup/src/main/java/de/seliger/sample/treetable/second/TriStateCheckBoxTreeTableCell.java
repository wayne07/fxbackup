package de.seliger.sample.treetable.second;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
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

    /**
     * ************************************************************************
     * * Static cell factories *
     *
     * @return *
     * ************************************************************************
     */

    public static Callback<TreeTableColumn<Inventory, String>, TreeTableCell<Inventory, String>> create() {
        return new Callback<TreeTableColumn<Inventory, String>, TreeTableCell<Inventory, String>>() {
            @Override
            public TreeTableCell<Inventory, String> call(TreeTableColumn<Inventory, String> list) {
                return new TriStateCheckBoxTreeTableCell(new InventoryCallback(), defaultStringConverter);
            }
        };
    }

    /**
     * ************************************************************************
     * * Fields * *
     * ************************************************************************
     */
    private final CheckBox checkBox;

    private boolean showLabel;

    private ObservableObjectValue<Inventory> inventoryObjectValue;

    /**
     * ************************************************************************
     * * Constructors * *
     * ************************************************************************
     */

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
        this.inventoryObjectValue = inventoryCallback.call(null);

        setGraphic(null);
        setSelectedStateCallback(inventoryCallback);
        setConverter(converter);

        addEventHandler(ActionEvent.ACTION, new ActionEventHandler());
    }

    /**
     * ************************************************************************
     * * Properties * *
     * ************************************************************************
     */

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
    private final ObjectProperty<Callback<Integer, ObservableObjectValue<Inventory>>> selectedStateCallback = new SimpleObjectProperty<>(
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * ************************************************************************
     * * Private implementation * *
     * ************************************************************************
     */

    private void updateShowLabel() {
        this.showLabel = converter != null;
        this.checkBox.setAlignment(showLabel ? Pos.CENTER_LEFT : Pos.CENTER);
    }

    private ObservableValue<?> getSelectedProperty() {
        return getSelectedStateCallback() != null ? getSelectedStateCallback().call(getIndex()) : getTableColumn().getCellObservableValue(getIndex());
    }

    private static class ActionEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent eventHandler) {
            TriStateCheckBoxTreeTableCell treeTableCell = (TriStateCheckBoxTreeTableCell) eventHandler.getSource();
            System.out.println(eventHandler.getEventType() + "-Event from: " + treeTableCell.getText());
            boolean isSelected = treeTableCell.checkBox.isSelected();
            System.out.println("cp-selected: " + isSelected);

            TreeTableView<Inventory> treeTableView = treeTableCell.getTreeTableView();
            TreeTableRow<Inventory> treeTableRow = treeTableCell.getTreeTableRow();
            TreeItem<Inventory> treeItem = treeTableRow.getTreeItem();
            treeItem.getValue().setSelected(isSelected);

            int treeItemLevel = treeTableView.getTreeItemLevel(treeItem);
            System.out.println("treeItemLevel: " + treeItemLevel);

            if (isRoot(treeTableView, treeItem)) {
                // do something
                updateChildren(treeTableView, treeItem, isSelected);
            } else {
                boolean isLeaf = treeItem.isLeaf();
                System.out.println("isLeaf: " + isLeaf);
                System.out.println("isExpanded: " + treeItem.isExpanded());
//                TreeItem<Inventory> parent = treeItem.getParent();
//                System.out.println("item.parent.value: " + parent.getValue().nameProperty());

//                int parentRow = treeTableView.getRow(parent);
//                System.out.println("parentRow: " + parentRow);
//
//                int row = treeTableView.getRow(treeItem);
//                System.out.println("row: " + row);
//
//                int rowIndex = treeTableRow.getIndex();
//                System.out.println("rowIndex: " + rowIndex);

                if (isLeaf) {
                    updateParentsToRoot(treeTableView, treeItem, isSelected);
                } else {
                    updateParentsToRoot(treeTableView, treeItem, isSelected); // partiell selektiert
                    updateChildren(treeTableView, treeItem, isSelected);
                }
            }
        }

        private void updateChildren(TreeTableView<Inventory> treeTableView, TreeItem<Inventory> treeItem, boolean isSelected) {
            ObservableList<TreeItem<Inventory>> children = treeItem.getChildren();
            for (TreeItem<Inventory> child : children) {
                child.getValue().setSelected(isSelected);
            }
        }

        private void updateParentsToRoot(TreeTableView<Inventory> treeTableView, TreeItem<Inventory> treeItem, boolean isSelected) {
            TreeItem<Inventory> parent = treeItem.getParent();
            parent.getValue().setSelected(isSelected);
            TreeTableColumn<Inventory, ?> treeColumn = treeTableView.getColumns().get(0);

            Callback<? extends TreeTableColumn<Inventory, ?>, ? extends TreeTableCell<Inventory, ?>> cellFactory = treeColumn.getCellFactory();
            TreeTableCell<Inventory, ?> treeTableCell = cellFactory.call(null);
            if( treeTableCell instanceof TriStateCheckBoxTreeTableCell ) {
                TriStateCheckBoxTreeTableCell cell = (TriStateCheckBoxTreeTableCell) treeTableCell;
                cell.checkBox.setIndeterminate(true);
            }

            if (isRoot(treeTableView, parent)) {
                // Ende
                System.out.println("Ende, root erreicht");
            } else {
                System.out.println("item: "+parent.getValue().nameProperty());
                updateParentsToRoot(treeTableView, parent, isSelected);
            }
        }

        private boolean isRoot(TreeTableView<Inventory> treeTableView, TreeItem<Inventory> treeItem) {
            return treeTableView.getTreeItemLevel(treeItem) == 0;
        }
    }
}
