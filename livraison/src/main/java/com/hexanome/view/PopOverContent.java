package com.hexanome.view;

import com.hexanome.model.Node;
import javafx.scene.layout.BorderPane;

public abstract class PopOverContent extends BorderPane{
    
    Node node;

    public PopOverContent(String popOverType, Node node) {      
        this.node = node;
    }

}
