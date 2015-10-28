package com.hexanome.view;

import com.hexanome.model.Arc;
import com.hexanome.model.Map;
import com.hexanome.model.Node;
import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;
import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * Reprensents a view of the map with nodes and arc
 *
 * This is a one-to-one representation of the Map model
 */
public class MapView extends AnchorPane implements Subscriber, Initializable {

    static HashMap<Node, NodeViewWrapper> nodeList;

    /**
     * Initializes the controller class.
     */
    public void MapView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ConstView.MAPVIEW));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmptyNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Add a node a the position pt.x & pt.y
     *
     * @param Type Node Type
     * @param node node as describes in the model
     */
    private void addNode(String Type, Node node) {
        NodeViewWrapper wrapper = new NodeViewWrapper(node, Type);
        NodeView nodeview = wrapper.getNodeView();
        getChildren().add(nodeview);
        nodeview.toFront();
        nodeview.relocate(node.getLocation().x - nodeview.getPrefWidth() / 2,
                node.getLocation().y - nodeview.getPrefHeight() / 2);
        nodeList.put(node, wrapper);

    }

    /**
     * Swap two nodes
     *
     * @param pt1
     * @param pt2
     */
    private void swapNode(Point pt1, Point pt2) {
        // TODO
    }

    public void addArc(Arc arc) {
        MapView mv = this;
        ArcView av = new ArcView(arc);
        mv.getChildren().add(av);
        av.toBack();
    }

    public void deleteArc(Point pt1, Point pt2) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nodeList = new HashMap<>();
    }

    @Override
    public void update(Publisher p, Object arg) {
        if (p instanceof Map) {
            for (Entry<Integer, Node> n : ((Map) (p)).getNodes().entrySet()) {
                addNode(ConstView.EMPTYNODE, n.getValue());
            }
            for (Arc a : ((Map) (p)).getArcs()) {
                addArc(a);
            }
        }
    }

    public void ClearMap() {
        nodeList.clear();
        getChildren().clear();
    }


}
