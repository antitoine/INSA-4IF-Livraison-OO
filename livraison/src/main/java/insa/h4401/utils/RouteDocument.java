/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insa.h4401.utils;

import insa.h4401.model.Arc;
import insa.h4401.model.Node;
import insa.h4401.model.Path;
import insa.h4401.model.Route;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides a convenient interface to write a Route output file
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class RouteDocument {
    private File file;
    private String content;

    /**
     * Creates a new instance of a RouteDocument using file
     *
     * @param file file for the RouteDocument
     */
    public RouteDocument(File file) {
        this.file = file;
    }

    /**
     * Format content of RouteDocument
     *
     * @param route The route
     * @return a list of formated Text
     */
    public static LinkedList<Text> generateFormatedRouteDocumentContent(Route route) {
        LinkedList<Text> texts = new LinkedList<>();
        Text title = new Text("Road Map \n\n");
        title.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        texts.add(title);

        Text totalText = new Text("Summary :\n");
        totalText.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
        texts.add(totalText);
        texts.add(
                new Text(
                        String.format(
                                "Total distance travelled : %.0f meters\n" +
                                        "Departure time : %s\n" +
                                        "Arrival time : %s\n" +
                                        "Total duration : %s\n\n\n",
                                route.getTotalDistance(),
                                TypeWrapper.secondsToFormatedTime((int) route.getStartTime()),
                                TypeWrapper.secondsToFormatedTime((int) route.getEndTime()),
                                TypeWrapper.secondsToFormatedTime((int) route.getTotalDuration())
                        )
                )
        );

        String time;

        Text warehouseText = new Text("From the warehouse\n");
        warehouseText.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
        texts.add(warehouseText);
        for (Path path : route.getPaths()) {
            Node start = path.getFirstNode();
            Node end = path.getLastNode();

            if (start.getDelivery() != null) {
                time = TypeWrapper.secondsToFormatedTime((int) start.getDelivery().getDeliveryEndTime());
                Text timeText = new Text("At " + time + "\n");
                timeText.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
                texts.add(timeText);
            }

            Arc previousArc = path.getArcs().get(0);
            texts.add(new Text("- Take the road : "));
            texts.add(new Text(previousArc.getStreetName()));
            float length = previousArc.getLength();

            for (Arc arc : path.getArcs().subList(1, path.getArcs().size())) {
                if (!arc.getStreetName().equals(previousArc.getStreetName())) {
                    texts.add(new Text(String.format(" for %.0f m\n", length)));
                    texts.add(new Text("- Then turn on the road : "));
                    texts.add(new Text(arc.getStreetName()));
                    previousArc = arc;
                    length = arc.getLength();
                } else {
                    length += arc.getLength();
                }
            }
            texts.add(new Text(String.format(" for %.0f m\n", length)));
            if (end.getDelivery() != null) {
                time = TypeWrapper.secondsToFormatedTime((int) end.getDelivery().getDeliveryTime());
                texts.add(new Text("- Stop there at " + time + " for a delivery\n\n"));
            } else {
                texts.add(new Text("- And you're back to the warehouse at " + TypeWrapper.secondsToFormatedTime((int) route.getEndTime()) + " !\n"));
            }
        }

        return texts;
    }

    private static String nodeToString(Node node) {
        return node.getId() + " (" + node.getLocation().x + ", " + node.getLocation().y + ")";
    }

    /**
     * Writes the route to the document content
     *
     * @param text The text to write
     */
    public void writeRoute(String text) {
        content = text;
    }

    /**
     * Save the document writting it to the File System.
     *
     * @return true if the file was successfully written, else returns false
     */
    public boolean save() {
        PrintWriter writer = null;
        boolean saved = false;
        try {
            writer = new PrintWriter(file, "UTF-8");
            writer.println(content);
            writer.close();
            saved = true;
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(RouteDocument.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return saved;
    }

}
