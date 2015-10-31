/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.utils;

/**
 * Inteface representing a graph which can be used by generic algorithms.
 * @author Pierre Jarsaillon
 */
public interface IGraph {

    /**
     * @return The number of edges of the implemented graph.
     */
    public int getNbEdges();

    /**
     * @param i The number of the initial vertex.
     * @param j The number of the terminal vertex.
     * @return The cost associated with the directed arc (i, j), if it exists. 
     * Otherwise, returns -1.
     */
    public float getCost(int i, int j);

    /**
     * Check if an arc exists.
     * @param i The number of the initial vertex.
     * @param j The number of the terminal vertex.
     * @return True if the directed arc (i, j) exists, false otherwise.
     */
    public boolean isArc(int i, int j);

}
