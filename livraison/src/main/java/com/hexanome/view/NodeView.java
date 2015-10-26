package com.hexanome.view;

import java.awt.Point;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 */
public abstract class NodeView extends Pane {

    Point position;
    PopOver popover;
    
    @FXML
    Node nodeShape;

    public NodeView(String nodeType, Point p) {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(nodeType));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(NodeView.class.getName()).log(Level.SEVERE, null, ex);
        }

        position = p;
        configurePopOver(nodeType);

        nodeShape.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popover.show(nodeShape);
            }
        });

    }
    
    private void configurePopOver(String nodeType) {
        switch(nodeType){
            case ConstView.EMPTYNODE : 
                popover = new PopOver(new PopOverContentEmptyNode(""+position.x+", "+position.y));
                break;
            case ConstView.DELIVERYNODE :
                popover = new PopOver(new PopOverContentDelivery(""+position.x+", "+position.y));
                break;
            case ConstView.WAREHOUSENODE :
                popover = new PopOver(new PopOverContentWarehouse(""+position.x+", "+position.y));
                break;      
        }
        popover.setAutoHide(true);
        popover.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
        popover.setDetachable(false);
    }

}
