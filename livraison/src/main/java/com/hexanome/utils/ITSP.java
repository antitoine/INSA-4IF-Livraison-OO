/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.utils;

/**
 * Interface with the methods to use to resolve the Travelling Salesman Problem.
 * @author Pierre Jarsaillon
 */
public interface ITSP {

    /**
     * Find a solution to the TSP problem, for the graph passed by argument,
     * before the time limit expires. The solution returned begins by the first
     * edge stored in the graph.
     *
     * @param timeLimit The limit of time, in ms.
     * @param graph The graph to analyse.
     * @return Array with the solutions : the value at the index "i" is the index
     * of the previous node.
     * @throws java.lang.InterruptedException Throws the exception in case of 
     * interruption.
     */
    public Integer[] computeSolution(int timeLimit, IGraph graph) throws InterruptedException;

    /**
     * @return The total cost of the last computed solution, or -1 if computeSolution
     * was never called.
     */
    public float getSolutionCost();
}
