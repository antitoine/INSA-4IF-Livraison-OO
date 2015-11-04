/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.model;

import java.awt.Point;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author guillaume
 */
public class PathGraphTest {
    
    public PathGraphTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getNbEdges method, of class PathGraph.
     */
    @Test
    public void testGetNbEdges() {
        System.out.println("getNbEdges");
        
        Node first = new Node(1, new Point(10,10));
        Node second = new Node(2, new Point(10,20));
        Node third = new Node(3, new Point(10,30));
        Arc arc1 = new Arc("hollywood",12,31,first,second);
        Arc arc2 = new Arc("doowylloh",12,31,second,third);      
        ArrayList<Arc> arcsList = new ArrayList<>();
        arcsList.add(arc1);
        arcsList.add(arc2);    
        Path path = new Path(arcsList);
        
        PathGraph pathGraph = new PathGraph();
        pathGraph.addPath(path);
        
        int expResult = 2;
        int result = pathGraph.getNbEdges();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCost method, of class PathGraph.
     */
    @Test
    public void testGetCost() {
        System.out.println("getCost");
        int i = 0;
        int j = 2;
        
        Node first = new Node(1, new Point(10,10));
        Node second = new Node(2, new Point(10,20));
        Node third = new Node(3, new Point(10,30));
        Arc arc1 = new Arc("hollywood",5,31,first,second);
        Arc arc2 = new Arc("doowylloh",6,31,second,third);      
        ArrayList<Arc> arcsList = new ArrayList<>();
        arcsList.add(arc1);
        arcsList.add(arc2);    
        Path path = new Path(arcsList);
        
        PathGraph pathGraph = new PathGraph();
        pathGraph.addPath(path);
        
        float expResult = 11.0F;
        float result = pathGraph.getCost(i, j);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of isArc method, of class PathGraph.
     */
    @Test
    public void testIsArc() {
        System.out.println("isArc");
        int i = 1;
        int j = 2;
        
        Node first = new Node(1, new Point(10,10));
        Node second = new Node(2, new Point(10,20));
        Node third = new Node(3, new Point(10,30));
        Arc arc1 = new Arc("hollywood",5,31,first,second);
        Arc arc2 = new Arc("doowylloh",6,31,second,third);      
        ArrayList<Arc> arcsList = new ArrayList<>();
        arcsList.add(arc1);
        arcsList.add(arc2);    
        Path path = new Path(arcsList);
        
        PathGraph pathGraph = new PathGraph();
        pathGraph.addPath(path);
        
        //is arc
        boolean expResult1 = true;
        boolean result1 = pathGraph.isArc(i, j);
        assertEquals(expResult1, result1);
        
        //is not arc
        i = 2;
        j = 1;
        boolean expResult2 = false;
        boolean result2 = pathGraph.isArc(i, j);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of addPath method, of class PathGraph.
     */
    @Test
    public void testAddPath() {
        System.out.println("addPath");
        
        Node first = new Node(1, new Point(10,10));
        Node second = new Node(2, new Point(10,20));
        Node third = new Node(3, new Point(10,30));
        Arc arc1 = new Arc("hollywood",5,31,first,second);
        Arc arc2 = new Arc("doowylloh",6,31,second,third);      
        ArrayList<Arc> arcsList = new ArrayList<>();
        arcsList.add(arc1);
        arcsList.add(arc2);    
        Path path = new Path(arcsList);

        PathGraph instance = new PathGraph();
        instance.addPath(path);                     //Function tested
        Path expResult = instance.indexAsPath(0, 3);
        
        ArrayList<Arc> expResultList = path.getArcs();
        ArrayList<Arc> resultList = expResult.getArcs();
        assertEquals(expResultList.size(), resultList.size());
        
        for(int i=0;i<expResultList.size();i++)
        {
            assertEquals(expResultList.get(i), resultList.get(i));
        }
    }

    /**
     * Test of indexAsPath method, of class PathGraph.
     */
    @Test
    public void testIndexAsPath() {
        System.out.println("indexAsPath");
         Node first = new Node(1, new Point(10,10));
        Node second = new Node(2, new Point(10,20));
        Node third = new Node(3, new Point(10,30));
        Arc arc1 = new Arc("hollywood",5,31,first,second);
        Arc arc2 = new Arc("doowylloh",6,31,second,third);      
        ArrayList<Arc> arcsList = new ArrayList<>();
        arcsList.add(arc1);
        arcsList.add(arc2);    
        Path path = new Path(arcsList);

        PathGraph instance = new PathGraph();
        instance.addPath(path);                     
        Path expResult = instance.indexAsPath(0, 3); //Function tested
        
        ArrayList<Arc> expResultList = path.getArcs();
        ArrayList<Arc> resultList = expResult.getArcs();
        assertEquals(expResultList.size(), resultList.size());
        
        for(int i=0;i<expResultList.size();i++)
        {
            assertEquals(expResultList.get(i), resultList.get(i));
        }
    }
    
}
