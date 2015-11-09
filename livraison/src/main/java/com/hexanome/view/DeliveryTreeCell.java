package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.UIManager;
import com.hexanome.model.Delivery;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.*;
import javafx.scene.paint.Color;

/**
 * This class is the graphic component used to display an element 
 * in a tree
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 * @see DeliveryTreeView
 */
final class DeliveryTreeCell extends TreeCell<String> {

    @SuppressWarnings("unused")
    private TextField textField;
    private ContextMenu addMenu = new ContextMenu();

    public DeliveryTreeCell() {

        setStyle("-fx-border-color: white;");

        // DRAG SOURCE
        setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */

                DeliveryTreeCell source = ((DeliveryTreeCell) (event.getSource()));
                if (DeliveryTreeView.getDeliveryFromName(source.getString()) != null) {
                    ContextManager.getInstance().getCurrentState().leftClickPressedOnDelivery();
                    Dragboard db = source.startDragAndDrop(TransferMode.ANY);

                /* Put a string on a dragboard */
                    ClipboardContent content = new ClipboardContent();
                    content.putString(source.getString());
                    db.setContent(content);
                }
                event.consume();
            }
        });


        // DRAG TARGET    
        setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* on drag Over */
                event.acceptTransferModes(TransferMode.ANY);
                event.consume();
            }
        });

        setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                event.acceptTransferModes(TransferMode.ANY);
                Dragboard sourceCell = event.getDragboard();
                DeliveryTreeCell targetCell = (DeliveryTreeCell) event.getSource();
                if (targetCell != null && !targetCell.getString()
                        .equals(sourceCell.getString())) {
                    Delivery delivery1 = DeliveryTreeView
                            .getDeliveryFromName(event.getDragboard().getString());
                    Delivery delivery2 = DeliveryTreeView
                            .getDeliveryFromName(targetCell.getString());
                    System.out.println("Drag done " + sourceCell.getString() + " <->" +
                            targetCell.getString());
                    UIManager.getInstance().swapDelivery(delivery1, delivery2);
                }

                event.consume();
            }
        });

        setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                /* show to the user that it is an actual gesture target */
                if (event.getDragboard().hasString()) {
                    Dragboard sourceCell = event.getDragboard();
                    DeliveryTreeCell targetCell = (DeliveryTreeCell) event.getSource();
                    if(! targetCell.getString().equals(sourceCell.getString())) {
                        if (DeliveryTreeView.getDeliveryFromName(targetCell.getString()) != null) {
                            targetCell.setTextFill(Color.RED);
                            targetCell.setStyle("-fx-border-color: red;");
                        }
                    }
                }
                event.consume();
            }
        });

        setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                Dragboard sourceCell = event.getDragboard();
                DeliveryTreeCell targetCell = (DeliveryTreeCell) event.getSource();
                if(! targetCell.getString().equals(sourceCell.getString())) {
                    targetCell.setStyle("-fx-border-color: white;");
                    targetCell.setTextFill(Color.BLACK);
                }
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
