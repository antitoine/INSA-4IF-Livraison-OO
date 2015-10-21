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
public class EmptyNodeView extends NodeView{

    
    @FXML
    Circle nodeShape;   
    
    Point position;
       
     /**
     * Initializes the controller class.
     */
    public EmptyNodeView(final Point p) {
        position = p;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EmptyNodeView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
 
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmptyNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }
       

        nodeShape.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Button btn = new Button("", new Glyph("FontAwesome", "TRASH"));
                btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("delete "+p.toString());
                       // MapView.DeleteNode(p);
                    }
                });
                PopOver po = new PopOver(btn);
                getChildren().add(po.getContentNode());
                po.setAutoHide(true);
                po.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
                po.show(nodeShape);
                po.setDetachable(false);
                po.setMinWidth(200);
                po.setMinHeight(100);
                
            }
        });
        
        
    }
    

}
