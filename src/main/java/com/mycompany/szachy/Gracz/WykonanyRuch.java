/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.szachy.Gracz;

import com.mycompany.szachy.pole.Ruch;
import com.mycompany.szachy.pole.Szachownica;
import com.mycompany.szachy.Gracz.StatusRuchu;

/**
 *
 * @author adamk
 */
public class WykonanyRuch //reprezentuje informacje o ruchach pionkami na kolejne pola
{
    private final Szachownica obecnaSzachownica; //pole na które pójdzie pionek
    private final Ruch ruch;
    private final StatusRuchu statusRuchu; //czy można wykonać ruch
    
    public WykonanyRuch(final Szachownica obecnaSzachownica, final Ruch ruch, final StatusRuchu statusRuchu)
    {
        this.obecnaSzachownica=obecnaSzachownica;
        this.ruch=ruch;
        this.statusRuchu= statusRuchu;
    }
    
    public StatusRuchu getStatusRuchu()
    {
        return this.statusRuchu;
    }
    
    public Szachownica getSzachownica()
    {
        return this.obecnaSzachownica;
    }
}
