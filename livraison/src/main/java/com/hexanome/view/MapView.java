/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.view;

import java.awt.Point;
import java.net.URL;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

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
    public  void initialize(URL url, ResourceBundle rb) {
        

        nodeList = new HashMap<>();
        arcList = new HashMap<>();
        Random r = new Random();

        Point pttemp = new Point(r.nextInt(1000), r.nextInt(1000));

        for (int i = 0; i < 10; i++) {
            Point pt1 = pttemp;
            Point pt2 = new Point(r.nextInt(1000), r.nextInt(1000));

            AddNode(ConstView.EMPTYNODE, pt1);
            AddNode(ConstView.EMPTYNODE, pt2);

            AddArc(pt1, pt2);

            pttemp = pt2;
        }

        for (int i = 0; i < 10; i++) {
            AddNode(ConstView.DELIVERYNODE, new Point(r.nextInt(1000), r.nextInt(1000)));
        }

        AddNode(ConstView.WAREHOUSENODE, new Point(r.nextInt(1000), r.nextInt(1000)));
        
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

        MainPane.getChildren().add(n);
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
        MainPane.getChildren().remove((NodeView) nodeList.get(pt));
        nodeList.remove(pt);
    }

    public void AddArc(Point pt1, Point pt2) {

        ArcView av = new ArcView(pt1, pt2);
        MainPane.getChildren().add(av);
        arcList.put(pt1, av);
        av.toBack();

    }

    public void deleteArc(Point pt1, Point pt2) {

    }

}
