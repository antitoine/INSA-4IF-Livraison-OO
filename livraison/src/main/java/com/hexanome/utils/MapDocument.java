package com.hexanome.utils;

import com.hexanome.model.Map;
import java.awt.Point;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 *
 * @author Estelle
 */
public class MapDocument {

    private Document dom;

    public MapDocument(Document dom) {
        this.dom = dom;
    }

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
     *
     * @return
     */
    public boolean checkIntegrity() {
        // \todo implement all XML checks here !
        return true;
    }
}
