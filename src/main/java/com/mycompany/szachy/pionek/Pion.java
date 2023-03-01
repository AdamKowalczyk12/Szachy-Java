/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.szachy.pionek;

import com.google.common.collect.ImmutableList;
import com.mycompany.szachy.Kolor;
import com.mycompany.szachy.pole.Ruch;
import com.mycompany.szachy.pole.Ruch.GłównyRuch;
import com.mycompany.szachy.pole.Ruch.PionekAtakującyRuch;
import com.mycompany.szachy.pole.Szachownica;
import com.mycompany.szachy.pole.SzachownicaNarzędzia;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adamk
 */
public class Pion extends Pionek
{  
    private final static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9};

    public Pion(final int pionekWspół, final Kolor pionekKolor) 
    {
        super(TypPionka.PION,pionekWspół, pionekKolor, true);
    }
    public Pion(final int pionekWspół, final Kolor pionekKolor, final boolean pierwszyRuch)
    {
        super(TypPionka.PION,pionekWspół, pionekKolor, pierwszyRuch);
    }

    @Override
    public List<Ruch> calculateLegalMoves(final Szachownica szachownica)
    {
       final List<Ruch> legalneRuchy = new ArrayList<>(); 
          
       for(final int obecnyRuch: CANDIDATE_MOVE_COORDINATES)
       {
            final int obecnyPionekWspół = this.pionekWspół + (this.getpionekKolor().getKierunek() * obecnyRuch); //dla białych -8, dla czarnych 8
       
            if(!SzachownicaNarzędzia.isValidTileCoordinate(obecnyPionekWspół ))
            {
              continue;  
            }
            
            if(obecnyRuch == 8 && !szachownica.getPole(obecnyPionekWspół).PoleInfo())
            {    //nieatakujący ruch
                legalneRuchy.add(new GłównyRuch(szachownica, this, obecnyRuch));
            } 
            else if(obecnyRuch == 16 && this.pierwszyRuch() && (SzachownicaNarzędzia.SEVENTH_RANK[this.pionekWspół]) && this.getpionekKolor().isCzarny() || 
                    (SzachownicaNarzędzia.SEVENTH_RANK[this.pionekWspół]) && this.getpionekKolor().isBiały())
            {
               final int skokPionekWspół = this.pionekWspół + (this.pionekKolor.getKierunek() * 8);
               
               if(!szachownica.getPole(skokPionekWspół).PoleInfo() && !szachownica.getPole(obecnyPionekWspół).PoleInfo())
               {
                  legalneRuchy.add(new Ruch.PionekSkok(szachownica, this, obecnyRuch)); 
               }
            } 
            else if (obecnyRuch == 7 && 
                    !((SzachownicaNarzędzia.EIGHTH_COLUMN[this.pionekWspół] && this.pionekKolor.isBiały() 
                    || (SzachownicaNarzędzia.FIRST_COLUMN[this.pionekWspół] && this.pionekKolor.isCzarny()))))
            {
               if(szachownica.getPole(obecnyPionekWspół).PoleInfo())
               {
                  final Pionek pionPozycja = szachownica.getPole(obecnyPionekWspół).getPionek();
                  
                  if(this.pionekKolor != pionPozycja.getpionekKolor())
                  {
                      legalneRuchy.add(new PionekAtakującyRuch(szachownica, this, obecnyRuch, pionPozycja));
                  }
               }
            }
             else if (obecnyRuch == 9 && 
                    !((SzachownicaNarzędzia.EIGHTH_COLUMN[this.pionekWspół] && this.pionekKolor.isBiały() 
                    || (SzachownicaNarzędzia.FIRST_COLUMN[this.pionekWspół] && this.pionekKolor.isCzarny()))))
            {
               if(szachownica.getPole(obecnyPionekWspół).PoleInfo())
               {
                  final Pionek pionPozycja = szachownica.getPole(obecnyPionekWspół).getPionek();
                  
                  if(this.pionekKolor != pionPozycja.getpionekKolor())
                  {
                    legalneRuchy.add(new PionekAtakującyRuch(szachownica, this, obecnyRuch,pionPozycja));
                  }
               }
            }

       }  
     
       return ImmutableList.copyOf(legalneRuchy);
    }
    @Override 
    public String toString()
    {
        return TypPionka.PION.toString();
    }
    
    @Override
    public Pion ruchPionka(final Ruch ruch) 
    {
     return new Pion( ruch.getCel(),ruch.getRuszonyPionek().getpionekKolor());
    }
}
