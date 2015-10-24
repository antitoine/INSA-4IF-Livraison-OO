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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 */
public class MapView extends AnchorPane implements Observer, Initializable{

    static HashMap<Point, NodeView> nodeList;
    static HashMap<Point, ArcView> arcList;

    /**
     * Initializes the controller class.
     */
    public  void MapView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ConstView.MAPVIEW));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmptyNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            case ConstView.EMPTYNODE:
                n = new EmptyNodeView(pt);
                break;
            case ConstView.DELIVERYNODE:
                n = new DeliveryNodeView(pt);
                break;
            case ConstView.WAREHOUSENODE:
                n = new WarehouseNodeView(pt);
                break;
        }

        this.getChildren().add(n);
        n.toFront();
        n.relocate(pt.x - n.getPrefWidth() / 2, pt.y - n.getPrefHeight() / 2);
        nodeList.put(pt, n);
    }

    public void SwapNode(Point pt1, Point pt2) {

    }

    /**
     * Delete a node a the position pt
     *
     * @param pt
     */
    public void DeleteNode(Point pt) {
        this.getChildren().remove((NodeView) nodeList.get(pt));
        nodeList.remove(pt);
    }

    public void AddArc(Point pt1, Point pt2) {
        ArcView av = new ArcView(pt1, pt2);
        this.getChildren().add(av);
        arcList.put(pt1, av);
        av.toBack();
    }

    public void deleteArc(Point pt1, Point pt2) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nodeList = new HashMap<>();
        arcList = new HashMap<>();
        
    }

}
