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
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map.Entry;

/**
 * This class is the graphic component equivalent to the arc defined
 * in the model
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class ArcView {

    private LinkedList<Entry<Arc, Color>> arcs;
    private Pane mapPane;

    /**
     * Builds a new instance of arc view
     * @param tsArcs list of arcs for the arcView
     */
    public ArcView(final LinkedList<Arc> tsArcs, Pane pane) {
        mapPane = pane;
        arcs = new LinkedList<>();

        for (Arc arc : tsArcs) {
            if (arc.getAssociatedTimeSlots().isEmpty()) {
                arcs.add(new AbstractMap.SimpleEntry<>(arc, Color.GRAY));
            } else {
                for (TimeSlot ts : arc.getAssociatedTimeSlots()) {
                    arcs.add(new AbstractMap.SimpleEntry<>(arc,
                            ColorsGenerator.getTimeSlotColor(ts)));
                }
            }
        }
    }


    private boolean isATwoWayTrip() {
        boolean isATwoWayTrip = false;
        if (arcs.size() == 2) {
            isATwoWayTrip = true;
            Color tempColor = arcs.get(0).getValue();
            for (Entry<Arc, Color> entry : arcs) {
                if (!entry.getValue().equals(tempColor)) {
                    isATwoWayTrip = false;
                    break;
                }
            }
        }
        return isATwoWayTrip;
    }

    /**
     * Draws arcs in the view
     */
    void addArcs() {
        ArrayList<Node> arcElements = new ArrayList<>();
        boolean isATwoWayTrip = isATwoWayTrip();

        int arcNb = arcs.size();

        // Draws a line between two nodes
        if ((arcNb % 2) == 1) {
            // draw line
            Entry<Arc, Color> entry = arcs.remove(0);
            Arc arc = entry.getKey();
            Color color = entry.getValue();

            Line line = drawLine(arc.getSrc().getLocation(), arc.getDest().getLocation());
            Polygon arrow = drawArrow(arc.getSrc().getLocation(), arc.getDest().getLocation());
            arrow.setFill(color);
            line.setStroke(color);
            arcElements.add(line);
            arcElements.add(arrow);
            arcNb--;
            addTooltip(line, arc);

        }

        // Draws as many cubic curves as needed between two nodes
        for (int i = 1; i <= arcNb / 2; i++) {
            Entry<Arc, Color> entry1 = arcs.remove(0);
            Entry<Arc, Color> entry2 = arcs.remove(0);
            Arc a1 = entry1.getKey();
            Arc a2 = entry2.getKey();
            Color color1 = entry1.getValue();
            Color color2 = entry2.getValue();

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

            double coef = 0;
            if (!isATwoWayTrip) {
                double ARC_DISTANCE = 4;
                coef = i * 2 * ARC_DISTANCE;
            }
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
                addTwoWayTooltip(curve2, a1, a2);
            } else {
                addTooltip(curve2, a2);
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

    private void addTooltip(Node node, Arc arc) {
        String arcDescription = "" +
                "Street: " + arc.getStreetName() + "\n" +
                "Start: " + arc.getSrc().getId() + "\n" +
                "End: " + arc.getDest().getId() + "\n" +
                "Length: " + arc.getLength() + " m\n" +
                "Duration: " + (int) arc.getDuration() + " s";

        Tooltip t1 = new Tooltip(arcDescription);
        Tooltip.install(node, t1);
        node.setCursor(Cursor.CROSSHAIR);
    }

    private void addTwoWayTooltip(Node node, Arc arc1, Arc arc2) {
        String arcDescription1 = "" +
                "Street: " + arc1.getStreetName() + "\n" +
                "Start: " + arc1.getSrc().getId() + "\n" +
                "End: " + arc1.getDest().getId() + "\n" +
                "Length: " + arc1.getLength() + " m\n" +
                "Duration: " + (int) arc1.getDuration() + " s";

        String arcDescription2 = "" +
                "Street: " + arc2.getStreetName() + "\n" +
                "Start: " + arc2.getSrc().getId() + "\n" +
                "End: " + arc2.getDest().getId() + "\n" +
                "Length: " + arc2.getLength() + " m\n" +
                "Duration: " + (int) arc2.getDuration() + " s";

        Tooltip t1 = new Tooltip(arcDescription1 + " \n --- \n" + arcDescription2);
        Tooltip.install(node, t1);
        node.setCursor(Cursor.CROSSHAIR);
    }

}