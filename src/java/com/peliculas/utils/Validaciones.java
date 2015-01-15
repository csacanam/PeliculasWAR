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
    
    /**
     * Permite determinar si una cadena es vacía o no
     * @param cadena Cadena que se desea evaluar
     * @return True si es vacía, false en caso contrario
     */
    public static boolean esVacio(String cadena)
    {
        if (cadena == null)
        {
            return true;
        }

        return cadena.equals("");
    }
}
