package com.hexanome.view;

import com.hexanome.controller.UIManager;
import com.hexanome.model.Delivery;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.*;

/**
 * This class is the graphic component used to display an element 
 * in a tree
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 * @see DeliveryTreeView
 */
final class DeliveryTreeCell extends TreeCell<String> {

    private TextField textField;
    private ContextMenu addMenu = new ContextMenu();

    public DeliveryTreeCell() {

        // DRAG SOURCE
        setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                DeliveryTreeCell source = ((DeliveryTreeCell) (event.getSource()));
                Dragboard db = source.startDragAndDrop(TransferMode.ANY);

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
                System.out.println("Drag done " + event.getDragboard().getString() + " <->" + event.getSource().toString());
                DeliveryTreeCell targetCell = (DeliveryTreeCell) event.getSource();
                if (targetCell.getString().startsWith("D")) {
                    Delivery delivery1 = DeliveryTreeView.getDeliveryIdFromName(event.getDragboard().getString());
                    Delivery delivery2 = DeliveryTreeView.getDeliveryIdFromName(targetCell.getString());
                    UIManager.getInstance().swapDelivery(delivery1, delivery2);
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
                    //System.out.println((db.getString()));
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
                System.out.println(event.getSource().toString() + " <-> " + event.getDragboard().toString());

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
                    Dragboard sourceCell = event.getDragboard();
                    DeliveryTreeCell targetCell = (DeliveryTreeCell) event.getSource();
                    if(! targetCell.getString().equals(sourceCell.getString())) {
                        if (targetCell.getString().startsWith("D")) {
                            targetCell.setUnderline(true);
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
                    // targetCell.setStyle("-fx-background: none;");
                    targetCell.setUnderline(false);
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
