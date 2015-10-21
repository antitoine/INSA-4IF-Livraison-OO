/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.view;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 */
public class MapView implements Initializable {

    @FXML
    AnchorPane MainPane;
    
    HashMap<Point, NodeView> nodeList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nodeList = new HashMap<>();
        
        Random r = new Random();
        
        for(int i = 0; i < 20; i++){
            AddNode(new Point(r.nextInt(1000), r.nextInt(1000)));
        }
    }
    
    /**
     * Add an empty node a the position pt.x & pt.y
     * @param pt node position
     */
    public void AddNode(Point pt) { 
        EmptyNodeView n = new EmptyNodeView(pt);
        MainPane.getChildren().add(n);   
        n.setLayoutX(pt.x);
        n.setLayoutY(pt.y);        
        nodeList.put(pt, n);
    }
    
    /**
     * Delete a node a the position pt
     * @param pt 
     */
    public void DeleteNode(Point pt) {
        MainPane.getChildren().remove((EmptyNodeView) nodeList.get(pt));
        nodeList.remove(pt);
    }

}
