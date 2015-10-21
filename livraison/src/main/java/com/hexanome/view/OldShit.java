/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.view;

/**
 *
 * @author hverlin
 */
public class OldShit {
        /**
//     * Dessine sur le canevas
//     * @param gc
//     */
//    private void drawShapes(GraphicsContext gc) {
//        gc.setFill(Color.GREEN);
//        gc.fillOval(10, 60, 30, 30);
//        gc.fillOval(50, 200, 30, 30);
//    }
//
//    private void drawNode() {
//        final Rectangle rect1 = RectangleBuilder.create()
//                .x(50)
//                .y(50)
//                .width(20)
//                .height(20)
//                .build();
//
//        Rectangle rect2;
//        rect2 = RectangleBuilder.create()
//                .x(500)
//                .y(100)
//                .width(100)
//                .height(100)
//                .fill(Color.BLUE)
//                .build();
//
//        final Line line = new Line(400, 400, 250, 50);
//        line.setStrokeWidth(5);
//        line.setOnMouseEntered(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//             line.setStroke(Color.DARKGRAY);
//            }
//        });
//        line.setOnMouseExited(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//             ((Line)event.getSource()).setStroke(Color.BLACK);
//            }
//        });
//
//        final Rectangle roundRect = RectangleBuilder.create()
//                .x(250)
//                .y(50)
//                .width(40)
//                .height(50)
//                .arcWidth(30)
//                .arcHeight(30)
//                .fill(Color.RED)
//                .build();
//
//        rect1.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                PopOver po = new PopOver( new Button("", new Glyph("FontAwesome", "BEER")));
//                mainPane.getChildren().add(po.getContentNode());
//                po.setAutoHide(true);
//                po.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
//                po.show(rect1);
//                po.setDetachable(false);
//                po.setMinWidth(200);
//                po.setMinHeight(100);
//                
//                System.out.println(po.isShowing() + "");
//            }
//        });
//   
//
////        mainPane.getChildren()
////                .add(rect1);
////        mainPane.getChildren()
////                .add(rect2);
////        //       mainPane.getChildren().add(roundRect);
////        mainPane.getChildren().add(line);
//        
//    }
//
//    /**
//     * Créé une branche à partir d'un noeud spécifié
//     *
//     * @param title titre du noeud
//     * @param parent parent
//     * @return le noeud créé
//     */
//    private TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
//        TreeItem<String> item = new TreeItem<>(title);
//        item.setExpanded(true);
//        parent.getChildren().add(item);
//        return item;
//    }
//    
//    
//    public void intialize() {
//        
//                // Exemple d'ajout d'item à la treeView
//        TreeItem<String> rootItem = new TreeItem<>();
//        rootItem.setExpanded(true);
//
//        TreeItem<String> fenetre_8h10h = makeBranch("8h-10h", rootItem);
//        TreeItem<String> fenetre_10h12h = makeBranch("10h-12h", rootItem);
//        TreeItem<String> fenetre_12h14h = makeBranch("12h-14h", rootItem);
//
//        TreeItem<String> livraison_1 = makeBranch("livraison 1", fenetre_8h10h);
//        TreeItem<String> livraison_2 = makeBranch("livraison 2", fenetre_10h12h);
//        TreeItem<String> livraison_3 = makeBranch("livraison 3", fenetre_10h12h);
//        TreeItem<String> livraison_4 = makeBranch("livraison 4", fenetre_12h14h);
//
////        livraisonsTreeView.setRoot(rootItem);
////        livraisonsTreeView.setShowRoot(false);
////
////        livraisonsTreeView.getSelectionModel().selectedItemProperty()
////                .addListener(new ChangeListener<TreeItem<String>>() {
////
////                    @Override
////                    public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
////                        labelInfos.setText(newValue.getValue());
////                    }
////                });
////
////        drawNode();
//    }
//    
    
    
}
