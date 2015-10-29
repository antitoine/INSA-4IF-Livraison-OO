/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.utils;

import com.hexanome.model.Delivery;
import com.hexanome.model.Map;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * This class provides a convenient interface to extract information from the 
 * XML description of a Planning 
 * @author paul
 */
public class PlanningDocument {
    private Document dom;
    /**
     * Creates a new instance of a PlanningDocument using the given DOM document
     * @param dom 
     */
    public PlanningDocument(Document dom) {
        this.dom = dom;
    }
    /**
     * Returns the Node matching with the warehouse described in the XML file.
     * @param map
     * @return 
     */
    public Node getWarehouse(Map map) {
        // Retreive warehouse element 
        Element warehouse = dom.getRootElement().getChild("Entrepot");
        Node node = null;
        try {
            // Retreive the Id of the node
            int nodeId = warehouse.getAttribute("adresse").getIntValue();
            // Return the node which is associated with the id
            node = map.getNodeById(nodeId);
        } catch (DataConversionException ex) {
            Logger.getLogger(PlanningDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
        return node;
    }
    /**
     * Returns a collection of timeslots extracted from the XML description
     * @param map
     * @return 
     */
    public ArrayList<TimeSlot> getTimeSlots(Map map) {
        // Get all the XML nodes representing timeslots
        List<Element> timeSlotElements = dom.getRootElement().getChildren("PlagesHoraires").get(0).getChildren();
        // Create the list f timeslots that will be returned 
        ArrayList<TimeSlot> timeslots = new ArrayList<>();
        // Loop on timeslots XML nodes
        for(Element timeSlotElement : timeSlotElements) {
            // Create current timeslot
            TimeSlot ts = null;
            // Get all deliveries scheduled in the current timeslot
            List<Element> deliveryElements = timeSlotElement.getChildren("Livraisons").get(0).getChildren();
            // Create a list of deliveries to attach to timeslot
            ArrayList<Delivery> deliveries = new ArrayList<>();
            // Loop on each delivery
            for(Element deliveryElement : deliveryElements) {    
                // Create a delivery element
                Delivery delivery = null;
                try {
                    int deliveryId = deliveryElement.getAttribute("id").getIntValue();
                    int nodeId = deliveryElement.getAttribute("adresse").getIntValue();
                    delivery = new Delivery(deliveryId, map.getNodeById(nodeId));
                } catch (DataConversionException ex) {
                    Logger.getLogger(PlanningDocument.class.getName()).log(Level.SEVERE, null, ex);
                }
                // Add the delivery to the collection
                deliveries.add(delivery);
            }
            // Get timeslot attributes 
            int startTime = TypeWrapper.timestampToSeconds(timeSlotElement.getAttribute("heureDebut").getValue());
            int endTime = TypeWrapper.timestampToSeconds(timeSlotElement.getAttribute("heureFin").getValue());
            // Create new timeslot
            ts = new TimeSlot(startTime, endTime, deliveries);
            // Add new timeslot to the collection
            timeslots.add(ts);
        }

        // Return the collection of timeslots
        return timeslots;
    }
    /**
     * Checks the semantic integrity of the XML description using the given Map
     * @param map
     *      Map used to check the semantic
     * @return 
     */
    // RemoveMeLater : must have a map to check if nodes used in the planning 
    //                 also exist in the map.
    public boolean checkIntegrity(Map map) {
        // \todo implement all XML checks here !
        return true;
    }
}
