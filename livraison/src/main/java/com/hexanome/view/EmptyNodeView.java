package com.hexanome.view;

import java.awt.Point;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 */
public class EmptyNodeView extends NodeView {

    @FXML
    Circle nodeShape;

    /**
     * Initializes the controller class.
     */
    public EmptyNodeView(final Point p) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EmptyNodeView.fxml"));
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
        popover = new PopOver(new PopOverContentEmptyNode(""+position.x+", "+position.y));
        popover.setAutoHide(true);
        popover.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
        popover.setDetachable(false);
    }

}
