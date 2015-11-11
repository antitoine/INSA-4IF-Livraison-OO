/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insa.h4401.utils;

/**
 * This interface describes a Publisher object, or "data" in the model-view paradigm.
 * It can be subclassed to represent an object that the application wants to have observed.
 * A Publisher object can have one or more subsriber, which is an object that implements interface subsriber.
 * After an object that implement Publisher changes, it has to call to the update method of the subscribers.
 * The order in which notifications will be delivered is unspecified.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public interface Publisher {
    void addSubscriber(Subscriber s);

    void removeSubscriber(Subscriber s);

    void notifySubscribers();

    void clearSubscribers();
}
