/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.szachy.pionek;

import com.google.common.collect.ImmutableList;
import com.mycompany.szachy.Kolor;
import com.mycompany.szachy.pole.Pole;
import com.mycompany.szachy.pole.Ruch;
import com.mycompany.szachy.pole.Szachownica;
import com.mycompany.szachy.pole.SzachownicaNarzędzia;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adamk
 */
public class Król extends Pionek
{
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9,-8,-7,-1,1,7,8,9};

    public Król(int pionekWspół, Kolor pionekKolor)
    {
        super(TypPionka.KRÓL,pionekWspół, pionekKolor, true);
    }
    public Król(final int pionekWspół, final Kolor pionekKolor, final boolean pierwszyRuch)
    {
        super(TypPionka.KRÓL,pionekWspół, pionekKolor, pierwszyRuch);
    }


    @Override
    public List<Ruch> calculateLegalMoves(Szachownica szachownica) 
    {
       final List<Ruch> legalneRuchy = new ArrayList<>();
       
       for(final int obecnyRuch: CANDIDATE_MOVE_COORDINATES) //iterujemy po każdym wektorze (-9, -7, 7, 9)
       {
           int obecnyPionekWspół = this.pionekWspół; 
           
           
               if(pierwszaKolumna(this.pionekWspół, obecnyRuch) ||
                       ósmaKolumna(this.pionekWspół, obecnyRuch))
               {
                   continue;
               }
               
               obecnyPionekWspół += obecnyRuch;
               
               if(SzachownicaNarzędzia.isValidTileCoordinate(obecnyPionekWspół))
               {
                   final Pole docelowePole = szachownica.getPole(obecnyPionekWspół);
               
                    if(!docelowePole.PoleInfo()) //jeśli pole nie jest zajęte
                    {
                       legalneRuchy.add(new Ruch.GłównyRuch(szachownica, this, obecnyPionekWspół)); //this- obecny skoczek
                    }
                    else
                    {
                        final Pionek pionekWspół = docelowePole.getPionek();
                        final Kolor pionekKolor = pionekWspół.getpionekKolor();

                        if(this.pionekKolor != pionekKolor) //najechanie na pionek przzeciwnika
                        {
                           legalneRuchy.add(new Ruch.AtakującyRuch(szachownica, this, obecnyPionekWspół,pionekWspół )); //atakujący ruch
                        }            
                    }
               }                
    }
         return ImmutableList.copyOf(legalneRuchy);
    } 
    private static boolean pierwszaKolumna(final int obecnaPozycja, final int warunekRuchu ) //kiedy pionek znmajduje się na pierwszej kolumnie
    {
        return SzachownicaNarzędzia.FIRST_COLUMN[obecnaPozycja] && (warunekRuchu == -9 || warunekRuchu == -1 || warunekRuchu == 7);
    }
    
    private static boolean ósmaKolumna(final int obecnaPozycja, final int warunekRuchu ) //kiedy pionek znmajduje się na pierwszej kolumnie
    {
        return SzachownicaNarzędzia.EIGHTH_COLUMN[obecnaPozycja] && (warunekRuchu == -7 || warunekRuchu == 1 || warunekRuchu == 9);
    } 
    
    @Override 
    public String toString()
    {
        return TypPionka.KRÓL.toString();
    }
    
    @Override
    public Król ruchPionka(final Ruch ruch) 
    {
     return new Król( ruch.getCel(),ruch.getRuszonyPionek().getpionekKolor());
    }
}
