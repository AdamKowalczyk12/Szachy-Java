/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.szachy;

import com.mycompany.szachy.Gracz.BiałyGracz;
import com.mycompany.szachy.Gracz.CzarnyGracz;
import com.mycompany.szachy.Gracz.Gracz;

/**
 *
 * @author adamk
 */
public enum Kolor 
{
    Biały 
    {
        @Override
        public int getKierunek() 
        {
           return -1;
        }

        @Override
        public boolean isBiały() 
        {
           return true;     
        }

        @Override
        public boolean isCzarny() 
        {
           return false; 
        }

        @Override
        public Gracz wybierzGracza(final BiałyGracz białyGracz, final CzarnyGracz czarnyGracz) 
        {
           return białyGracz;
        }
    },
    Czarny
    {
        @Override
        public int getKierunek() 
        {
           return 1;
        }

        @Override
        public boolean isBiały() 
        {
           return true; 
        }

        @Override
        public boolean isCzarny() 
        {
            return false;
        }

        @Override
        public Gracz wybierzGracza(final BiałyGracz białyGracz, final CzarnyGracz czarnyGracz) 
        {
            return czarnyGracz;
        }
    };
    
    public abstract int getKierunek();
    
    public abstract boolean isBiały();
    
    public abstract boolean isCzarny();

    public abstract Gracz wybierzGracza(final BiałyGracz białyGracz, final CzarnyGracz czarnyGracz);
}
