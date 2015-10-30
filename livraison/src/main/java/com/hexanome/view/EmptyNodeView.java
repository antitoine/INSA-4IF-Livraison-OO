package com.hexanome.view;

import com.hexanome.controller.UIManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 */
public class EmptyNodeView extends Circle implements INodeViewShape {

    /**
     * Initializes the controller class.
     */
    public EmptyNodeView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ConstView.EMPTYNODE));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmptyNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onMouseClickedNotify(NodeView context) {
        UIManager.getInstance().NotifyUI(ConstView.Action.CLICK_ON_EMPTY_NODE, context);
    }

    @Override
    public Node asSceneNode() {
        return this;
    }

    @Override
    public PopOver createPopOver(com.hexanome.model.Node node) {
        return new PopOver(new PopOverContentEmptyNode(node));
    }

}
