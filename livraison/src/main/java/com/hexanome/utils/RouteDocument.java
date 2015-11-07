/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.utils;

import com.hexanome.model.Arc;
import com.hexanome.model.Node;
import com.hexanome.model.Path;
import com.hexanome.model.Route;
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
 *  This class provides a convenient interface to write a Route output file 
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class RouteDocument {
    private File file;
    private String content;
    /**
     * Creates a new instance of a RouteDocument using pathname
     * @param pathname 
     */
    public RouteDocument(File file) {
        this.file = file;
    }
    /**
     * Writes the route to the document content
     * @param route 
     */
    public void writeRoute(Route route) {
        content = generateRouteDocumentContent(route);
    }
    /**
     * Save the document writting it to the File System.
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
    /**
     * Getter of content property
     * @return 
     */
    public String getContent() {
        return content;
    }
    /**
     * Format content of RouteDocument
     * @param route
     * @return 
     */
    public static String generateRouteDocumentContent(Route route) {   
        String docContent = " --- ROAD MAP ---\n\n";
        for(Path path : route.getPaths()){
            docContent += "From : "+path.getFirstNode().getLocation() + "\n";
            for(Arc arc : path.getArcs()){
                docContent += "take the road : "+arc.getStreetName()+"\n";
            }
            docContent += "Then, go to "+path.getLastNode().getLocation()+"\n";
        }
        return docContent;
    }

    /**
     * Format content of RouteDocument
     * @param route
     * @return
     */
    public static LinkedList<Text> generateFormatedRouteDocumentContent(Route route) {
        LinkedList<Text> texts = new LinkedList<>();
        Text title = new Text("Road Map \n\n");
        title.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        texts.add(title);
        for(Path path : route.getPaths()){
            texts.add(new Text("From intersection "));
            texts.add(new Text(nodeToString(path.getFirstNode())+ "\n"));
            for(Arc arc : path.getArcs()){
                texts.add(new Text("take the road : "));
                texts.add(new Text(arc.getStreetName()+"\n"));
            }
            texts.add(new Text("Then, go to intersection "));
            texts.add(new Text(nodeToString(path.getLastNode())+"\n\n"));
        }
        return texts;
    }

    private static String nodeToString(Node node){
        return node.getId() + " ("+node.getLocation().x+ ", "+node.getLocation().y+")";
    }
    
}
