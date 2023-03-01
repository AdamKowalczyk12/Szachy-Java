/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.szachy.pole;


import com.mycompany.szachy.Gracz.Gracz;
import com.mycompany.szachy.Gracz.CzarnyGracz;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mycompany.szachy.Gracz.BiałyGracz;
import com.mycompany.szachy.Kolor;
import com.mycompany.szachy.pionek.Goniec;
import com.mycompany.szachy.pionek.Król;
import com.mycompany.szachy.pionek.Królowa;
import com.mycompany.szachy.pionek.Pion;
import com.mycompany.szachy.pionek.Pionek;
import com.mycompany.szachy.pionek.Skoczek;
import com.mycompany.szachy.pionek.Wieża;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

/**
 *
 * @author adamk
 */
public class Szachownica
{
    
   private final List<Pole> Plansza;
   private final Collection<Pionek> białePionki;
   private final Collection<Pionek> czarnePionki;
   
   private final BiałyGracz białyGracz;
   private final CzarnyGracz czarnyGracz;
   private final Gracz obencyGracz;
  
   private Szachownica(Builder builder)
   {
       this.Plansza= createPlansza(builder);
       this.białePionki=policzPionki(this.Plansza, Kolor.Biały);
       this.czarnePionki=policzPionki(this.Plansza, Kolor.Czarny);
       
       final Collection<Ruch> białyRuch=policzRuchy(this.białePionki);
       final Collection<Ruch> czarnyRuch=policzRuchy(this.czarnePionki);
       //białyRuch i czarnyRuch zostaną użyte przy opisaniu graczy
       
       this.białyGracz = new BiałyGracz(this, białyRuch, czarnyRuch); //potrzebne pobieranie ruchów obu kolorów aby dla każdego gracza zdefiniować legale ruchy
       this.czarnyGracz = new CzarnyGracz(this, białyRuch, czarnyRuch); 
       this.obencyGracz=builder.następnyRuch.wybierzGracza(this.białyGracz, this.czarnyGracz);
   }
   
   @Override 
   public String toString()
   {
       final StringBuilder builder = new StringBuilder();
       for(int i=0; i< SzachownicaNarzędzia.NUM_TILES; i++)
       {
           final String tekst = this.Plansza.get(i).toString();
           
           builder.append(String.format("%3s", tekst));
           if((i+1)% SzachownicaNarzędzia.NUM_TILES_PER_ROW == 0)
           {
              builder.append("\n"); //wypisanie szachownicy za pomocą znaków ASCII
           }
       }
       return builder.toString();
   }
   
   public Gracz obecnyGracz()
   {
       return this.obencyGracz;
   }
   
   public Gracz białyGracz()
   {
       return this.białyGracz;
   }
   
    public Gracz czarnyGracz()
   {
       return this.czarnyGracz;
   }
    
   public Collection getCzarnePionki()
   {
       return this.czarnePionki;
   }
   
   public Collection getBiałePionki()
   {
       return this.białePionki;
   }
   
   private static Collection<Pionek> policzPionki(final List<Pole> Plansza, final Kolor kolor)
   {
       final List<Pionek> aktywnyPionek = new ArrayList<>();
       
       for(final Pole pole: Plansza)
       {
          if(pole.PoleInfo())
          {
              final Pionek pionek = pole.getPionek();
              if(pionek.getpionekKolor() == kolor)
              {
                  aktywnyPionek.add(pionek);
              }
          }
       }     
       return ImmutableList.copyOf(aktywnyPionek);
   }
   
   public Pole getPole(final int poleWspół)
   {
       return Plansza.get(poleWspół);
   }
   
   private static List<Pole> createPlansza(final Builder builder)
   {
       final Pole[] pola = new Pole[SzachownicaNarzędzia.NUM_TILES]; //tworzy tablicę 64 pól
       for (int i = 0; i < SzachownicaNarzędzia.NUM_TILES; i++)
       {
          pola[i] = Pole.stwórzPole(i, builder.szachownicaConfig.get(i)); //get pionek na danym polu i stwórz pole
       }
       
       return ImmutableList.copyOf(Arrays.asList(pola));
         
   }
   
   public static Szachownica stwórzPlanszę()
   {
       
      final Builder builder = new Builder();
      builder.setPionek(new Wieża(0, Kolor.Czarny));
      builder.setPionek(new Skoczek(1, Kolor.Czarny));
      builder.setPionek(new Goniec(2, Kolor.Czarny));
      builder.setPionek(new Królowa(3, Kolor.Czarny));
      builder.setPionek(new Król(4, Kolor.Czarny));
      builder.setPionek(new Goniec(5, Kolor.Czarny));
      builder.setPionek(new Skoczek(6, Kolor.Czarny));
      builder.setPionek(new Wieża(7, Kolor.Czarny));
      builder.setPionek(new Pion(8, Kolor.Czarny));
      builder.setPionek(new Pion(9, Kolor.Czarny));
      builder.setPionek(new Pion(10, Kolor.Czarny));
      builder.setPionek(new Pion(11, Kolor.Czarny));
      builder.setPionek(new Pion(12, Kolor.Czarny));
      builder.setPionek(new Pion(13, Kolor.Czarny));
      builder.setPionek(new Pion(14, Kolor.Czarny));
      builder.setPionek(new Pion(15, Kolor.Czarny));
      
      builder.setPionek(new Pion(48, Kolor.Biały));
      builder.setPionek(new Pion(49, Kolor.Biały));
      builder.setPionek(new Pion(50, Kolor.Biały));
      builder.setPionek(new Pion(51, Kolor.Biały));
      builder.setPionek(new Pion(52, Kolor.Biały));
      builder.setPionek(new Pion(53, Kolor.Biały));
      builder.setPionek(new Pion(54, Kolor.Biały));
      builder.setPionek(new Pion(55, Kolor.Biały));
      builder.setPionek(new Wieża(56, Kolor.Biały));
      builder.setPionek(new Skoczek(57, Kolor.Biały));
      builder.setPionek(new Goniec(58, Kolor.Biały));
      builder.setPionek(new Królowa(59, Kolor.Biały));
      builder.setPionek(new Król(60, Kolor.Biały));
      builder.setPionek(new Goniec(61, Kolor.Biały));
      builder.setPionek(new Skoczek(62, Kolor.Biały));
      builder.setPionek(new Wieża(63, Kolor.Biały));
      
      builder.setRuchMaker(Kolor.Biały);
      
      return builder.build();
  
   }

    private Collection<Ruch> policzRuchy(final Collection<Pionek> pionki) //obliczyć ruchy dla danego koloru
    {
      List<Ruch> legalneRuchy = new ArrayList<>();
      
      for(Pionek pionek : pionki)
      {
          legalneRuchy.addAll(pionek.calculateLegalMoves(this)); //dodanie wszystkich ruchów do legalneRuchy
      }
      return ImmutableList.copyOf(legalneRuchy);
    }

    public Iterable<Ruch> getWszystkieLegalneRuchy() 
    {
        return Iterables.unmodifiableIterable(Iterables.concat(this.białyGracz.getLegalneRuchy(), this.czarnyGracz.getLegalneRuchy()));
    }
                        
   
   public static class Builder
   {
       Map<Integer, Pionek> szachownicaConfig;
               
       Kolor następnyRuch;  
        private Pion enPassantPion;
       
       public Builder()
       {
           this.szachownicaConfig = new HashMap<>();
       }
       public Builder setPionek(final Pionek pionek)
       {
           this.szachownicaConfig.put(pionek.getPionekWspół(), pionek);
           return this;
       }
       
       public Builder setRuchMaker(final Kolor kolor)
       {
           this.następnyRuch = następnyRuch;
           return this;
       }
       
       public Szachownica build() //budowanie stałej szachownicy
       {
           return new Szachownica(this);
       }

        void setEnPassantPion(Pion enPassantPion)
        {
          this.enPassantPion=enPassantPion;
        }
   }
}
