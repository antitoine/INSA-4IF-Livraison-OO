package com.hexanome.controller.command;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import com.hexanome.controller.states.EmptyNodeSelectedState;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

/**
 * This class represent the action of removing a delivery from the planning
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 * @see ICommand
 */


public class RemoveDeliveryCommand implements ICommand {

    private Delivery delivery;
    private Node nodePreviousDelivery;
    private TimeSlot timeSlot;
    private Node node;

    /**
     * Constructs a new instance of a RemoveDeliveryCommand
     *
     * @param delivery Delivery to be removed
     */
    public RemoveDeliveryCommand(Delivery delivery) {
        this.delivery = delivery;
        this.timeSlot = delivery.getTimeSlot();
        this.node = delivery.getNode();
    }

    /**
     * Execute the command by removing the delivery
     *
     * @return
     * @see ICommand
     */
    @Override
    public ICommand execute() {
        if (ModelManager.getInstance().getPlanning() != null) {
            nodePreviousDelivery = ModelManager.getInstance()
                    .getPlanning().getNodePreviousDelivery(delivery);
            UIManager.getInstance().beginComputingRoute();

            final Service<Void> removeService = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            ModelManager.getInstance().getPlanning().removeDelivery(delivery);
                            return null;
                        }
                    };
                }
            };
            removeService.stateProperty()
                    .addListener(new ChangeListener<Worker.State>() {
                        @Override
                        public void changed(ObservableValue<? extends Worker.State> observableValue,
                                            Worker.State oldValue,
                                            Worker.State newValue) {
                            switch (newValue) {
                                case SUCCEEDED:
                                    ModelManager.getInstance()
                                            .getPlanning().notifySubscribers();
                                    ModelManager.getInstance().getPlanning()
                                            .getRoute().notifySubscribers();
                                    // Jump to EmptyNodeSelectedState
                                    ContextManager.getInstance()
                                            .setCurrentState(EmptyNodeSelectedState.getInstance());
                                    // Open new popover
                                    UIManager.getInstance().getMainWindow()
                                            .repositionToLatestPosition();
                                    UIManager.getInstance().getMainWindow().getMapView()
                                            .showPopOver(delivery.getNode());
                                    break;
                            }
                        }
                    });
            removeService.start();
        } else {
            // \todo treat error case
        }
        return this;
    }

    /**
     * Reverse command execution by bringing back the removed delivery
     *
     * @return
     * @see ICommand
     */
    @Override
    public ICommand reverse() {
        if (ModelManager.getInstance().getPlanning() != null) {
            delivery = ModelManager.getInstance().getPlanning().addDelivery(node, nodePreviousDelivery, timeSlot);
        } else {
            // \todo treat error case
        }
        return this;
    }
}
