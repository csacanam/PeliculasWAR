/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peliculas.utils;

/**
 *
 * @author csacanam
 */
public class Validaciones
{
    
    
    public static boolean esVacio(String cadena)
    {
        if (cadena == null)
        {
            return true;
        }

        return cadena.equals("");
    }
}
