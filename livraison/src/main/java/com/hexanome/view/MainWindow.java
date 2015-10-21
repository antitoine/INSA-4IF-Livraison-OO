package com.hexanome.livraison;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;

public class FXMLController implements Initializable {

    @FXML
    private TreeView<String> livraisonsTreeView;

    @FXML
    private Canvas graphCanvas;
    
    @FXML
    private MenuItem quitMenuItem;

    @FXML
    private void quitApplication(ActionEvent event) {
        System.exit(0);
    }
    
    GraphicsContext gc;

    public FXMLController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Exemple d'ajout d'item à la treeView
        TreeItem<String> rootItem = new TreeItem<>("Emploi du temps");
        rootItem.setExpanded(true);
        
        TreeItem<String> fenetre_8h10h = new TreeItem<>("8h-10h");
        TreeItem<String> fenetre_10h12h = new TreeItem<>("10h-12h");
        TreeItem<String> fenetre_12h14h = new TreeItem<>("12h-14h");
        rootItem.getChildren().add(fenetre_8h10h);
        rootItem.getChildren().add(fenetre_10h12h);
        rootItem.getChildren().add(fenetre_12h14h);
        
        TreeItem<String> livraison_1 = new TreeItem<>("livraison 1");
        fenetre_10h12h.getChildren().add(livraison_1);
        
        livraisonsTreeView.setRoot(rootItem);
        
        // Exemple de manipulation du canvas
        gc = graphCanvas.getGraphicsContext2D();
        drawShapes(gc);
        
    }
    
    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillOval(10, 60, 30, 30);
        gc.fillOval(50, 200, 30, 30);

    }
}
