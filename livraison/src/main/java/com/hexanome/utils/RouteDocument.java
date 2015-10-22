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
 *
 * @author paul
 */
public class RouteDocument extends File {
    private String content;
    /**
     * 
     * @param pathname 
     */
    public RouteDocument(String pathname) {
        super(pathname);
    }
    /**
     * 
     * @param route 
     */
    public void writeRoute(Route route) {
        // \todo implement here, file output for a route write all in content
    }
    
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
