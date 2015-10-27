/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.model;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author paul
 */
public class ModelTest {

    public ModelTest() {
        testDebugPrint();
    }
    
    private void testDebugPrint() {
        Node n1 = new Node(0, new Point(0,0));
        Node n2 = new Node(1, new Point(10, 10));
        
        Arc a1 = new Arc("rue du paradis", 42.2017f, 2017.42f, n1, n2);
        Arc a2 = new Arc("rue du paradis", 2017.42f, 42.2017f, n2, n1);
        
        ArrayList<Arc> arcs = new ArrayList<>();
        arcs.add(a1);
        arcs.add(a2);
        
        n1.AttachOutgoingArc(a1);
        n2.AttachOutgoingArc(a2);
        
        Delivery d1 = new Delivery(0, n1);
        Delivery d2 = new Delivery(1, n2);
        
        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(d1);
        deliveries.add(d2);
        
        Path p = new Path(arcs);
        
        TimeSlot ts = new TimeSlot(10, 20, deliveries);
        
        
        // Affichage
        System.out.println(n1.toString());
        System.out.println(n2.toString());
        System.out.println(a1.toString());
        System.out.println(a2.toString());
        System.out.println(d1.toString());
        System.out.println(d2.toString());
        System.out.println(p.toString());
        System.out.println(ts.toString());
        
    }
    
}
