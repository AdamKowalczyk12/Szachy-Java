/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.szachy.Gracz;

/**
 *
 * @author adamk
 */
public enum StatusRuchu
{
    DONE 
    {
        @Override
         public boolean isWykonany()
        {
           return true;
        }
    },
    
    NIELEGALNE_RUCHY
    {
        @Override
        public boolean isWykonany()
        {
            return false;
        }
        
    },
    
    POZOSTAWIA_GRACZA_W_SZACHU
    {
         @Override
        public boolean isWykonany()
        {
           return false;
        }
    };
    public abstract boolean isWykonany();
}
