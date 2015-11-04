package com.hexanome.view;

import com.hexanome.model.Arc;
import com.hexanome.model.TimeSlot;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class ArcView extends Pane {

    final double D = 10;
    ConstView.ArcViewType typeOfNonDeliveryNode;
    LinkedList<Color> colors;
    LinkedList<Arc> arcs;
    /**
     * Initializes the controller class.
     *
     * @param tsArcs
     * @param type
     */
    public ArcView(final LinkedList<Arc> tsArcs, ConstView.ArcViewType type) {
        
        setMouseTransparent(true);
        this.typeOfNonDeliveryNode = type;
        colors = new LinkedList<>();
        
        arcs = new LinkedList<>(tsArcs);
        
        boolean moreThanOneTimeSlot;
        for (Arc arc : tsArcs) {
            moreThanOneTimeSlot = false;
            if (arc.getAssociatedTimeSlots().isEmpty()) {
                if (type == ConstView.ArcViewType.STANDARD) {
                    colors.add(Color.GRAY);
                } else {
                    colors.add(Color.BLACK);
                }
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

    public void addArcs(LinkedList<Arc> arcs) {

        ArrayList<Node> arcElements = new ArrayList<>();

        int arcNb = arcs.size();

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
        }

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

            double ptCtrlX1 = ptMiddleX + (i * D * vecOrthoMiddleX) / (normVecOrthoMiddle);
            double ptCtrlY1 = ptMiddleY + (i * D * vecOrthoMiddleY) / (normVecOrthoMiddle);

            double ptCtrlX2 = ptMiddleX - (i * D * vecOrthoMiddleX) / (normVecOrthoMiddle);
            double ptCtrlY2 = ptMiddleY - (i * D * vecOrthoMiddleY) / (normVecOrthoMiddle);

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
        }

        getChildren().addAll(arcElements);

    }

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

    private Polygon drawArrow(Point src, Point dest) {
        double angle = Math.atan2(dest.y - src.y, dest.x - src.x);
        double deltaX = Math.cos(angle) * ConstView.SIZE_NODE;
        double deltaY = Math.sin(angle) * ConstView.SIZE_NODE;

        double endX = dest.x - deltaX;
        double endY = dest.y - deltaY;

        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(new Double[]{
            (endX - 1), (endY + 2),
            (endX + 2), (endY),
            (endX - 1), (endY - 2)
        });

        arrow.setScaleX(1.3);
        arrow.setScaleY(1.3);
        arrow.setRotate(Math.toDegrees(angle));

        return arrow;
    }

    private double distance(double xa, double ya, double xb, double yb) {
        double a = (xa - xb) * (xa - xb);
        double b = (ya - yb) * (ya - yb);
        return Math.sqrt(a + b);
    }

}
