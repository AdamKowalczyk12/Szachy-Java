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
import com.mycompany.szachy.pole.Ruch.KrólowaRoszadaRuch;
import com.mycompany.szachy.pole.Szachownica;
import com.mycompany.szachy.pole.Szachownica;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author adamk
 */
public class BiałyGracz extends Gracz
{

   public BiałyGracz(final Szachownica szachownica, final Collection<Ruch> białyRuch, final Collection<Ruch> czarnyRuch) 
    {
      super(szachownica, białyRuch, czarnyRuch); 
    }
    @Override
    public Collection<Pionek> getPionki() 
    {
      return this.szachownica.getBiałePionki();
    }

    @Override
    public Kolor getKolor()
    {
       return Kolor.Biały;
    }
    @Override
     public Gracz getPrzeciwnik()
    {
       return this.szachownica.czarnyGracz();
    }

    @Override
    protected Collection<Ruch> obliczKrólRoszada(final Collection<Ruch> prawaGracza, final Collection<Ruch> prawaPrzeciwnika) 
    {
        final List<Ruch> królRoszada = new ArrayList<>();
        
        if(this.król.pierwszyRuch() && !this.czySzach())
        {
           if(!this.szachownica.getPole(61).PoleInfo() && !this.szachownica.getPole(62).PoleInfo())
           {
               final Pole wieżaPole = this.szachownica.getPole(63);
               
               if(wieżaPole.PoleInfo() && wieżaPole.getPionek().pierwszyRuch())
               {
                   if(Gracz.obliczAtaki(61, prawaPrzeciwnika).isEmpty() 
                       && Gracz.obliczAtaki(62, prawaPrzeciwnika).isEmpty()
                       && wieżaPole.getPionek().getTypPionka().isWieża())
                   {
                       
                   }
                   królRoszada.add(new Ruch.KrólRoszadaRuch(this.szachownica, this.król, 62, 
                           (Wieża)wieżaPole.getPionek(), wieżaPole.getPoleWspół(), 61));
               }
           }
           
           if(!this.szachownica.getPole(59).PoleInfo() 
                   && !this.szachownica.getPole(58).PoleInfo() 
                   && !this.szachownica.getPole(57).PoleInfo())
           {
                final Pole wieżaPole = this.szachownica.getPole(56);
                if(wieżaPole.PoleInfo() && wieżaPole.getPionek().pierwszyRuch() && Gracz.obliczAtaki(58, prawaPrzeciwnika).isEmpty()
                        && Gracz.obliczAtaki(59, prawaPrzeciwnika).isEmpty()
                        && wieżaPole.getPionek().getTypPionka().isWieża())
                {
                    królRoszada.add(new KrólowaRoszadaRuch(this.szachownica,this.król,58,
                            (Wieża)wieżaPole.getPionek(),wieżaPole.getPoleWspół(), 59));
                }
           }
      
        } 
        
        
        //obliczanie roszady białego króla
        return ImmutableList.copyOf(królRoszada);
    }
}
