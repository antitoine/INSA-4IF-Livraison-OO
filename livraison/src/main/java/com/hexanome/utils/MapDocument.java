package com.hexanome.utils;

import com.hexanome.model.Map;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides a convenient interface to extract information from the 
 * XML description of a Map 
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class MapDocument extends XMLParser {

    /**
     * Creates a new instance of a MapDocument using the given DOM document
     * @param dom 
     */
    public MapDocument(Document dom) {
        super(dom);
    }
    
    
    /**
     * Fills the Map object using the content of file and the Map's 
     * factory methods 
     * @param map 
     */
    public void fillMap(Map map) {
        map.clear();
        Element root = getDom().getRootElement();

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
        Element root = getDom().getRootElement();
        // TEST : root contains enough children
        if(root.getChildren().size() < 2)
        {   
            setErrorMsg("Root has not enough children to build a map !");
            return false; // Interrupt check here
        }
        ArrayList<Integer> idList = new ArrayList<>();
        ArrayList<Point> ps = new ArrayList<>();
        ArrayList<Element> arcs = new ArrayList<>();
        for (Element node : root.getChildren()) {
            // TEST : each node has at least one outgoing arc
            if(node.getChildren("LeTronconSortant").size() < 1) {   
                setErrorMsg("At least one node is isolated in the map (no outgoing arc specified) !");
                return false; // Interrupt check here
            } else {
                // Add arcs to list
                arcs.addAll(node.getChildren("LeTronconSortant"));
            }
            // Initialize attributes
            int id;
            int x = -1;
            int y = -1;
            if(node.getAttributeValue("id") == null) {   
                setErrorMsg("Missing attribute <id> in at least one node !");
                return false; // Interrupt check here
            } else {
                try {
                    id = node.getAttribute("id").getIntValue();
                    idList.add(id);
                } catch (DataConversionException ex) {
                    Logger.getLogger(MapDocument.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(node.getAttributeValue("x") == null) {   
                setErrorMsg("Missing attribute <x> in at least one node !");
                return false; // Interrupt check here
            } else {
                try {
                    x = node.getAttribute("x").getIntValue();
                } catch (DataConversionException ex) {
                    Logger.getLogger(MapDocument.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(node.getAttributeValue("y") == null) {   
                setErrorMsg("Missing attribute <y> in at least one node !");
                return false; // Interrupt check here
            } else {
                try {
                    y = node.getAttribute("y").getIntValue();
                } catch (DataConversionException ex) {
                    Logger.getLogger(MapDocument.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ps.add(new Point(x,y));
        }
        // TEST : check if two nodes have the same id
        for (Integer i : idList) {
            if (Collections.frequency(idList, i) > 1) {
                setErrorMsg("At least two nodes share the same id !");
                return false; // Interrupt check here
            }
        }
        // TEST : check if two or more nodes have the same coordinates
        for (int i = 0; i < ps.size(); i++) {
            for (int j = i+1; j < ps.size(); j++) {
                if(ps.get(i).x == ps.get(j).x && ps.get(i).y == ps.get(j).y) {
                    setErrorMsg("At least two nodes share the same coordinates !");
                    return false; // Interrupt check here
                }
            }
        }
        // TEST : arcs destination node is valid
        for (Element arc : arcs) {
            if(arc.getAttributeValue("nomRue") == null) {   
                setErrorMsg("Missing attribute <nomRue> in at least one arc !");
                return false; // Interrupt check here
            }
            if(arc.getAttributeValue("vitesse") == null) {   
                setErrorMsg("Missing attribute <vitesse> in at least one arc !");
                return false; // Interrupt check here
            } else {
                float avgSpeed = Float.parseFloat(arc.getAttribute("vitesse").getValue().replaceAll(",","."));
                // TEST : check if arc's avgSpeed is strictly above 0
                if(avgSpeed <= 0f) {
                    setErrorMsg("At least one arc has its average speed <= 0 !");
                    return false; // Interrupt check here
                }
            }
            if(arc.getAttributeValue("longueur") == null) {   
                setErrorMsg("Missing attribute <longueur> in at least one arc !");
                return false; // Interrupt check here
            } else {
                float length = Float.parseFloat(arc.getAttribute("longueur").getValue().replaceAll(",","."));
                // TEST : check if arc's length is strictly above 0
                if(length <= 0f) {
                    setErrorMsg("At least one arc has its length <= 0 !");
                    return false; // Interrupt check here
                }   
            }
            if(arc.getAttributeValue("idNoeudDestination") == null) {   
                setErrorMsg("Missing attribute <idNoeudDestination> in at least one arc !");
                return false; // Interrupt check here
            } else {
                try {
                    // TEST : check if arc's destination node is present in the map
                    int id = arc.getAttribute("idNoeudDestination").getIntValue();
                    boolean found = false;
                    for (Integer i : idList) {
                        if (i == id) {
                            found = true;
                        }
                    }
                    if(!found) {
                        setErrorMsg("At least one arc has its destination node missing in the map !");
                        return false; // Interrupt check here
                    }
                } catch (DataConversionException ex) {
                    Logger.getLogger(MapDocument.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        // Returns true if all tests passed !
        return true;
    }
}
