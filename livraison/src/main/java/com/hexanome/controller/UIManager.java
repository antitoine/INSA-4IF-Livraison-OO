/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.controller;

import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;
import com.hexanome.view.ConstView;
import com.hexanome.view.DeliveryTreeView;
import com.hexanome.view.MainWindow;
import com.hexanome.view.MapView;
import java.awt.Point;
import java.io.File;
import java.util.Random;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author hverlin
 */
public class UIManager extends Application {

    MainWindow mainWindow;

    public UIManager() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainWindow = new MainWindow(stage);
        Scene scene = new Scene(mainWindow);
        scene.getStylesheets().add("/styles/Styles.css");

        // créer la fenêtre principale
        stage.setTitle("Editeur de livraisons");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        testMapDisplay(mainWindow.getMapView());

        testTreeView(mainWindow.getDeliveryTreeView());
    }

    public static void NotifyUI(ConstView.Action action) {
        switch (action) {
            case QUIT:
                System.exit(0);
                break;
        }
    }
    


    private void testTreeView(DeliveryTreeView dv) {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int start = r.nextInt(24);
            dv.AddTimeSlot(new TimeSlot(start, r.nextInt(24), null));

            for (int j = 0; j < 10; j++) {
                dv.AddDelivery(new Delivery(new Node(r.nextInt(10000), j, j)), start);
            }
        }
    }

    private void testMapDisplay(MapView mv) {
        Random r = new Random();

        Point pttemp = new Point(r.nextInt(1000), r.nextInt(1000));

        for (int i = 0; i < 10; i++) {
            Point pt1 = pttemp;
            Point pt2 = new Point(r.nextInt(1000), r.nextInt(1000));

            mv.AddNode(ConstView.EMPTYNODE, pt1);
            mv.AddNode(ConstView.EMPTYNODE, pt2);

            mv.AddArc(pt1, pt2);

            pttemp = pt2;
        }

        for (int i = 0; i < 10; i++) {
            mv.AddNode(ConstView.DELIVERYNODE, new Point(r.nextInt(1000), r.nextInt(1000)));
        }

        mv.AddNode(ConstView.WAREHOUSENODE, new Point(r.nextInt(1000), r.nextInt(1000)));
    }
}
