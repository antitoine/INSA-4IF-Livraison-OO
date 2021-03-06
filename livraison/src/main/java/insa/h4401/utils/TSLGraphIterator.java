/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insa.h4401.utils;

import java.util.Collection;
import java.util.Iterator;

/**
 * Iterator on IGraph for ITSP algorithm.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
class TSLGraphIterator implements Iterator<Integer> {

    private Integer[] candidates;
    private int nbCandidates;

    /**
     * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus qui
     * sont successeurs de sommetCrt dans le graphe g, dans l'odre d'apparition
     * dans <code>nonVus</code>
     */
    public TSLGraphIterator(Collection<Integer> nonVus, int sommetCrt, IGraph g) {
        this.candidates = new Integer[nonVus.size()];
        for (Integer s : nonVus) {
            if (g.isArc(sommetCrt, s)) {
                candidates[nbCandidates++] = s;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nbCandidates > 0;
    }

    @Override
    public Integer next() {
        nbCandidates--;
        return candidates[nbCandidates];
    }

    @Override
    public void remove() {
    }

}
