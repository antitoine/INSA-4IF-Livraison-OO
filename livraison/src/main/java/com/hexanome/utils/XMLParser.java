/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.utils;

import org.jdom2.Document;

/**
 * This class provides a generic XML parser.
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class XMLParser {
   
    /**
     * DOM Document representing the XML data to parse
     */
    private Document dom;
    /**
     * String containing the latest error message if an error occured while 
     * parsing XML data
     */
    private String error;
    
    /**
     * Builds a new instance of XMLParser
     * @param dom
     *      DOM Document to parse
     */
    XMLParser(Document dom) {
        this.dom = dom;
        this.error = null;
    }

    /**
     * Returns the latest error message or null if no error message was set
     * @return 
     */
    public String getErrorMsg() {
        return error;
    }

    /**
     * Set the error message of the parser
     * @param msg
     */
    void setErrorMsg(String msg) {
        error = String.format("Semantic error : %s", msg);
    }
    
    /**
     * Returns the DOM Document
     * @return
     */
    Document getDom() {
        return dom;
    }
}
