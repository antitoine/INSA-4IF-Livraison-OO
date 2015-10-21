
package com.hexanome.view;

import javafx.event.ActionEvent;
import javafx.event.Event;

public class BtnListener implements javafx.event.EventHandler<ActionEvent>{

    @Override
    public void handle(ActionEvent event) {
        System.out.println(""+event.toString()+"-> "+event.getSource()+"  "+event.getTarget());
    }

}
