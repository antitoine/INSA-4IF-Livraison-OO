/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.model;

import com.hexanome.utils.AbstractTSP;
import java.util.Collection;

/**
 *
 * @author pierre
 */
public class RouteTSP extends AbstractTSP {

    @Override
    protected int bound(Integer sommetCourant, Collection<Integer> nonVus) {
        return 0;
        // \todo Améliorer la borne pour accélérer l'algo
    }

    
}
