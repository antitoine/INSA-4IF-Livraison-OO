package com.hexanome.model;

import java.util.Observer;

/**
 *
 * @author guillaume
 */
public interface Observable {
  
    public void addObserver(Observer observer);
    public void deleteObserver(Observer observer);
    public void notidyObservers(Object object);
}
