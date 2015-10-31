/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.utils;

import com.hexanome.model.Map;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *
 * @author Estelle
 */
public class MapDocumentTest {
    
    public MapDocumentTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of fillMap method, of class MapDocument.
     */
    @org.junit.Test
    public void testFillMap() {
        try {
            System.out.println("fillMap");
            Map map = new Map();
            MapDocument instance = null;
            
            SAXBuilder builder = new SAXBuilder();
            File file = new File("src\\test\\java\\com\\hexanome\\utils\\plan10x10.xml");
            Document document = (Document) builder.build(file);
            MapDocument mapdoc = new MapDocument(document);
            mapdoc.fillMap(map);
            assertTrue(map.getNodeById(0).getId() == 0);
        } catch (JDOMException ex) {
            fail("Failure : JDOMException caught");
        } catch (IOException ex) {
            Logger.getLogger(MapDocument.class.getName()).log(Level.SEVERE, null, ex);
            fail("Failure : IOException caught : ");
        }
    }

    /**
     * Test of checkIntegrity method, of class MapDocument.
     */
    @org.junit.Test
    public void testCheckIntegrity() {
        System.out.println("checkIntegrity");
        MapDocument instance = null;
        boolean expResult = false;
        boolean result = instance.checkIntegrity();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
