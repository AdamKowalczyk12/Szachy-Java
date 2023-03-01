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
public class Królowa extends Pionek
{
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};
 
    public Królowa(int pionekWspół, Kolor pionekKolor)
    {
        super(TypPionka.KRÓLOWA,pionekWspół, pionekKolor, true);
    }
    public Królowa(final int pionekWspół, final Kolor pionekKolor, final boolean pierwszyRuch)
    {
        super(TypPionka.KRÓLOWA,pionekWspół, pionekKolor, pierwszyRuch);
    }

    @Override
    public List<Ruch> calculateLegalMoves(Szachownica szachownica) 
    {
       final List<Ruch> legalneRuchy = new ArrayList<>();
       
       for(final int obecnyRuch: CANDIDATE_MOVE_COORDINATES) //iterujemy po każdym wektorze (-9, -7, 7, 9)
       {
           int obecnyPionekWspół = this.pionekWspół; 
           
           while(SzachownicaNarzędzia.isValidTileCoordinate(obecnyPionekWspół))
           {
               if(pierwszaKolumna(obecnyPionekWspół, obecnyRuch) ||
                       ósmaKolumna(obecnyPionekWspół, obecnyRuch))
               {
                   break;
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
                        break; //jeśli pole nie jest zajęte to możemy poruszać siędalej na ukos 
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
        return TypPionka.KRÓLOWA.toString();
    }
    
    @Override
    public Królowa ruchPionka(final Ruch ruch) 
    {
     return new Królowa( ruch.getCel(),ruch.getRuszonyPionek().getpionekKolor());
    }
}
