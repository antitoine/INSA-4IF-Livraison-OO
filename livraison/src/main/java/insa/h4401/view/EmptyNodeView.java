package insa.h4401.view;

import insa.h4401.controller.ContextManager;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import org.controlsfx.control.PopOver;

/**
 * This class is the graphic component equivalent of the model node
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class EmptyNodeView extends Circle implements INodeViewShape {
    private insa.h4401.model.Node node;

    public EmptyNodeView(insa.h4401.model.Node node) {
        setFill(Color.web("9e9e9e"));
        setRadius(5.0);
        setStroke(Color.BLACK);
        setStrokeType(StrokeType.INSIDE);
        this.node = node;
    }

    @Override
    public void onMouseClickedNotify(NodeView context) {
        // Call current state related action
        ContextManager.getInstance().getCurrentState().clickOnEmptyNode(node);
    }

    @Override
    public PopOver createPopOver(insa.h4401.model.Node node) {
        return new PopOver(new PopOverContentEmptyNode(node));
    }

    @Override
    public Node asSceneNode() {
        return this;
    }


}
