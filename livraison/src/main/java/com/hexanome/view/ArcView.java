package com.hexanome.view;

import com.hexanome.model.Arc;
import java.awt.Point;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class ArcView extends Pane {

    final double D = 10;
    ConstView.ArcViewType typeOfNonDeliveryNode;

    /**
     * Initializes the controller class.
     *
     * @param arcs
     * @param type
     */
    public ArcView(ArrayList<Arc> arcs, ConstView.ArcViewType type) {
        setMouseTransparent(true);
        this.typeOfNonDeliveryNode = type;
        addArcs(arcs);
    }

    public void addArcs(ArrayList<Arc> arcs) {

        ArrayList<Node> arcElements = new ArrayList<>();

        int arcNb = arcs.size();

        if ((arcNb % 2) == 1) {
            // draw line
            Arc a = arcs.remove(0);

            Line line = drawLine(a.getSrc().getLocation(), a.getDest().getLocation());
            Polygon arrow = drawArrow(a.getSrc().getLocation(), a.getDest().getLocation());
            setArrowColor(a, arrow);
            setLineColor(a, line);
            arcElements.add(line);
            arcElements.add(arrow);
            arcNb--;
        }

        for (int i = 1; i <= arcNb / 2; i++) {
            Arc a1 = arcs.remove(0);
            Arc a2 = arcs.remove(0);

            double startX = a1.getSrc().getLocation().x;
            double startY = a1.getSrc().getLocation().y;

            double endX = a1.getDest().getLocation().x;
            double endY = a1.getDest().getLocation().y;

            double middleX = 0.5 * (endX + startX);
            double middleY = 0.5 * (endY + startY);
            
            double alpha = Math.atan2(middleY - startY, middleX - startX);
          //  double alpha = Math.atan2( distance(startX, startY, middleX, middleY) , D);

            double ctrlX = i * D * Math.cos(Math.PI * 0.5 - alpha);
            double ctrlY = i * D * Math.sin(Math.PI * 0.5 - alpha);

            CubicCurve curve1 = new CubicCurve(startX, startY,
                    middleX + ctrlX, middleY + ctrlY, middleX + ctrlX, middleY + ctrlY, endX, endY);
            Polygon arrow1 = drawArrow(new Point((int) (middleX + ctrlX), (int) (middleY + ctrlY)),
                    a1.getDest().getLocation());

            CubicCurve curve2 = new CubicCurve(startX, startY,
                    middleX - ctrlX, middleY - ctrlY, middleX - ctrlX, middleY - ctrlY, endX, endY);
            Polygon arrow2 = drawArrow(new Point((int) (middleX - ctrlX), (int) (middleY - ctrlY)),
                    a2.getDest().getLocation());
            
            setCurveColor(a1, curve1);
            setArrowColor(a1, arrow1);
            setCurveColor(a2, curve2);
            setArrowColor(a2, arrow2);

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

    private void setArrowColor(Arc arc, Polygon polygon) {
        if (arc.getAssociatedTimeSlot() != null) {
            polygon.setFill(ColorsGenerator.getTimeSlotColor(arc.getAssociatedTimeSlot()));
        } else if(typeOfNonDeliveryNode == ConstView.ArcViewType.STANDARD){
            polygon.setFill(Color.GRAY);
        } else {
            polygon.setFill(Color.BLACK);
        }
    }

    private void setCurveColor(Arc arc, CubicCurve curve) {
        curve.setFill(null);
        curve.toBack();
        if (arc.getAssociatedTimeSlot() != null) {
            curve.setStroke(ColorsGenerator.getTimeSlotColor(arc.getAssociatedTimeSlot()));
        } else if(typeOfNonDeliveryNode == ConstView.ArcViewType.STANDARD){
            curve.setStroke(Color.GRAY);
        } else {
            curve.setStroke(Color.BLACK);
        }
    }

    private void setLineColor(Arc arc, Line line) {
        line.setStrokeWidth(1.0);
        line.toBack();

        if (arc.getAssociatedTimeSlot() != null) {
            line.setStroke(ColorsGenerator.getTimeSlotColor(arc.getAssociatedTimeSlot()));
        } else if(typeOfNonDeliveryNode == ConstView.ArcViewType.STANDARD){
            line.setStroke(Color.GRAY);
        } else {
            line.setStroke(Color.BLACK);
        }
    }

    private double distance(double xa, double ya, double xb, double yb) {
        double a = (xa - xb) * (xa - xb);
        double b = (ya - yb) * (ya - yb);
        return Math.sqrt(a + b);
    }

}
