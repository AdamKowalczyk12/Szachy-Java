/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.szachy.Gracz;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mycompany.szachy.Kolor;
import com.mycompany.szachy.pionek.Król;
import com.mycompany.szachy.pionek.Pionek;
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
public abstract class Gracz 
{

    protected static Collection<Ruch> obliczAtaki(int pionekWspół, Collection<Ruch> ruchy)
    {
       final List<Ruch> atakująceRuchy = new ArrayList<>();
       for(final Ruch ruch : ruchy)
       {
           if(pionekWspół == ruch.getCel())
           {
              atakująceRuchy.add(ruch);
           }
       }
       return ImmutableList.copyOf(atakująceRuchy);
    } //podajemy lokacjękróla i lokację ruchu przecinika, jeśli przeciwnika ruch najeżsdza na króla to atakuje go, 
    
    protected final Szachownica szachownica; //szachownica na któej grają gracze
    protected final Król król; //informacja o pionku królu
    protected final Collection<Ruch> legalneRuchy; 
    private final boolean czySzach;
    
       
    Gracz(final Szachownica szachownica, final Collection<Ruch>legalneRuchy, final Collection<Ruch> przeciwnyRuch)
    {
        this.szachownica= szachownica;
        this.król= StwórzKróla();
        this.legalneRuchy= ImmutableList.copyOf(Iterables.concat(legalneRuchy,obliczKrólRoszada(legalneRuchy, przeciwnyRuch)));
        //aby obliczyć ruchy roszady musisz wiedzieć jakie są ruchy przecniwka, jeśli są jakieś ataki pomiędzy polami roszadowymi nie można wykonać roszady
        this.czySzach= !Gracz.obliczAtaki(this.król.getPionekWspół(),przeciwnyRuch).isEmpty(); 
        //czy ruch przecwinika atakuje pozycję króla i pobiera ataki, jeśli ruchy ataków się nie skończyły to obecny gracz ma Szacha
    }
    
    public Król getKról()
    {
        return this.król;
    }
    
    public Collection<Ruch> getLegalneRuchy()
    {
       return this.legalneRuchy; 
    }
 

    private Król StwórzKróla() //zapewnienie że jest król
    {
       for(final Pionek pionek : getPionki())
       {
           if(pionek.getTypPionka().isKing())
           {
               return(Król) pionek;
           }
       }
        throw new RuntimeException("Nie ma króla");
    }
    
    
    public abstract Collection<Pionek> getPionki();
    public abstract Kolor getKolor();
    public abstract Gracz getPrzeciwnik();
    
    public boolean czyLegalny(final Ruch ruch)
    {
        return this.legalneRuchy.contains((ruch)); //sprawdzenie czy ruch jest zawarty w legalnych ruchach gracza
    }
    
    public boolean czySzach()
    {
        return this.czySzach;
    } 
    
    public boolean czySzachMat()
    {
        return this.czySzach && !ruchyUcieczki();
    }
    
    public boolean czyPat()
    {
        return !this.czySzach && !ruchyUcieczki();
    }
    
    public boolean czyRoszada()
    {
        return false;
    }
    
    public Szachownica wykonaj() 
    {
       return null;
    }
    
    //kiedy robimy ruch zwracamy WykonanyRuch jeśłi ruch jest prawidłowy
    
    public WykonanyRuch wykonajRuch(final Ruch ruch)
    {
       if(!czyLegalny(ruch))
       {
           return new WykonanyRuch(this.szachownica,ruch, StatusRuchu.NIELEGALNE_RUCHY);
       }
       //rch nie jest nielegalny
       final Szachownica szachownica = ruch.wykonaj();
       
        final Collection<Ruch> królAtaki = Gracz.obliczAtaki(szachownica.obecnyGracz().getPrzeciwnik().getKról().getPionekWspół(),
                szachownica.obecnyGracz().getLegalneRuchy());
       
        //jeśłi atak jest nielegalny, to kolejny ruch nie przekazuje nas do kolejnej szachownicy tylko do tej samej i status ruchu staje się nielegalny
        //następnie używamy ruchu aby polimofricznie wykonać ruch i zwócić nam nową planszęna którą przechodzimy i pytamy czy są jakieś ataki na króla obecnego gracza
        //jeśłi są to nie możemy wykonać ruchu który odsłoni króla, jeśli ataki nie są puste to zwróć planszę przejściową z nowym ruchem do wykonania
        //wywołujemy metodę pomocniczą obliczającą ataki na polu i podajemy miejsce króla przeciwnika i dostępne ruchy do wykonania 
        //np. dowiadujemy gdzie jest obcnie biały król i dostajemy legalne ruchy czarnych i dowiadujemy się czy zaatakują białęgo króla
           
        if(!królAtaki.isEmpty())
        {
            return new WykonanyRuch(this.szachownica, ruch, StatusRuchu.POZOSTAWIA_GRACZA_W_SZACHU);
        }
        
       return new WykonanyRuch(szachownica,ruch,StatusRuchu.DONE);
    }
    
    protected boolean ruchyUcieczki()
    {
        for(final Ruch ruch : this.legalneRuchy)
        {
            final WykonanyRuch wykonanie = wykonajRuch(ruch);
            if(wykonanie.getStatusRuchu().isWykonany())
            {
                return true;
            }
        }
     
        return false;
    }
    
    protected abstract Collection<Ruch> obliczKrólRoszada(Collection<Ruch> prawaGracza, Collection<Ruch> prawaPrzeciwnika);

    
    
}
