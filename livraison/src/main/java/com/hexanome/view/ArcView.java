package com.hexanome.view;

import com.hexanome.model.Arc;
import com.hexanome.model.TimeSlot;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class is the graphic component equivalent to the arc defined
 * in the model
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class ArcView {

    final double ARC_DISTANCE = 4;
    final double COEF_ARC_DISTANCE = 0.8;
    LinkedList<Color> colors;
    LinkedList<Arc> arcs;
    Pane mapPane;

    /**
     * Builds a new instance of arc view
     * @param tsArcs list of arcs for the arcView
     */
    public ArcView(final LinkedList<Arc> tsArcs, Pane pane) {
        // setMouseTransparent(true);
        mapPane = pane;
        colors = new LinkedList<>();
        arcs = new LinkedList<>(tsArcs);

        boolean moreThanOneTimeSlot;
        for (Arc arc : tsArcs) {
            moreThanOneTimeSlot = false;
            if (arc.getAssociatedTimeSlots().isEmpty()) {
                colors.add(Color.GRAY);
            } else {
                for (TimeSlot ts : arc.getAssociatedTimeSlots()) {
                    if (!moreThanOneTimeSlot) {
                        moreThanOneTimeSlot = true;
                    } else {
                        arcs.add(arc);
                    }
                    colors.add(ColorsGenerator.getTimeSlotColor(ts));
                }
            }
        }
        addArcs(arcs);
    }

    /**
     * Draws arcs in the view 
     * @param arcs 
     *      Arcs to be drawn in the view
     */
    public void addArcs(LinkedList<Arc> arcs) {
        ArrayList<Node> arcElements = new ArrayList<>();
        boolean isATwoWayTrip = false;
        if (colors.size() <= 2) {
            isATwoWayTrip = true;
            Color tempColor = colors.get(0);
            for (Color c : colors) {
                if (!c.equals(tempColor)) {
                    isATwoWayTrip = false;
                    break;
                }
            }
        }

        int numberOfArcs;
        if (isATwoWayTrip) {
            numberOfArcs = 0;
        } else {
            numberOfArcs = arcs.size();
        }

        int arcNb = arcs.size();

        // Draws a line between two nodes
        if ((arcNb % 2) == 1) {
            // draw line
            Arc arc = arcs.remove(0);
            Color color = colors.remove(0);

            Line line = drawLine(arc.getSrc().getLocation(), arc.getDest().getLocation());
            Polygon arrow = drawArrow(arc.getSrc().getLocation(), arc.getDest().getLocation());
            arrow.setFill(color);
            line.setStroke(color);
            arcElements.add(line);
            arcElements.add(arrow);
            arcNb--;
            Tooltip t1 = new Tooltip(arc.toString());
            Tooltip.install(line, t1);
            line.setCursor(Cursor.CROSSHAIR);
        }

        // Draws as many cubic curves as needed between two nodes
        for (int i = 1; i <= arcNb / 2; i++) {
            Arc a1 = arcs.remove(0);
            Arc a2 = arcs.remove(0);
            Color color1 = colors.remove(0);
            Color color2 = colors.remove(0);

            double ptStartX = a1.getSrc().getLocation().x;
            double ptStartY = a1.getSrc().getLocation().y;

            double ptEndX = a1.getDest().getLocation().x;
            double ptEndY = a1.getDest().getLocation().y;

            double vecMiddleX = 0.5 * (ptEndX - ptStartX);
            double vecMiddleY = 0.5 * (ptEndY - ptStartY);

            double ptMiddleX = ptStartX + vecMiddleX;
            double ptMiddleY = ptStartY + vecMiddleY;

            double vecOrthoMiddleX = -vecMiddleY;
            double vecOrthoMiddleY = vecMiddleX;

            double normVecOrthoMiddle = Math.sqrt((vecOrthoMiddleX * vecOrthoMiddleX)
                    + (vecOrthoMiddleY * vecOrthoMiddleY));

            double coef = i * ARC_DISTANCE * (numberOfArcs * COEF_ARC_DISTANCE);
            double ptCtrlX1 = ptMiddleX + (coef * vecOrthoMiddleX) / (normVecOrthoMiddle);
            double ptCtrlY1 = ptMiddleY + (coef * vecOrthoMiddleY) / (normVecOrthoMiddle);

            double ptCtrlX2 = ptMiddleX - (coef * vecOrthoMiddleX) / (normVecOrthoMiddle);
            double ptCtrlY2 = ptMiddleY - (coef * vecOrthoMiddleY) / (normVecOrthoMiddle);

            CubicCurve curve1 = new CubicCurve(ptStartX, ptStartY,
                    ptCtrlX1, ptCtrlY1, ptCtrlX1, ptCtrlY1, ptEndX, ptEndY);
            Polygon arrow1 = drawArrow(new Point((int) (ptCtrlX1), (int) (ptCtrlY1)),
                    a1.getDest().getLocation());

            CubicCurve curve2 = new CubicCurve(ptStartX, ptStartY,
                    ptCtrlX2, ptCtrlY2, ptCtrlX2, ptCtrlY2, ptEndX, ptEndY);
            Polygon arrow2 = drawArrow(new Point((int) (ptCtrlX2), (int) (ptCtrlY2)),
                    a2.getDest().getLocation());

            curve1.setFill(null);
            curve2.setFill(null);
            curve1.setStroke(color1);
            curve2.setStroke(color2);
            arrow1.setFill(color1);
            arrow2.setFill(color2);

            arcElements.add(curve1);
            arcElements.add(arrow1);
            arcElements.add(curve2);
            arcElements.add(arrow2);

            if (isATwoWayTrip) {
                Tooltip t1 = new Tooltip(a1.toString() + a2.toString());
                Tooltip.install(curve2, t1);
                curve2.setCursor(Cursor.CROSSHAIR);
            } else {
                Tooltip t1 = new Tooltip(a1.toString());
                Tooltip t2 = new Tooltip(a2.toString());
                Tooltip.install(curve1, t1);
                Tooltip.install(curve2, t2);
                curve1.setCursor(Cursor.CROSSHAIR);
                curve2.setCursor(Cursor.CROSSHAIR);
            }

        }

        mapPane.getChildren().addAll(arcElements);

    }

    /**
     * Draws an arrow on an arc 
     * @param src
     * @param dest
     * @return 
     */
    private Polygon drawArrow(Point src, Point dest) {
        double angle = Math.atan2(dest.y - src.y, dest.x - src.x);
        double deltaX = Math.cos(angle) * ConstView.SIZE_NODE;
        double deltaY = Math.sin(angle) * ConstView.SIZE_NODE;

        double endX = dest.x - deltaX;
        double endY = dest.y - deltaY;

        Polygon arrow = new Polygon();
        arrow.getPoints().addAll((endX - 1), (endY + 2),
                (endX + 2), (endY),
                (endX - 1), (endY - 2));

        arrow.setScaleX(1.3);
        arrow.setScaleY(1.3);
        arrow.setRotate(Math.toDegrees(angle));

        return arrow;
    }

    /**
     * Draws a line between two points
     * @param src
     * @param dest
     * @return 
     */
    private Line drawLine(Point src, Point dest) {
        Line line = new Line();

        double angle = Math.atan2(dest.y - src.y, dest.x - src.x);

        double deltaX2 = Math.cos(angle) * (ConstView.SIZE_NODE + 1);
        double deltaY2 = Math.sin(angle) * (ConstView.SIZE_NODE + 1);

        line.setStartX(src.x);
        line.setStartY(src.y);

        line.setEndX(dest.x - deltaX2);
        line.setEndY(dest.y - deltaY2);

        return line;
    }


}
