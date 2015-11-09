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

/**
 * This class is a factory used to build objects providing convenient interfaces
 * to interact with input/output files used by the application
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class DocumentFactory {
    
    private static String error = null;
    
    /**
     * Creates a MapDocument object from the given File object
     *
     * @param file file to interpret as a MapDocument
     * @return null if the given file doesn't exists or is a directory
     */
    public static MapDocument createMapDocument(File file) {
        // Reset factory error
        DocumentFactory.error = null;
        // Create MapDocument
        MapDocument mapdoc = null;
        if (file.exists() && !file.isDirectory()) {
            SAXBuilder builder = new SAXBuilder();
            try {
                Document document = builder.build(file);
                mapdoc = new MapDocument(document);
            } catch (IOException | JDOMException ex) {
                DocumentFactory.error = "Invalid XML file";
            }
        } else {
            DocumentFactory.error = "File is not a regular file";
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
        // Reset factory error
        DocumentFactory.error = null;
        // Create a PlanningDocument
        PlanningDocument plandoc = null;
        if (file.exists() && !file.isDirectory()) {
            SAXBuilder builder = new SAXBuilder();
            try {
                Document document = builder.build(file);
                plandoc = new PlanningDocument(document);
            } catch (IOException | JDOMException ex) {
                DocumentFactory.error = "Invalid XML file";
            }
        } else {
            DocumentFactory.error = "File is not a regular file";
        }
        return plandoc;
    }

    /**
     * Returns a RouteDocument created using the given filename
     *
     * @param filename Filename used to create the document
     * @return
     */
    public static RouteDocument createRouteDocument(File file) {
        return new RouteDocument(file);
    }
    /**
     * Returns last error that occured in the XML factory described in
     * a string, if no error is set, returns null.
     * @return 
     */
    public static String getLastError() {
        return DocumentFactory.error;
    }
}
