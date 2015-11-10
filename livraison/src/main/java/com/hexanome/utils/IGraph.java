/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.utils;

/**
 * Inteface representing a graph which can be used by generic algorithms.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public interface IGraph {

    /**
     * @return The number of arcs of the implemented graph.
     */
    int getNbArcs();

    /**
     * @param i The number of the initial vertex. (starts to 0)
     * @param j The number of the terminal vertex. (starts to 0)
     * @return The cost associated with the directed arc (i, j), if it exists.
     * Otherwise, returns -1.
     */
    float getCost(int i, int j);

    /**
     * Check if an arc exists.
     *
     * @param i The number of the initial vertex. (starts to 0)
     * @param j The number of the terminal vertex. (starts to 0)
     * @return True if the directed arc (i, j) exists, false otherwise.
     */
    boolean isArc(int i, int j);

}
