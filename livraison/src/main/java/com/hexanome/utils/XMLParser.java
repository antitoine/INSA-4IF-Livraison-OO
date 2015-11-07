/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.utils;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import org.jdom2.Document;

/**
 * This class provides a generic XML parser.
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class XMLParser {
   
    private Document dom;
    private String error;
    /**
     * Builds a new instance of XMLParser
     * @param dom 
     */
    protected XMLParser(Document dom) {
        this.dom = dom;
        this.error = "No error";
    }
    /**
     * Set the error message of the parser
     * @param msg 
     */
    protected void setErrorMsg(String msg) {
        error = String.format("Semantic error : %s", msg);
    }
    /**
     * 
     * @return 
     */
    public String getErrorMsg() {
        return error;
    }
    
    protected Document getDom() {
        return dom;
    }
}
