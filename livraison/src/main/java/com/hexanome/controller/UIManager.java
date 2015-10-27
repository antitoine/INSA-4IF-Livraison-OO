package com.hexanome.controller;

import com.hexanome.view.ConstView;
import com.hexanome.view.MainWindow;
import java.io.File;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * This controller manage all the view and is notify when something happens on
 * the view
 *
 * It implements the sigleton design pattern and only one instance should exist
 * when the application is running
 *
 * Should always be use as following UIManager.getInstance(). ...
 */
public class UIManager {

    /**
     * Main Window of the application see MainWindow class for more information
     */
    MainWindow mainWindow;

    private static UIManager uimanager = null;

    private UIManager() {
    }

    /**
     * Return the single of the UIManager
     *
     * @return the UIManager Instance
     */
    public static UIManager getInstance() {
        if (uimanager == null) {
            uimanager = new UIManager();
        }
        return uimanager;
    }

    /**
     * Create the main Window and return it
     *
     * @param stage
     * @return the mainWindow which should be integrated into the scene
     */
    MainWindow createMainWindow(Stage stage) {
        mainWindow = new MainWindow(stage);
        return mainWindow;
    }

    /**
     * Method that allow to notify the UIController
     *
     * @param action Action to be executed
     * @param arg Optional argument
     *
     * Expected @param arg for the following action Load_MAP : File file (valid
     * xml file) Load_PLANNING : File file (valid xml file)
     */
    public void NotifyUI(ConstView.Action action, Object arg) {
        switch (action) {
            case QUIT:
                ContextManager.getInstance().exit(); // Special undoable
                break;
            case ADD_DELIVERY:
                // Create an AddDeliveryCommand and give it to context manager
                break;
            case DELETE_DELIVERY:
                // Create a RemoveDeliveryCommand and give it to context manager
                break;
            case SWAP_DELIVERIES:
                // Create a SwapDeliveryCommand and give it to context manager
                break;
            case LOAD_MAP:
                new Thread() {
                    public void run() {
                        mainWindow.SetWait("Loading Map...");
                        ContextManager.getInstance().loadMap((File) arg); // Not undoable
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                mainWindow.getMapView().ClearMap();
                                ModelManager.getInstance().getMap().addSubscriber(mainWindow.getMapView());
                                mainWindow.SetDone();
                            }
                        });
                    }
                }.start();

                break;
            case LOAD_PLANNING:
                ContextManager.getInstance().loadPlanning((File) arg); // Not undoable
                break;
        }
    }

    /**
     * Method that allow to notify the UIController
     *
     * @param action action to be executed
     */
    public void NotifyUI(ConstView.Action action) {
        NotifyUI(action, null);
    }

}

/////////////// WILL BE DELETED
/**
 * Test for the treeview Add delivery and timeslot
 *
 * @param dv
 */
//    private void testTreeView(DeliveryTreeView dv) {
//        Random r = new Random();
//        for (int i = 0; i < 10; i++) {
//            int start = r.nextInt(24);
//            dv.AddTimeSlot(new TimeSlot(start, r.nextInt(24), null));
//
//            for (int j = 0; j < 10; j++) {
//                dv.AddDelivery(new Delivery(i + j, new Node(r.nextInt(10000), new Point(i, j))), start);
//            }
//        }
//    }
/**
 * Test for the MapView Add Node and arc
 *
 * @param mv
 */
//    private void testMapDisplay(MapView mv) {
//        Random r = new Random();
//
//        Point pttemp = new Point(r.nextInt(1000), r.nextInt(1000));
//
//        for (int i = 0; i < 10; i++) {
//            Point pt1 = pttemp;
//            Point pt2 = new Point(r.nextInt(1000), r.nextInt(1000));
//
//            mv.AddNode(ConstView.EMPTYNODE, pt1);
//            mv.AddNode(ConstView.EMPTYNODE, pt2);
//
//            mv.AddArc(pt1, pt2);
//
//            pttemp = pt2;
//        }
//
//        for (int i = 0; i < 10; i++) {
//            mv.AddNode(ConstView.DELIVERYNODE, new Point(r.nextInt(1000), r.nextInt(1000)));
//        }
//
//        mv.AddNode(ConstView.WAREHOUSENODE, new Point(r.nextInt(1000), r.nextInt(1000)));
//    }
