package com.hexanome.utils;

import com.hexanome.controller.UIManager;
import com.hexanome.model.Map;
import java.awt.Point;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * This class provides a convenient interface to extract information from the 
 * XML description of a Map 
 * @author Estelle
 */
public class MapDocument {

    private Document dom;
    /**
     * Creates a new instance of a MapDocument using the given DOM document
     * @param dom 
     */
    public MapDocument(Document dom) {
        this.dom = dom;
    }
    /**
     * Fills the Map object using the content of file and the Map's 
     * factory methods 
     * @param map 
     */
    public void fillMap(Map map) {
        map.clear();
        Element root = dom.getRootElement();

        for (Element node : root.getChildren()) {
            try {
                int id = node.getAttribute("id").getIntValue();
                int x = node.getAttribute("x").getIntValue();
                int y = node.getAttribute("y").getIntValue();
                map.createNode(id, new Point(x, y));
            } catch (DataConversionException ex) {
                Logger.getLogger(MapDocument.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (Element node : root.getChildren()) {
            try {
                // Get the ID of the current node
                int idSrcNode = node.getAttribute("id").getIntValue();
                // Add outgoing arcs to list
                List<Element> arcElements = node.getChildren("LeTronconSortant");
                // Add arcs to the map
                for(Element arc : arcElements) {
                    // Retreive arcs attributes
                    String streetName = arc.getAttributeValue("nomRue");
                    float avgSpeed = Float.parseFloat(arc.getAttribute("vitesse").getValue().replaceAll(",","."));
                    float length = Float.parseFloat(arc.getAttribute("longueur").getValue().replaceAll(",","."));
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
     * Checks the semantic integrity of the XML description.
     * @return
     */
    public boolean checkIntegrity() {
        // \todo implement all XML checks here !
        return true;
    }
}
