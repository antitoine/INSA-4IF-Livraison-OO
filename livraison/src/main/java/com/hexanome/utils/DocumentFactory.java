/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.utils;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is a factory used to build objects providing convenient interfaces
 * to interact with input/output files used by the application
 *
 * @author paul
 */
public class DocumentFactory {
    /**
     * Creates a MapDocument object from the given File object
     *
     * @param file file to interpret as a MapDocument
     * @return null if the given file doesn't exists or is a directory
     */
    public static MapDocument createMapDocument(File file) {
        MapDocument mapdoc = null;
        if (file.exists() && !file.isDirectory()) {
            SAXBuilder builder = new SAXBuilder();
            try {
                Document document = builder.build(file);
                mapdoc = new MapDocument(document);
            } catch (IOException | JDOMException ex) {
                Logger.getLogger(DocumentFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // \todo treat error case
        }
        return mapdoc;
    }

    /**
     * Creates a PlanningDocument object from the given File object
     *
     * @param file file to interpret as a PlanningDocument
     * @return
     */
    public static PlanningDocument createPlanningDocument(File file) {
        if (file.exists() && !file.isDirectory()) {
            SAXBuilder builder = new SAXBuilder();
            try {
                Document document = (Document) builder.build(file);
                PlanningDocument plandoc = new PlanningDocument(document);
                return plandoc;
            } catch (IOException | JDOMException ex) {
                Logger.getLogger(DocumentFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // \todo treat error case
        }

        return null;
    }

    /**
     * Returns a RouteDocument created using the given filename
     *
     * @param filename Filename used to create the document
     * @return
     */
    public static RouteDocument createRouteDocument(String filename) {
        return new RouteDocument(filename);
    }
}
