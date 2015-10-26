
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.controller;

/**
 * @author antoine
 *
 */
public interface ICommand {
    /**
     * 
     * @return 
     */
    public ICommand execute();
    /**
     * 
     * @return 
     */
    public ICommand reverse();
}
