package com.mycompany.szachy.pole;

import com.google.common.collect.ImmutableMap;
import com.mycompany.szachy.pionek.Pionek;
import java.util.HashMap;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author adamk
 */
public abstract class Pole 
{   
  protected final int poleWspół; //używana tylko przez podklasy, może być ustawione tylko raz
  
  private static final Map<Integer, PustePole> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles(); //pojemnik
            private static  Map<Integer, PustePole> createAllPossibleEmptyTiles()
              {
                 final Map<Integer, PustePole> PustePoleMap = new HashMap<>();
                  for (int i = 0; i < SzachownicaNarzędzia.NUM_TILES; i++) //do 64 - ilosci pól na szachownicy
                  {
                      PustePoleMap.put(i, new PustePole(i));
                  }
                  
                  return ImmutableMap.copyOf(PustePoleMap); //ImmutableMap z guava library, po tym jak stworzymy pustą mapę nie może być ona zmieniona
              }
              
          
  public static Pole stwórzPole(final int poleWspół, final Pionek pionek)
  {
      return pionek != null ? new ZajętePole(poleWspół,pionek) : EMPTY_TILES_CACHE.get(poleWspół); //Integer.getInteger(polewspół)
  }
  private Pole(int poleWspół) //kontruktor klasy Pole
  {
      this.poleWspół=poleWspół;
  }
  
  public int getPoleWspół()
  {
      return this.poleWspół;
  }
  
  public abstract boolean PoleInfo(); //informacja czy pole jest zajęte
  
  public abstract Pionek getPionek();  //zwraca obecny na polu pionek
   
  public static final class PustePole extends Pole //dziedziczenie po klasie Pole
  {
      PustePole(final int współ)
      {
          super(współ);  //przywołanie konstrukotra superklasy
      }
      
      @Override //nadpisanie metod klasy Pole 
      public boolean PoleInfo()
      {
          return false;
      }
      @Override 
      public Pionek getPionek()
      {
          return null;
      }
      
      @Override
      public String toString()
      {
          return "_"; //puste pole
      }
  }
  
  public static final class ZajętePole extends Pole
  {
      private final Pionek danyPionek;
      
      ZajętePole(int poleWspół, final Pionek danyPionek)
      {
          super(poleWspół);
          
          this.danyPionek= danyPionek;
                  
      }
      
      @Override
      public boolean PoleInfo()
      {
          return true;
      }
      
      @Override
      public Pionek getPionek()
      {
          return this.danyPionek;
      }
      
      @Override
      public String toString()
      { //czarne figury będą małymi litarami a białe wielkimi
          return getPionek().getpionekKolor().isCzarny() ?  getPionek().toString().toLowerCase() :  getPionek().toString(); //zajęte pole
      }
  }
  
}
