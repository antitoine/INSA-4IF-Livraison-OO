package com.hexanome.model;

import java.awt.Point;

/**
 *
 * @author paul
 */
public class ModelTest {

    public ModelTest() {
        //testDebugPrint();
    }

    private void testDebugPrint() {

        Map m = new Map();
        m.createNode(5, new Point(10, 15));
        m.createNode(6, new Point(11, 12));
        m.createNode(7, new Point(12, 13));
        m.createArc("rue 1", 10, 10f, 5, 7);
        m.createArc("rue 2", 10, 10f, 5, 6);

        // Affichage
        System.out.println(m);

    }

}
