package com.hexanome.controller;

import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;
import com.hexanome.view.ConstView;
import com.hexanome.view.DeliveryTreeView;
import com.hexanome.view.MainWindow;
import com.hexanome.view.MapView;
import java.awt.Point;
import java.util.Random;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This controller manage all the view
 * and is notify when something happens on the view
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

        // small test for the view components
        testMapDisplay(mainWindow.getMapView());
        testTreeView(mainWindow.getDeliveryTreeView());
    }
    
    /**
     * Method that allow to notify the UIController
     * @param action Action to be executed
     * @param arg Optional argument
     */
    public static void NotifyUI(ConstView.Action action, Object arg) {
        switch (action) {
            case QUIT:
                System.exit(0);
                break;
            case UNDO :
                break;
            case REDO :
                break;
            case ADD_NODE:
                break;
            case DELETE_NODE :
                break;
            case LOAD_MAP :
                break;
            case LOAD_PLANNING :
                break;
        }
    }   

    /**
     * Method that allow to notify the UIController
     * @param action action to be executed
     */
    public static void NotifyUI(ConstView.Action action) {
        NotifyUI(action, null);
    }
    


    /**
     * Test for the treeview
     * Add delivery and timeslot
     * @param dv 
     */
    private void testTreeView(DeliveryTreeView dv) {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int start = r.nextInt(24);
            dv.AddTimeSlot(new TimeSlot(start, r.nextInt(24), null));

            for (int j = 0; j < 10; j++) {
                dv.AddDelivery(new Delivery(new Node(r.nextInt(10000), new Point(i, j))), start);
            }
        }
    }

    /**
     * Test for the MapView
     * Add Node and arc 
     * @param mv 
     */
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
