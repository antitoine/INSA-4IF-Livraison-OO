package com.hexanome.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

public class MainWindow implements Initializable {

    @FXML
    private TreeView<String> livraisonsTreeView;
    @FXML
    private MenuItem quitMenuItem;
    @FXML
    private Label labelInfos;

    @FXML
    private BorderPane mainPane;
    
    public enum ACTION {
        QUIT, OPEN
    }

    @FXML
    private void quitApplication(ActionEvent event) {
        // UIManager.Notify(ACTION.QUIT);
        System.exit(0);
    }

    public MainWindow() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {



    }


}
