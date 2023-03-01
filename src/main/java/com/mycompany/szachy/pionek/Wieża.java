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
public class Wieża extends Pionek
{
     private final static int[] CANDIDATE_MOVE_COORDINATES = {-8, -1, 1, 8};
    
    public Wieża(final int pionekWspół, Kolor pionekKolor) 
    {
        super(TypPionka.WIEŻA,pionekWspół, pionekKolor, true);
    }
    public Wieża(final int pionekWspół, final Kolor pionekKolor, final boolean pierwszyRuch)
    {
        super(TypPionka.WIEŻA,pionekWspół, pionekKolor, pierwszyRuch);
    }

    @Override
    public List<Ruch> calculateLegalMoves(Szachownica szachownica)
    {
       int obecnyPionekWspół;
       
        final List<Ruch> legalneRuchy = new ArrayList<>();
        
        for(final int obecnyRuch: CANDIDATE_MOVE_COORDINATES)
        {
           obecnyPionekWspół = this.pionekWspół + obecnyRuch; 
           
           if(SzachownicaNarzędzia.isValidTileCoordinate(obecnyPionekWspół)) //jeśl nie wyjdziemy poza granicę //true
           {
               if(pierwszaKolumna(this.pionekWspół, obecnyRuch)
                       || ósmaKolumna(this.pionekWspół, obecnyRuch))
               {
                   continue; 
               }
               
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
            return SzachownicaNarzędzia.FIRST_COLUMN[obecnaPozycja] && (warunekRuchu == -1);

        }

         private static boolean ósmaKolumna(final int obecnaPozycja, final int warunekRuchu)
        {
             return SzachownicaNarzędzia.EIGHTH_COLUMN[obecnaPozycja] && (warunekRuchu == 1); 
        }
         
         @Override 
         public String toString()
         {
             return TypPionka.WIEŻA.toString();
         }
         
        @Override
        public Wieża ruchPionka(final Ruch ruch) 
        {
             return new Wieża(  ruch.getCel(),ruch.getRuszonyPionek().getpionekKolor());
        }
    }
    


