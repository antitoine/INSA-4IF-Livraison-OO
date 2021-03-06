/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insa.h4401.utils;

/**
 * A class can implement the Subscriber interface when it wants
 * to be informed of changes in Publisher objects.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public interface Subscriber {
    void update(Publisher p, Object arg);

}
