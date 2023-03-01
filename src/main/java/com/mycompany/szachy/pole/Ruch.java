/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.szachy.pole;

import com.mycompany.szachy.pionek.Pion;
import com.mycompany.szachy.pionek.Pionek;
import com.mycompany.szachy.pionek.Wieża;
import com.mycompany.szachy.pole.Szachownica.Builder;

/**
 *
 * @author adamk
 */
public abstract class Ruch 
{
    protected final Szachownica szachownica;
    protected final Pionek ruszonyPionek;
    protected final int cel;
    protected final boolean pierwszyRuch;
    
    public static final Ruch NULL_RUCH = new NullRuch();
         
     
     
    private Ruch(final Szachownica szachownica, final Pionek ruszonyPionek, final int cel)
    {
            this.szachownica = szachownica;
            this.ruszonyPionek= ruszonyPionek;
            this.cel = cel; //cel ruchu 
            this.pierwszyRuch=ruszonyPionek.pierwszyRuch();
    }
    
    public int getCel()
    {
         return this.cel;
    }
    
    public Pionek getRuszonyPionek()
    {
        return this.ruszonyPionek;
    }
    
    public abstract Szachownica wykonaj();

    private int getObecneWspół() 
    {
       return this.getRuszonyPionek().getPionekWspół();
    }
    
    private Ruch(final Szachownica szachownica, final int cel)
    {
        this.szachownica=szachownica;
        this.cel=cel;
        this.ruszonyPionek=null;
        this.pierwszyRuch=false;
    } //wygoda refaktoryzacji ruchu null
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int wynik = 1;
        
        wynik = prime*wynik+this.cel;
        wynik = prime*wynik+this.ruszonyPionek.hashCode();
        wynik = prime*wynik+this.ruszonyPionek.getPionekWspół();//decyzja czy to pierwszy ruch
        return wynik;
    }
    
    @Override
    public boolean equals(final Object inny)
    {
        if(this == inny)
        {
            return true;
        }
        if(!(inny instanceof Ruch))
        {
            return false;
        }
        final Ruch innyRuch = (Ruch) inny;
        return getObecneWspół() == innyRuch.getObecneWspół() &&
               getCel() == innyRuch.getCel() &&
               getRuszonyPionek().equals(innyRuch.getRuszonyPionek());
    }
    
    public static final class GłównyRuch extends Ruch
    {
        
        public GłównyRuch(Szachownica szachownica, Pionek pionek, int cel)
        {
            super(szachownica, pionek, cel);
        }

        @Override
        public boolean equals(final Object inny)
        {
            return this == inny || inny instanceof GłównyRuch && super.equals(inny);
        }
        
        @Override
        public String toString()
        {
            return ruszonyPionek.getTypPionka().toString() + SzachownicaNarzędzia.getCel(this.cel);
        }
        
        
        @Override //tworzenie nowej szachownicy (nie zmienia poprzedniej)
        public Szachownica wykonaj() 
        {
           final Builder builder = new Builder();
           
           for(final Pionek pionek : this.szachownica.obecnyGracz().getPionki())
           {
               if(!this.ruszonyPionek.equals(pionek))
               {
                   builder.setPionek(pionek);//if ponieważ jest runda gracza
               } 
           } //przechodzi przez wszystkie pionki, ustawia nieruszone pionki na tych samych miejscach
           for(final Pionek pionek : this.szachownica.obecnyGracz().getPrzeciwnik().getPionki())
           {
               builder.setPionek(pionek);
           } //również ustawia nieruszone pionki przeciwnika na tych samych miejscach
           
           builder.setPionek(this.ruszonyPionek.ruchPionka(this)); //ustawienie ruszonego pionka na nowe pole
           builder.setRuchMaker(this.szachownica.obecnyGracz().getKolor());
           
           return builder.build();  
           
        }
        //wykonanie ruchu tworzy nową szachownicę   
    }
    
     public static class AtakującyRuch extends Ruch
     {
            final Pionek atakowanyPionek;
            
            public AtakującyRuch(final Szachownica szachownica, final Pionek ruszonyPionek, final int cel, final Pionek atakowanyPionek) 
            {
                 super(szachownica, ruszonyPionek, cel);
                 this.atakowanyPionek = atakowanyPionek;
            }

            @Override
            public Szachownica wykonaj() 
            {
               return null;
            }
            
            @Override
            public boolean isAtak()
            {
                return true;
            }
            
            @Override
            public int hashCode()
            {
                return this.atakowanyPionek.hashCode() + super.hashCode();
            }
            
            @Override
            public boolean equals(final Object inny)
            {
                if(this==inny)
                {
                    return true;
                }
                if(!(inny instanceof AtakującyRuch))
                {
                    return false;
                }
                final AtakującyRuch innyAtakującyRuch = (AtakującyRuch) inny;
                return super.equals(innyAtakującyRuch) && getAtakowanyPionek().equals(innyAtakującyRuch.getAtakowanyPionek());
            }
           
            public Pionek getAtakowanyPionek()
            {
                return this.atakowanyPionek;
            }
     }

     
     public static final class PionRuch extends Ruch
     {
         public PionRuch(final Szachownica szachownica, final Pionek ruszonyPionek, final int cel)
         {
             super(szachownica, ruszonyPionek, cel);
         }

        @Override
        public Szachownica wykonaj()
        {
             final Builder builder = new Builder();
           
           for(final Pionek pionek : this.szachownica.obecnyGracz().getPionki())
           {
               if(!this.ruszonyPionek.equals(pionek))
               {
                   builder.setPionek(pionek);//if ponieważ jest runda gracza
               } 
           } //przechodzi przez wszystkie pionki, ustawia nieruszone pionki na tych samych miejscach
           for(final Pionek pionek : this.szachownica.obecnyGracz().getPrzeciwnik().getPionki())
           {
               builder.setPionek(pionek);
           } //również ustawia nieruszone pionki przeciwnika na tych samych miejscach
           
           builder.setPionek(this.ruszonyPionek.ruchPionka(this)); //ustawienie ruszonego pionka na nowe pole
           builder.setRuchMaker(this.szachownica.obecnyGracz().getKolor());
           
           return builder.build();  
        }
     }

      public static final class PionekAtakującyRuch extends AtakującyRuch                           
      {
          
        public PionekAtakującyRuch(Szachownica szachownica, Pionek ruszonyPionek, int cel, Pionek atakowanyPionek) 
        {
            super(szachownica, ruszonyPionek, cel, atakowanyPionek);
        }
        
        @Override
        public Szachownica wykonaj() {
             final Builder builder = new Builder();
           
           for(final Pionek pionek : this.szachownica.obecnyGracz().getPionki())
           {
               if(!this.ruszonyPionek.equals(pionek))
               {
                   builder.setPionek(pionek);//if ponieważ jest runda gracza
               } 
           } //przechodzi przez wszystkie pionki, ustawia nieruszone pionki na tych samych miejscach
           for(final Pionek pionek : this.szachownica.obecnyGracz().getPrzeciwnik().getPionki())
           {
               builder.setPionek(pionek);
           } //również ustawia nieruszone pionki przeciwnika na tych samych miejscach
           
           builder.setPionek(this.ruszonyPionek.ruchPionka(this)); //ustawienie ruszonego pionka na nowe pole
           builder.setRuchMaker(this.szachownica.obecnyGracz().getKolor());
           
           return builder.build();  
        }
          
      }
      
     //enpassant tuch niepotrzeebny part 24
   
     public static final class PionekSkok extends Ruch
     {

            public PionekSkok(Szachownica szachownica, Pionek ruszonyPionek, int cel)
            {
                super(szachownica, ruszonyPionek, cel);
            }

              @Override
               public Szachownica wykonaj() {
               final Builder builder = new Builder();

               for(final Pionek pionek : this.szachownica.obecnyGracz().getPionki())
               {
                   if(!this.ruszonyPionek.equals(pionek))
                   {
                       builder.setPionek(pionek);//if ponieważ jest runda gracza
                   } 
               } //przechodzi przez wszystkie pionki, ustawia nieruszone pionki na tych samych miejscach
               for(final Pionek pionek : this.szachownica.obecnyGracz().getPrzeciwnik().getPionki())
               {
                   builder.setPionek(pionek);
               } //również ustawia nieruszone pionki przeciwnika na tych samych miejscach
               
               final Pion ruszonyPion = (Pion)this.ruszonyPionek.ruchPionka(this);
               
               builder.setPionek(ruszonyPion);
               builder.setEnPassantPion(ruszonyPion);
               builder.setRuchMaker(this.szachownica.obecnyGracz().getPrzeciwnik().getKolor());
               
               return builder.build();  
            }
       }
      public static final class PionEnPassantAtakującyRuch extends Ruch 
      { //kiedy pion skacze tworzymy nową szachownicę  

        public PionEnPassantAtakującyRuch(Szachownica szachownica, Pionek ruszonyPionek, int cel) 
        {
            super(szachownica, ruszonyPionek, cel);
        }

        @Override
        public Szachownica wykonaj() 
        {
             final Builder builder = new Builder();
           
           for(final Pionek pionek : this.szachownica.obecnyGracz().getPionki())
           {
               if(!this.ruszonyPionek.equals(pionek))
               {
                   builder.setPionek(pionek);//if ponieważ jest runda gracza
               } 
           } //przechodzi przez wszystkie pionki, ustawia nieruszone pionki na tych samych miejscach
           for(final Pionek pionek : this.szachownica.obecnyGracz().getPrzeciwnik().getPionki())
           {
               builder.setPionek(pionek);
           } //również ustawia nieruszone pionki przeciwnika na tych samych miejscach
           
           builder.setPionek(this.ruszonyPionek.ruchPionka(this)); //ustawienie ruszonego pionka na nowe pole
           builder.setRuchMaker(this.szachownica.obecnyGracz().getKolor());
           
           return builder.build();  
        }
          
      }
     
     public abstract class Roszada extends Ruch
     {

        public Roszada(Szachownica szachownica, Pionek ruszonyPionek, int cel)
        {
            super(szachownica, ruszonyPionek, cel);
        }

               @Override
               public Szachownica wykonaj() {
               final Builder builder = new Builder();

               for(final Pionek pionek : this.szachownica.obecnyGracz().getPionki())
               {
                   if(!this.ruszonyPionek.equals(pionek))
                   {
                       builder.setPionek(pionek);//if ponieważ jest runda gracza
                   } 
               } //przechodzi przez wszystkie pionki, ustawia nieruszone pionki na tych samych miejscach
               for(final Pionek pionek : this.szachownica.obecnyGracz().getPrzeciwnik().getPionki())
               {
                   builder.setPionek(pionek);
               } //również ustawia nieruszone pionki przeciwnika na tych samych miejscach

               builder.setPionek(this.ruszonyPionek.ruchPionka(this)); //ustawienie ruszonego pionka na nowe pole
               builder.setRuchMaker(this.szachownica.obecnyGracz().getKolor());

               return builder.build();  
            }
         
     }
     
     public static final class KrólRoszadaRuch extends RoszadaRuch
     {
         
        public KrólRoszadaRuch(final Szachownica szachownica, final Pionek ruszonyPionek, final int cel, final Wieża roszadaWieża, final int roszadaWieżaStart, final int roszadaWieżaCel)
        {
            super(szachownica, ruszonyPionek, cel, roszadaWieża, roszadaWieżaStart, roszadaWieżaCel);
        }
        
        @Override
        public String toString()
        {
            return "0-0";
        }
     }
     
     public static class KrólowaRoszadaRuch extends RoszadaRuch
     {
         
        public KrólowaRoszadaRuch(Szachownica szachownica, Pionek ruszonyPionek, int cel, final Wieża roszadaWieża, final int roszadaWieżaStart, final int roszadaWieżaCel)
        {
            super(szachownica, ruszonyPionek, cel, roszadaWieża, roszadaWieżaStart, roszadaWieżaCel);
        }
        
        @Override
        public String toString()
        {
            return "0-0-0";
        }
        
     }
     
     public abstract class RoszadaRuch extends Ruch
     {
        protected final Wieża roszadaWieża;
        protected final int roszadaWieżaStart;
        protected final int roszadaWieżaCel;
         
        public RoszadaRuch(Szachownica szachownica, Pionek ruszonyPionek, int cel, final Wieża roszadaWieża, final int roszadaWieżaStart, final int roszadaWieżaCel)
        {
            super(szachownica, ruszonyPionek, cel);
            this.roszadaWieża = roszadaWieża;
            this.roszadaWieżaStart = roszadaWieżaStart;
            this.roszadaWieżaCel = roszadaWieżaCel;
            
        }
        
        public Pionek getWieża()
        {
            return this.roszadaWieża; 
        }
        
        @Override
        public boolean isRoszada()
        {
            return true;
        }
        
        @Override
        public Szachownica wykonaj()
        {
            final Builder builder = new Builder();
            for(final Pionek pionek : this.szachownica.obecnyGracz().getPionki())
               {
                   if(!this.ruszonyPionek.equals(pionek) && !this.roszadaWieża.equals(pionek))
                   {
                       builder.setPionek(pionek);
                   } 
               } 
               for(final Pionek pionek : this.szachownica.obecnyGracz().getPrzeciwnik().getPionki())
               {
                   builder.setPionek(pionek);
               }
               builder.setPionek(this.ruszonyPionek.ruchPionka(this));
               //
               builder.setPionek(new Wieża(this.roszadaWieżaCel, this.roszadaWieża.getpionekKolor()));
               builder.setRuchMaker(this.szachownica.obecnyGracz().getPrzeciwnik().getKolor());
               return builder.build();
        }       
         
     }
     
     public static class NullRuch extends Ruch
     {

        private NullRuch() 
        {
            super( null,-1);
        }

           @Override
               public Szachownica wykonaj() 
               {
                   throw new RuntimeException("cannot execute the null move!");
               }                  
     }
     
     public static class RuchFabryka
     {
         private RuchFabryka()
         {
             throw new RuntimeException("Not instaantiable!");
         }
         public static Ruch stwórzRuch(final Szachownica szachownica, final int obecneWspół,final int cel)
         {
             for(final Ruch ruch : szachownica.getWszystkieLegalneRuchy())
             {
               if(ruch.getObecneWspół() == obecneWspół && ruch.getCel() == cel)
               {
                   return ruch;        
               }
           
            }
            return NULL_RUCH;
        }
    }
     
     public boolean isAtak()
     {
         return false;
     }
     
     public boolean isRoszada()
     {
         return false;
     } 
     
     public Pionek getAtakującyPionek()
     {
         return null;
     }
         
   
}


