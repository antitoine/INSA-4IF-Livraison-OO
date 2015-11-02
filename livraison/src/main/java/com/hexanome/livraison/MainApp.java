package com.hexanome.livraison;

import com.hexanome.controller.Controller;
import com.hexanome.model.Arc;
import com.hexanome.model.ModelTest;
import com.hexanome.model.Node;
import java.awt.Point;

public class MainApp {

    /** 
     * Main Method : start the application
     * launch the main controller and the view
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TEST
       ModelTest mt = new ModelTest();
        // Init managers by instanciating singletons
       Controller.getInstance().initManagers();
        // launch the UI Manager to display the windows
        // This is default JavaFX behaviour
       Controller.getInstance().initUI(args);
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
