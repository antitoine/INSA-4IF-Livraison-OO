/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Abstract class implementing the ITSP interface.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class DefaultTSP implements ITSP {

    private Integer[] bestSolutions;
    private IGraph graph;
    private float costBestSolution;
    private int limitTime;
    private long startTime;

    @Override
    public Integer[] computeSolution(int timeLimit, IGraph graph) throws InterruptedException {
        if (timeLimit <= 0) {
            return null;
        }
        startTime = System.currentTimeMillis();
        this.limitTime = timeLimit;
        this.graph = graph;
        bestSolutions = new Integer[graph.getNbArcs()];
        Collection<Integer> nonVus = new ArrayList<>(graph.getNbArcs() - 1);
        for (int i = 1; i < graph.getNbArcs(); i++) {
            nonVus.add(i);
        }
        Collection<Integer> vus = new ArrayList<>(graph.getNbArcs());
        vus.add(0); // le premier sommet visite est 0
        costBestSolution = Float.MAX_VALUE;
        branchAndBound(0, nonVus, vus, 0);

        return bestSolutions;
    }

    @Override
    public float getSolutionCost() {
        if (graph != null) {
            return costBestSolution;
        }
        return -1;
    }

    /**
     * Check if the thread is not interrupted.
     *
     * @throws InterruptedException Throws the exception if the thread is
     *                              interrupted.
     */
    private void checkInterruption() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
    }

    /**
     * Methode devant etre redefinie par les sous-classes de TemplateTSP
     *
     * @param sommetCourant
     * @param nonVus
     * @return une borne inferieure du cout des chemins de <code>g</code>
     * partant de <code>sommetCourant</code>, visitant tous les sommets de
     * <code>nonVus</code> exactement une fois, puis retournant sur le sommet
     * <code>0</code>.
     */
    protected int bound(Integer sommetCourant, Collection<Integer> nonVus) {
        return 0;
    }

    /**
     * Returns an iterator to iterate on every unviewed edge following the currentEdge.
     *
     * @param currentEdge
     * @param nonVus
     * @param graph
     * @return
     */
    private Iterator<Integer> iterator(Integer currentEdge, Collection<Integer> nonVus, IGraph graph) {
        return new TSLGraphIterator(nonVus, currentEdge, graph);
    }

    /**
     * Methode definissant le patron (template) d'une resolution par separation
     * et evaluation (branch and bound) du TSP pour le graphe <code>g</code>.
     *
     * @param sommetCrt le dernier sommet visite
     * @param nonVus    la liste des sommets qui n'ont pas encore ete visites
     * @param vus       la liste des sommets deja visites (y compris sommetCrt)
     * @param coutVus   la somme des couts des arcs du chemin passant par tous les
     *                  sommets de vus dans l'ordre ou ils ont ete visites
     */
    private void branchAndBound(int sommetCrt, Collection<Integer> nonVus, Collection<Integer> vus, float coutVus) throws InterruptedException {
        checkInterruption();
        if (System.currentTimeMillis() - startTime > limitTime) {
            return;
        }
        if (nonVus.isEmpty()) { // tous les sommets ont ete visites
            if (graph.isArc(sommetCrt, 0)) { // on peut retourner au sommet de depart (0)
                if (coutVus + graph.getCost(sommetCrt, 0) < costBestSolution) { // on a trouve une solution meilleure que meilleureSolution
                    vus.toArray(bestSolutions);
                    costBestSolution = coutVus + graph.getCost(sommetCrt, 0);
                }
            }
        } else if (coutVus + bound(sommetCrt, nonVus) < costBestSolution) {
            Iterator<Integer> it = iterator(sommetCrt, nonVus, graph);

            while (it.hasNext()) {
                checkInterruption();
                Integer prochainSommet = it.next();
                vus.add(prochainSommet);
                nonVus.remove(prochainSommet);
                branchAndBound(prochainSommet, nonVus, vus, coutVus + graph.getCost(sommetCrt, prochainSommet));
                vus.remove(prochainSommet);
                nonVus.add(prochainSommet);
            }
        }
    }
}
