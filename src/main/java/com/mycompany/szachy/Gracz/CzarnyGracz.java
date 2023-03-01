/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.szachy.Gracz;

import com.google.common.collect.ImmutableList;
import com.mycompany.szachy.Gracz.Gracz;
import com.mycompany.szachy.Kolor;
import com.mycompany.szachy.pionek.Pionek;
import com.mycompany.szachy.pionek.Wieża;
import com.mycompany.szachy.pole.Pole;
import com.mycompany.szachy.pole.Ruch;
import com.mycompany.szachy.pole.Ruch;
import com.mycompany.szachy.pole.Szachownica;
import com.mycompany.szachy.pole.Szachownica;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author adamk
 */
public class CzarnyGracz extends Gracz
{

    public CzarnyGracz(final Szachownica szachownica, final Collection<Ruch> białyRuch, final Collection<Ruch> czarnyRuch) 
    {
        super(szachownica, czarnyRuch, białyRuch); //białyRuch jest przeciwnika
    }

    @Override
    public Collection<Pionek> getPionki() 
    {
      return this.szachownica.getCzarnePionki();
    }

    @Override
    public Kolor getKolor() 
    {
        return Kolor.Czarny;
    }

    @Override
    public Gracz getPrzeciwnik()
    {
       return this.szachownica.białyGracz();
    }

    @Override
    protected Collection<Ruch> obliczKrólRoszada(final Collection<Ruch> prawaGracza,final  Collection<Ruch> prawaPrzeciwnika) 
    {
        final List<Ruch> królRoszada = new ArrayList<>();
        
        if(this.król.pierwszyRuch() && !this.czySzach())
        {
           if(!this.szachownica.getPole(5).PoleInfo() && !this.szachownica.getPole(6).PoleInfo())
           {
               final Pole wieżaPole = this.szachownica.getPole(7);
               
               if(wieżaPole.PoleInfo() && wieżaPole.getPionek().pierwszyRuch())
               {
                   if(Gracz.obliczAtaki(5, prawaPrzeciwnika).isEmpty() 
                       && Gracz.obliczAtaki(6, prawaPrzeciwnika).isEmpty()
                       && wieżaPole.getPionek().getTypPionka().isWieża())    
                   {
                       
                   }
                   królRoszada.add(new Ruch.KrólRoszadaRuch(this.szachownica, this.król, 6, 
                           (Wieża)wieżaPole.getPionek(), wieżaPole.getPoleWspół(), 5));
               }
           }
           
           if(!this.szachownica.getPole(1).PoleInfo() 
                   && !this.szachownica.getPole(2).PoleInfo() 
                   && !this.szachownica.getPole(3).PoleInfo())
           {
                final Pole wieżaPole = this.szachownica.getPole(0);
                if(wieżaPole.PoleInfo() && wieżaPole.getPionek().pierwszyRuch() && Gracz.obliczAtaki(2, prawaPrzeciwnika).isEmpty()
                        && Gracz.obliczAtaki(3, prawaPrzeciwnika).isEmpty()
                        && wieżaPole.getPionek().getTypPionka().isWieża())
                {
                    królRoszada.add(new Ruch.KrólowaRoszadaRuch(this.szachownica,this.król,2,
                            (Wieża)wieżaPole.getPionek(),wieżaPole.getPoleWspół(), 3));
                }
           }
      
        } 
        
        
        //obliczanie roszady białego króla
        return ImmutableList.copyOf(królRoszada);
    }
    
}
