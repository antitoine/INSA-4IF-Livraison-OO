package com.hexanome.view;

import com.hexanome.model.Arc;
import java.awt.Point;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * FXML Controller class
 *
 * @author hverlin
 */
public class ArcView extends Pane {

    @FXML
    Line line;

    /**
     * Initializes the controller class.
     *
     * @param arc
     */
    public ArcView(Arc arc) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ConstView.ARCVIEW));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmptyNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }

        Point a = arc.getSrc().getLocation();
        Point b = arc.getDest().getLocation();

        double angle = Math.atan2(b.y - a.y, b.x - a.x);
        double deltaX = Math.cos(angle) * ConstView.SIZE_NODE;
        double deltaY = Math.sin(angle) * ConstView.SIZE_NODE;

        line.setStartX(a.x + deltaX);
        line.setStartY(a.y + deltaY);

        line.setEndX(b.x - deltaX);
        line.setEndY(b.y - deltaY);
        
        Tooltip t = new Tooltip(arc.getDuration()+"");
        t.install(line, t);
        line.setCursor(Cursor.CROSSHAIR);
        
        Point e = new Point((int)(b.x - deltaX), (int)(b.y - deltaY));
        
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(new Double[]{
            (double)(e.x - 1), (double)(e.y + 2),
            (double)(e.x + 2), (double)(e.y),
            (double)(e.x - 1), (double)(e.y - 2)
        });
        
        arrow.setScaleX(2);
        arrow.setScaleY(2);
        getChildren().add(arrow);
        arrow.setRotate(Math.toDegrees(angle));

    }

}
