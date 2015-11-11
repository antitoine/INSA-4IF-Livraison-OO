package com.hexanome.model;

import com.hexanome.utils.AbstractTSP;

import java.util.Collection;

/**
 * This class represents \todo javadoc here
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class RouteTSP extends AbstractTSP {

    @Override
    protected int bound(Integer sommetCourant, Collection<Integer> nonVus) {
        return 0;
        // \todo Améliorer la borne pour accélérer l'algo
    }
}
