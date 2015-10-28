/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.utils;

import com.hexanome.model.Route;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  This class provides a convenient interface to write a Route output file 
 * @author paul
 */
public class RouteDocument extends File {
    private String content;
    /**
     * Creates a new instance of a RouteDocument using pathname
     * @param pathname 
     */
    public RouteDocument(String pathname) {
        super(pathname);
    }
    /**
     * Writes the route to the document content
     * @param route 
     */
    public void writeRoute(Route route) {
        // \todo implement here, file output for a route write all in content
    }
    /**
     * Save the document writting it to the File System.
     * @return true if the file was successfully written, else returns false
     */
    public boolean save() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(this, "UTF-8");
            writer.println(content);
            writer.close();
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RouteDocument.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RouteDocument.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
        return false;
    }
    
}
