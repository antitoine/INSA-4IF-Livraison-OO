/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.model;

/**
 *
 * @author paul
 */
public class Arc {
    private String _streetName;
    private float _length;
    private float _avgSpeed;
    private float _duration;
    private Node _dest;
    private Node _src;
    
    public Arc(String streetName, float length, float avgSpeed, Node src, Node dest) {
        _streetName = streetName;
        _length = length;
        _avgSpeed = avgSpeed;
        _duration = length*avgSpeed; // Unit : s
        _dest = dest;
        _src = src;
    }
    
    public float getDuration() {
        return _duration;
    }
}
