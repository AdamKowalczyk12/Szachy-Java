/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.szachy;

import com.mycompany.szachy.pole.Szachownica;
import com.szachy.gui.Tablica;

/**
 *
 * @author adamk
 */
public class Szachy {

    public static void main(String[] args) 
    {
        Szachownica szachownica = Szachownica.stwórzPlanszę();
        
        System.out.println(szachownica);
        
        Tablica tablica = new Tablica();
    }
}
