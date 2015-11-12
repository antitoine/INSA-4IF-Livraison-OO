/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insa.h4401.utils;

import insa.h4401.model.Delivery;
import insa.h4401.model.Map;
import insa.h4401.model.Node;
import insa.h4401.model.TimeSlot;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

/**
 * This class provides a convenient interface to extract information from the
 * XML description of a Planning
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class PlanningDocument extends XMLParser {
    /**
     * Creates a new instance of a PlanningDocument using the given DOM document
     *
     * @param dom DOM document for the planning document
     */
    public PlanningDocument(Document dom) {
        super(dom);
    }

    /**
     * Returns the Node matching with the warehouse described in the XML file.
     *
     * @param map map where there is the warehouse
     * @return the warehouse
     */
    public Node getWarehouse(Map map) {
        // Retreive warehouse element 
        Element warehouse = getDom().getRootElement().getChild("Entrepot");
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
     *
     * @param map The map document
     * @return the list of timeSlots
     */
    public ArrayList<TimeSlot> getTimeSlots(Map map) {
        // Get all the XML nodes representing timeslots
        List<Element> timeSlotElements = getDom().getRootElement().getChildren("PlagesHoraires").get(0).getChildren();
        // Create the list f timeslots that will be returned 
        ArrayList<TimeSlot> timeslots = new ArrayList<>();
        // Loop on timeslots XML nodes
        for (Element timeSlotElement : timeSlotElements) {
            // Create current timeslot
            TimeSlot ts;
            // Get all deliveries scheduled in the current timeslot
            List<Element> deliveryElements = timeSlotElement.getChildren("Livraisons").get(0).getChildren();
            // Create a list of deliveries to attach to timeslot
            ArrayList<Delivery> deliveries = new ArrayList<>();
            // Loop on each delivery
            for (Element deliveryElement : deliveryElements) {
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
            int startTime = TypeWrapper.xmlTimeStringToSeconds(timeSlotElement.getAttributeValue("heureDebut"));
            int endTime = TypeWrapper.xmlTimeStringToSeconds(timeSlotElement.getAttributeValue("heureFin"));
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
     *
     * @param map Map used to check the semantic
     * @return true if the document is correct
     */
    public boolean checkIntegrity(Map map) {
        Element root = getDom().getRootElement();
        // TEST : check if there is only one warehouse
        if (root.getChildren("Entrepot").size() != 1) {
            setErrorMsg("The planning specifies zero or more than one warehouse !");
            return false; // Interrupt check here
        } else {
            // TEST : check warehouse id
            if (root.getChild("Entrepot").getAttributeValue("adresse") != null) {
                try {
                    // TEST : check if id of the node referenced by the warehouse exists in the map 
                    int id = root.getChild("Entrepot").getAttribute("adresse").getIntValue();
                    if (map.getNodeById(id) == null) {
                        setErrorMsg("The node referenced by the warehouse is missing in the map !");
                        return false; // Interrupt check here
                    }
                } catch (DataConversionException ex) {
                    Logger.getLogger(PlanningDocument.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                setErrorMsg("Missing <adresse> attribute in warehouse node !");
                return false; // Interrupt check here
            }
            // TEST : check if planning file specifies one or more timeslots
            if (root.getChildren("PlagesHoraires").get(0).getChildren().size() < 1) {
                setErrorMsg("At least one timeslot should be specified by planning file !");
                return false; // Interrupt check here
            }

            // TEST : check if each timeSlot contains at least one delivery
            for (Element ts : root.getChildren("PlagesHoraires").get(0).getChildren()) {

                // TEST : check if timeSlot attributes are not missing and correct
                int startTime;
                int endTime;
                if (ts.getAttributeValue("heureDebut") != null) {
                    startTime = TypeWrapper.xmlTimeStringToSeconds(ts.getAttributeValue("heureDebut"));
                } else {
                    setErrorMsg("Missing <heureDebut> attribute in at least one timeSlot !");
                    return false; // Interrupt check here
                }
                if (ts.getAttributeValue("heureFin") != null) {
                    endTime = TypeWrapper.xmlTimeStringToSeconds(ts.getAttributeValue("heureFin"));
                } else {
                    setErrorMsg("Missing <heureFin> attribute in at least one timeSlot !");
                    return false; // Interrupt check here
                }

                // TEST : check if endTime > startTime
                if (startTime >= endTime) {
                    setErrorMsg("At least one timeSlot has an end time before its start time !");
                    return false; // Interrupt check here
                }

                List<Element> deliveries = ts.getChildren("Livraisons").get(0).getChildren();
                if (deliveries.size() < 1) {
                    setErrorMsg("All timeslots must specify at least one delivery !");
                    return false; // Interrupt check here
                }
                ArrayList<Integer> ids = new ArrayList<>();
                ArrayList<Integer> addresses = new ArrayList<>();

                for (Element delivery : deliveries) {
                    // TEST : if delivery has an address
                    if (delivery.getAttributeValue("adresse") == null) {
                        setErrorMsg("At least one delivery doesn't reference it's node id !");
                        return false; // Interrupt check here
                    } else {
                        try {

                            int address = delivery.getAttribute("adresse").getIntValue();
                            addresses.add(address);
                            int id = delivery.getAttribute("id").getIntValue();
                            ids.add(id);
                            // TEST : if delivery reference an existing node in the map
                            if (map.getNodeById(id) == null) {
                                setErrorMsg("At least one delivery has its node missing in the map !");
                                return false; // Interrupt check here
                            }
                        } catch (DataConversionException ex) {
                            Logger.getLogger(PlanningDocument.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                // TEST : check if two nodes share the same id in the timeslot
                for (Integer id : ids) {
                    if (Collections.frequency(ids, id) > 1) {
                        setErrorMsg("At least two deliveries share the same id !");
                        return false; // Interrupt check here
                    }
                }
                // TEST : check if two nodes share the same address in the timeslot
                for (Integer address : addresses) {
                    if (Collections.frequency(addresses, address) > 1) {
                        setErrorMsg("At least two deliveries share the same <adresse> !");
                        return false; // Interrupt check here
                    }
                }
            }
        }

        return true;
    }
}
