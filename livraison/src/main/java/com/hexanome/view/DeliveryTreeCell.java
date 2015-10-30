package com.hexanome.view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    // TODO
                }
            }
        });

        // DRAG SOURCE
        setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                DeliveryTreeCell source = ((DeliveryTreeCell) (event.getSource()));
                Dragboard db = source.startDragAndDrop(TransferMode.MOVE);

                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(source.getString());
                db.setContent(content);

                event.consume();
            }
        });

        setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag and drop gesture ended */
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    System.out.println("Drag done");
                }
                event.consume();
            }
        });

        // DRAG TARGET    
        setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    System.out.println((db.getString()));
                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                //event.setDropCompleted(success);

                event.consume();
            }
        });

        setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                /* accept it only if it is not dragged from the same node 
                 * and if it has a string data */
                if (event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                /* show to the user that it is an actual gesture target */
                if (event.getDragboard().hasString()) {
                    System.out.println("ON drag entered");
                }
                event.consume();
            }
        });

        setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                System.out.println("ON drag exited");

                event.consume();
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
