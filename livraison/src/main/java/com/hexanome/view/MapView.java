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
import java.util.Observable;
import java.util.Observer;
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
public class MapView implements Initializable, Observer {

    @FXML
    AnchorPane MainPane;

    HashMap<Point, NodeView> nodeList;
    HashMap<Point, ArcView> arcList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nodeList = new HashMap<>();

        Random r = new Random();

        for (int i = 0; i < 20; i++) {
            AddNode("EMPTY", new Point(r.nextInt(1000), r.nextInt(1000)));
        }

        for (int i = 0; i < 10; i++) {
            AddNode("DELIVERY", new Point(r.nextInt(1000), r.nextInt(1000)));
        }

        AddNode("WAREHOUSE", new Point(r.nextInt(1000), r.nextInt(1000)));
    }

    @Override
    public void update(Observable o, Object arg) {
        // TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Add a node a the position pt.x & pt.y
     *
     * @param Type Node Type
     * @param pt node position
     */
    public void AddNode(String Type, Point pt) {
        NodeView n = null;

        switch (Type) {
            case "EMPTY":
                n = new EmptyNodeView(pt);
                break;
            case "DELIVERY":
                n = new DeliveryNodeView(pt);
                break;
            case "WAREHOUSE":
                n = new WarehouseNodeView(pt);
                break;
        }

        MainPane.getChildren().add(n);
        n.setLayoutX(pt.x);
        n.setLayoutY(pt.y);
        nodeList.put(pt, n);
    }

    public void SwapNode(Point pt1, Point pt2) {

    }

    /**
     * Delete a node a the position pt
     * @param pt
     */
    public void DeleteNode(Point pt) {
        MainPane.getChildren().remove((NodeView) nodeList.get(pt));
        nodeList.remove(pt);
    }

    public void AddArc(Point pt1, Point pt2) {

    }

    public void deleteArc(Point pt1, Point pt2) {

    }

}
