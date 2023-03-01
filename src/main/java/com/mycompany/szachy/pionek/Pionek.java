package com.mycompany.szachy.pionek;

import com.mycompany.szachy.Kolor;
import com.mycompany.szachy.pole.Ruch;
import com.mycompany.szachy.pole.Szachownica;
import java.util.Collection;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author adamk
 */
public abstract class Pionek 
{
    protected final TypPionka typPionka;
    
    protected final int pionekWspół;
    
    protected final Kolor pionekKolor;
    
    protected final boolean pierwszyRuch;
    
    private final int cachedHashCode;
    
    Pionek(final TypPionka typPionka, final int pionekWspół, final Kolor pionekKolor, final boolean pierwszyRuch)
    {
        this.typPionka=typPionka;
        this.pionekKolor = pionekKolor;
        this.pionekWspół = pionekWspół;
        this.pierwszyRuch = pierwszyRuch;
        this.cachedHashCode = computedHashCode();
    }
    
    @Override
    public boolean equals(final Object inny)
    {
        if(this==inny)
        {
            return true;
        }
        if(!(inny instanceof Pionek))
        {
            return false;
        }
        final Pionek innyPionek = (Pionek) inny;
        return pionekWspół == innyPionek.getPionekWspół() && typPionka == innyPionek.getTypPionka() &&
               pionekKolor == innyPionek.getpionekKolor() && pierwszyRuch == innyPionek.pierwszyRuch();           
    }
    
    private int computedHashCode() 
    {
      int wynik = typPionka.hashCode();
      wynik = 31 * wynik + pionekKolor.hashCode();
      wynik = 31 * wynik + pionekWspół;
      wynik = 31 * wynik + (pierwszyRuch ? 1 : 0);
      return wynik;
    }
    //dzięki temu że nasz obiekt jest niezmienny nie musimy obliczać cały czas tylko swtorzyć nowe pole człon ka
    
    @Override
    public int hashCode()
    {
      return this.cachedHashCode;
    }
    
    public TypPionka getTypPionka()
    {
        return this.typPionka;
    }
    
    public int getPionekWspół()
    {
        return this.pionekWspół;
    }
    
    public Kolor getpionekKolor()
    {
        return this.pionekKolor;
    }
    
    public boolean pierwszyRuch()
    {
        return this.pierwszyRuch;
    }
    
    //zwraca kolekcję ruchów
    public abstract List<Ruch> calculateLegalMoves(final Szachownica szachownica); //można zamienić na Collection

    public abstract Pionek ruchPionka(Ruch ruch); //zwrac anowy pionek z nową zaktualiozowaną pozycją
    
    public enum TypPionka
    {
        PION("P") {
            @Override
            public boolean isKing() 
            {
               return false;
            }

            @Override
            public boolean isWieża() 
            {
                return false;
            }
        }, 
        SKOCZEK("S") {
            @Override
            public boolean isKing() 
            {
                return false;
            }
            @Override
            public boolean isWieża() 
            {
                return false;
            }
        },
        GONIEC("G") {
            @Override
            public boolean isKing() 
            {
                return false;
            }
            @Override
            public boolean isWieża() 
            {
                return false;
            }
        },
        WIEŻA("W") {
            @Override
            public boolean isKing()
            {
               return false;
            }
            @Override
            public boolean isWieża() 
            {
                return true;
            }
        },
        KRÓLOWA("Q") {
            @Override
            public boolean isKing() 
            {
                return false;
            }
            @Override
            public boolean isWieża() 
            {
                return false;
            }
        },
        KRÓL("K") {
            @Override
            public boolean isKing() 
            {
                return true;
            }
            @Override
            public boolean isWieża() 
            {
                return false;
            }
        };
        
        private final String nazwaPionka;
        
        TypPionka(final String nazwaPionka)
        {
            this.nazwaPionka = nazwaPionka;
        }
        
        @Override
        public String toString()
        {
            return this.nazwaPionka;
        }
        
        public abstract boolean isKing();

        public abstract boolean isWieża();

    }

}
