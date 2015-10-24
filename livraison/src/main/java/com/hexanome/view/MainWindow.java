package com.hexanome.view;

import com.hexanome.controller.UIManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainWindow extends AnchorPane {

    @FXML
    private TreeView<String> livraisonsTreeView;
    @FXML
    private MenuItem quitMenuItem;

    @FXML
    private Label labelInfos;

    @FXML
    private BorderPane mainPane;
    
    @FXML
    private MapView mapView;

    
    static BtnListener btnListener;

    public MainWindow() {
        btnListener = new BtnListener();
                
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ConstView.MAINWINDOW));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmptyNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mapView.initialize(null, null);
       
    }

    public static BtnListener getBtnListener() {
        return btnListener;
    }

    /**
     * 
     * @param event 
     */
    @FXML
    private void quitApplication(ActionEvent event) {
        UIManager.NotifyUI(ConstView.Action.QUIT);
    }
    
    /**
     *
     * @return
     */
    public TreeView<String> getLivraisonsTreeView() {
        return livraisonsTreeView;
    }

    public MapView getMapView() {
        return mapView;
    }

}
