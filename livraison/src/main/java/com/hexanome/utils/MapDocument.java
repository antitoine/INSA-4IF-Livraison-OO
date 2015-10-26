/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.utils;
import com.hexanome.model.Map;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import static org.jdom2.filter.Filters.document;

/**
 *
 * @author Estelle
 */
public class MapDocument 
{
    private Document dom;
    
    public MapDocument(Document dom) 
    {
        this.dom = dom;
    }
    
    public void fillMap(Map map)
    {
        map.clear();
        Element root = dom.getRootElement();
        ArrayList<Element> nodeElements = (ArrayList<Element>)root.getChildren("Noeud");
        // Add nodes to the map
        for(Element node : nodeElements) {
            // Retreive node parameters and create it using Map factory method
            try {
                int id = node.getAttribute("id").getIntValue();
                int x = node.getAttribute("x").getIntValue();
                int y = node.getAttribute("y").getIntValue();
                map.createNode(id, new Point(x, y));
            } catch (DataConversionException ex) {
                Logger.getLogger(MapDocument.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for(Element node : nodeElements) {
            try {
                // Get the ID of the current node
                int idSrcNode = node.getAttribute("id").getIntValue();
                // Add outgoing arcs to list
                ArrayList<Element> arcElements = (ArrayList<Element>)node.getChildren("LeTronconSortant");
                // Add arcs to the map
                for(Element arc : arcElements) {
                    // Retreive arcs attributes
                    String streetName = arc.getAttributeValue("nomRue");
                    float avgSpeed = arc.getAttribute("vitesse").getFloatValue();
                    float length = arc.getAttribute("longueur").getFloatValue();
                    int idDestNode = arc.getAttribute("idNoeudDestination").getIntValue();
                    // Add arc to the map using its factory method
                    map.createArc(streetName, length, avgSpeed, idSrcNode, idDestNode);
                }   
            } catch (DataConversionException ex) {
                Logger.getLogger(MapDocument.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * 
     * @return 
     */
    public boolean checkIntegrity() {
        // \todo implement all XML checks here !
        return false;
    }
}
