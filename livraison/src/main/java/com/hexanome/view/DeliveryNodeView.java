package com.hexanome.view;

/**
 * FXML Controller class
 */
import com.hexanome.controller.UIManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.controlsfx.control.PopOver;

public class DeliveryNodeView extends Circle implements INodeViewShape {

    public DeliveryNodeView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ConstView.DELIVERYNODE));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(DeliveryNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onMouseClickedNotify(NodeView context) {
        UIManager.getInstance().NotifyUI(ConstView.Action.CLICK_ON_DELIVERY_NODE, context);
    }
    
    @Override
    public Node asSceneNode() {
        return this;
    }

    @Override
    public PopOver createPopOver(com.hexanome.model.Node node) {
        return new PopOver(new PopOverContentDelivery(node));
    }

}
