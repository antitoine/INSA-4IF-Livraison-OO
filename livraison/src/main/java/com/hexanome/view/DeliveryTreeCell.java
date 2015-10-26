package com.hexanome.view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import org.controlsfx.glyphfont.Glyph;

final class DeliveryTreeCell extends TreeCell<String> {

    private TextField textField;
    private ContextMenu addMenu = new ContextMenu();

    public DeliveryTreeCell() {
        MenuItem addMenuItem = new MenuItem("Add Delivery");
        addMenu.getItems().add(addMenuItem);
        addMenuItem.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                TreeItem newDelivery
                        = new TreeItem<>("New Delivery", new Glyph("FontAwesome", "TRUCK"));
                getTreeItem().getChildren().add(newDelivery);
            }
        });
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(getTreeItem().getGraphic());
                if (!getTreeItem().isLeaf() && getTreeItem().getParent() != null) {
                    setContextMenu(addMenu);
                }
            }
        }
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
