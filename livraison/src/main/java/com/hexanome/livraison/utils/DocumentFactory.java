/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.livraison.utils;

import com.hexanome.livraison.utils.MapDocument;
import com.hexanome.livraison.utils.PlanningDocument;
import com.hexanome.livraison.utils.RouteDocument;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author paul
 */
public class DocumentFactory {
    public static MapDocument createMapDocument(String filename)
    {
        File mapf = new File(filename);
        
        if(mapf.exists() && !mapf.isDirectory())
        {
            SAXBuilder builder = new SAXBuilder();
            try {
                Document document = (Document) builder.build(mapf);
                MapDocument mapdoc = new MapDocument(document);
                return mapdoc;
            } catch (IOException ex) {
                Logger.getLogger(DocumentFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JDOMException ex) {
                Logger.getLogger(DocumentFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            // TODO : treat error case
        }
        
        return null;
    }
    
    public static PlanningDocument createPlanningDocument(String filename)
    {
        File planf = new File(filename);
        
        if(planf.exists() && !planf.isDirectory())
        {
            SAXBuilder builder = new SAXBuilder();
            try {
                Document document = (Document) builder.build(planf);
                PlanningDocument plandoc = new PlanningDocument(document);
                return plandoc;
            } catch (IOException ex) {
                Logger.getLogger(DocumentFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JDOMException ex) {
                Logger.getLogger(DocumentFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            // TODO : treat error case
        }
        
        return null;
    }
    
    public static RouteDocument createRouteDocument(String filename)
    {
        return new RouteDocument(filename);
    }
}
