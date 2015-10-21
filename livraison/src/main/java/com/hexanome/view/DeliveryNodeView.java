package com.hexanome.view;

import java.awt.Point;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.PopOver;


/**
 * FXML Controller class
 */
import javafx.scene.shape.Rectangle;
public class DeliveryNodeView extends NodeView {

    @FXML
    Rectangle nodeShape;

    /**
     * Initializes the controller class.
     */
    public DeliveryNodeView(final Point p) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DeliveryNodeView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmptyNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }

        position = p;
        configurePopOver();

        nodeShape.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popover.show(nodeShape);
            }
        });

    }

    private void configurePopOver() {
        popover = new PopOver(new PopOverContentDelivery(""+position.x+", "+position.y));
        popover.setAutoHide(true);
        popover.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
        popover.setDetachable(false);
    }

}
