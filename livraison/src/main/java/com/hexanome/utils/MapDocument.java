/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.utils;
import java.util.ArrayList;
import javafx.scene.Node;
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
    
    public ArrayList<Node> getNodes()
    {
        Element rootNode = dom.getRootElement();
        
        return null;
    }
}
