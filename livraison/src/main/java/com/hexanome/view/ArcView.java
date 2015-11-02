package com.hexanome.view;

import com.hexanome.model.Arc;
import java.awt.Point;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;


public class ArcView extends Pane {

    Line line;

    /**
     * Initializes the controller class.
     *
     * @param arc
     * @param type
     */
    public ArcView(Arc arc, ConstView.ArcViewType type) {
        line = new Line();
        line.setStrokeWidth(3.0);
        line.setStroke(Color.GRAY);
        getChildren().add(line);

        Point a = arc.getSrc().getLocation();
        Point b = arc.getDest().getLocation();

        double angle = Math.atan2(b.y - a.y, b.x - a.x);
        double deltaX = Math.cos(angle) * ConstView.SIZE_NODE;
        double deltaY = Math.sin(angle) * ConstView.SIZE_NODE;

        double deltaX2 = Math.cos(angle) * (ConstView.SIZE_NODE + 4);
        double deltaY2 = Math.sin(angle) * (ConstView.SIZE_NODE + 4);

        line.setStartX(a.x + deltaX2);
        line.setStartY(a.y + deltaY2);

        line.setEndX(b.x - deltaX2);
        line.setEndY(b.y - deltaY2);

        double endX = b.x - deltaX;
        double endY = b.y -deltaY;
        
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(new Double[]{
             (endX - 1), (endY + 2),
             (endX + 2), (endY),
             (endX - 1), (endY - 2)
        });

        arrow.setScaleX(2);
        arrow.setScaleY(2);
        getChildren().add(arrow);
        arrow.setRotate(Math.toDegrees(angle));

        if (type == ConstView.ArcViewType.ROUTE) {
            if (arc.getAssociatedTimeSlot() != null) {
                line.setStroke(ColorsGenerator.getTimeSlotColor(arc.getAssociatedTimeSlot()));
                arrow.setFill(ColorsGenerator.getTimeSlotColor(arc.getAssociatedTimeSlot()));
            } else {
                line.setStroke(Color.BLACK);
            }
        } else {
            arrow.setFill(Color.GRAY);
        }

        setMouseTransparent(true);
    }

}
