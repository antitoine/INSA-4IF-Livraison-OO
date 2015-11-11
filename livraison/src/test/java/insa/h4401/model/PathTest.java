/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insa.h4401.model;

import org.junit.Test;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author guillaume
 */
public class PathTest {

    public PathTest() {
    }

    /**
     * Test of getArcs method, of class Path.
     */
    @Test
    public void testGetArcs() {
        System.out.println("getArcs");

        Node node1 = new Node(1, new Point(10, 30));
        Node node2 = new Node(2, new Point(10, 20));
        Arc arc1 = new Arc("hollywood", 12, 31, node1, node2);
        Arc arc2 = new Arc("doowylloh", 12, 31, node2, node1);
        LinkedList<Arc> expResult = new LinkedList<>();
        expResult.add(arc1);
        expResult.add(arc2);
        Path instance = new Path(expResult);
        List<Arc> result = instance.getArcs();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFirstNode method, of class Path.
     */
    @Test
    public void testGetFirstNode() {
        System.out.println("getFirstNode");
        Node first = new Node(1, new Point(10, 10));
        Node second = new Node(2, new Point(10, 20));
        Node third = new Node(3, new Point(10, 30));
        Arc arc1 = new Arc("hollywood", 12, 31, first, second);
        Arc arc2 = new Arc("doowylloh", 12, 31, second, third);
        LinkedList<Arc> expResult = new LinkedList<>();
        expResult.add(arc1);
        expResult.add(arc2);
        Path instance = new Path(expResult);
        Node result = instance.getFirstNode();
        assertEquals(first, result);
    }

    /**
     * Test of getLastNode method, of class Path.
     */
    @Test
    public void testGetLastNode() {
        System.out.println("getLastNode");
        Node first = new Node(1, new Point(10, 10));
        Node second = new Node(2, new Point(10, 20));
        Node third = new Node(3, new Point(10, 30));
        Arc arc1 = new Arc("hollywood", 12, 31, first, second);
        Arc arc2 = new Arc("doowylloh", 12, 31, second, third);
        LinkedList<Arc> expResult = new LinkedList<>();
        expResult.add(arc1);
        expResult.add(arc2);
        Path instance = new Path(expResult);
        Node result = instance.getLastNode();
        assertEquals(third, result);
    }

    /**
     * Test of containsNode method, of class Path.
     */
    @Test
    public void testContainsNode() {
        System.out.println("containsNode");

        //Node is in the path
        Node first = new Node(1, new Point(10, 10));
        Node second = new Node(2, new Point(10, 20));
        Node test = new Node(2, new Point(10, 30));
        Arc arc1 = new Arc("hollywood", 12, 31, first, second);
        Arc arc2 = new Arc("doowylloh", 12, 31, second, test);
        LinkedList<Arc> arcsListWithNodeTest = new LinkedList<>();
        arcsListWithNodeTest.add(arc1);
        arcsListWithNodeTest.add(arc2);

        Path instance1 = new Path(arcsListWithNodeTest);
        boolean result1 = instance1.containsNode(test);
        assertEquals(true, result1);

        //Node is not in the path
        LinkedList<Arc> arcsListWithoutNodeTest = new LinkedList<>();
        arcsListWithoutNodeTest.add(arc1);
        Path instance2 = new Path(arcsListWithoutNodeTest);
        boolean result2 = instance2.containsNode(test);
        assertEquals(false, result2);
    }

    /**
     * Test of getPathDuration method, of class Path.
     */
    @Test
    public void testGetPathDuration() {
        System.out.println("getPathDuration");

        //Node is in the path
        Node first = new Node(1, new Point(10, 10));
        Node second = new Node(2, new Point(10, 20));
        Node test = new Node(2, new Point(10, 30));
        Arc arc1 = new Arc("hollywood", 12, 10, first, second);
        Arc arc2 = new Arc("doowylloh", 12, 10, second, test);
        LinkedList<Arc> arcsList = new LinkedList<>();
        arcsList.add(arc1);
        arcsList.add(arc2);
        Path instance = new Path(arcsList);

        float expResult = 2.4F;
        float result = instance.getPathDuration();
        assertEquals(expResult, result, 0.0);
    }
}
