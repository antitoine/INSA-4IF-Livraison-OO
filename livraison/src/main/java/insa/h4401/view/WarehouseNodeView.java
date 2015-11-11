package insa.h4401.view;

import insa.h4401.controller.ContextManager;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import org.controlsfx.control.PopOver;

/**
 * This class represents the warehouse version of the nodeView
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class WarehouseNodeView extends Rectangle implements INodeViewShape {
    private insa.h4401.model.Node node;

    /**
     * Initializes the controller class.
     */
    public WarehouseNodeView(insa.h4401.model.Node node) {
        setHeight(10);
        setWidth(10);
        setFill(Color.BLACK);
        setStroke(Color.BLACK);
        setStrokeType(StrokeType.INSIDE);

        this.node = node;
    }

    @Override
    public void onMouseClickedNotify(NodeView context) {
        ContextManager.getInstance().getCurrentState().clickOnWarehouse(node);
    }

    @Override
    public PopOver createPopOver(insa.h4401.model.Node node) {
        return new PopOver(new PopOverContentWarehouse(node));
    }

    @Override
    public Node asSceneNode() {
        return this;
    }
}
