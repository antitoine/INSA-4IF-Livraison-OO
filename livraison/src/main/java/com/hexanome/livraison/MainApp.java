package com.hexanome.livraison;

import com.hexanome.controller.Controller;
import com.hexanome.model.Arc;
import com.hexanome.model.Delivery;
import com.hexanome.model.Map;
import com.hexanome.model.ModelTest;
import com.hexanome.model.Node;
import com.hexanome.model.Path;
import com.hexanome.model.Planning;
import com.hexanome.model.Route;
import com.hexanome.model.TimeSlot;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp {

    /** 
     * Main Method : start the application
     * launch the main controller and the view
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        // TEST
       ModelTest mt = new ModelTest();
        // Init managers by instanciating singletons
       Controller.getInstance().initManagers();
        // launch the UI Manager to display the windows
        // This is default JavaFX behaviour
       Controller.getInstance().initUI(args);
    }*/
    public static void main(String[] args) {
        Map map = new Map();
        
        Node node1 = map.createNode(1, new Point(20, 30));
        Node node2 = map.createNode(2, new Point(20, 20));
        Node node3 = map.createNode(3, new Point(20, 10));
        Node node4 = map.createNode(4, new Point(10, 10));
        Node node5 = map.createNode(5, new Point(10, 20));
        Node node6 = map.createNode(6, new Point(10, 30));
        Node warehouse = map.createNode(7, new Point(10,40));
        
        Arc streetWHto2 = map.createArc("WHto1", 10, 3, 7, 1);
        Arc street1to2 = map.createArc("1to2", 10, 1, 1, 2);
        Arc street2to3 = map.createArc("2to3", 10, 2, 2, 3);
        Arc street2to4 = map.createArc("2to4", 10, 4, 2, 4);
        Arc street2to5 = map.createArc("2to5", 10, 3, 2, 5);
        Arc street3to5 = map.createArc("3to5", 10, 3, 3, 5);
        Arc street4to6 = map.createArc("4to6", 10, 5, 4, 6);
        
        Delivery delivery1 = new Delivery(1, node1);
        Delivery delivery2 = new Delivery(2, node2);
        Delivery delivery3 = new Delivery(3, node4);
        Delivery delivery4 = new Delivery(4, node6);
        
        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(delivery1);
        deliveries.add(delivery2);
        deliveries.add(delivery3);
        deliveries.add(delivery4);
        
        Path path1 = map.getFastestPath(warehouse, node1); // wh -> node1
        Path path2 = map.getFastestPath(node1, node5); // 1 -> 2 -> 5
        Path path3 = map.getFastestPath(node2, node6); // 2 -> 4 -> 6
        Path path4 = map.getFastestPath(node2, node5); // 2 -> 5
        Path path5 = map.getFastestPath(node2, node3); // 2 -> 3
        
        ArrayList<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
        TimeSlot time = new TimeSlot(8, 9, deliveries);
        timeSlotList.add(time);
        
        Planning plan = new Planning(map, warehouse, timeSlotList);
        
        List<Path> expResult = new ArrayList<Path>();
        expResult.add(path1);
        expResult.add(path2);
        expResult.add(path3);
        expResult.add(path4);
        expResult.add(path5);
        
        try {
            plan.computeRoute();
        } catch (Exception ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        Route route = plan.getFastestRoute();
        List<Path> result = route.getPaths();
        
        if(expResult.size() == result.size())
        {
            System.out.println("\n\n------------------- expResult.size() == result.size()");
        }
        for(int i = 0; i<expResult.size(); i++)
        {
            //assertEquals(expResult.get(i), result.get(i));
        }   
    }
    
    /*public static void main(String[]args)
    {
        Point point = new Point();
        point = null;
        
        Node A = new Node(0, point);
        Node B = new Node(1, point);
        Node C = new Node(2, point);
        Node D = new Node(3, point);
        Node E = new Node(4, point);
        
        Arc AtoB = new Arc("AtoB", 10, 3, A, B);
        Arc AtoC = new Arc("AtoC", 10, 1, A, C);
        Arc BtoC = new Arc("BtoC", 10, 1, B, C);
        Arc CtoD = new Arc("CtoD", 10, 6, C, D);
        Arc BtoD = new Arc("BtoD", 10, 2, B, D);
        Arc DtoE = new Arc("DtoE", 10, 1, D, E);

        A.outgoings = new ArrayList<Arc>(){{add(AtoB); add(AtoC);}};
        B.outgoings = new ArrayList<Arc>(){{add(BtoC); add(BtoD);}};
        C.outgoings = new ArrayList<Arc>(){{add(CtoD);}};
        D.outgoings = new ArrayList<Arc>(){{add(DtoE);}};
        E.outgoings = new ArrayList<Arc>();


        computePathsFromSource(A);
        
        Path path = getFastestPath(A, D);
        System.out.println("Distance from A to D " + path.getPathDuration());
        System.out.println("Path: " + path.getArcs());
    }*/

}
