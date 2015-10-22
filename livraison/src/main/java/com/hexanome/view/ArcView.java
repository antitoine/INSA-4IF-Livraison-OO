package com.hexanome.view;

import java.awt.Point;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Line;

/**
 * FXML Controller class
 *
 * @author hverlin
 */
public class ArcView extends Line {

    @FXML
    Line line;

    /**
     * Initializes the controller class.
     * @param pt1
     * @param pt2
     */
    public ArcView(Point pt1, Point pt2) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ArcView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmptyNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        line.setStartX(pt1.x);
        line.setStartY(pt1.y);
        line.setEndX(pt2.x);
        line.setEndY(pt2.y);
        line.toBack();
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
        
    }


    
}
