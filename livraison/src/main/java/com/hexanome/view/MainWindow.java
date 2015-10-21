package com.hexanome.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    static BtnListener btnListener;

    @FXML
    private void quitApplication(ActionEvent event) {
        // TODO : UIManager.Notify(ACTION.QUIT);
        System.exit(0);
    }

    public MainWindow() {
        btnListener = new BtnListener();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public static BtnListener getBtnListener() {
        return btnListener;
    }

}
