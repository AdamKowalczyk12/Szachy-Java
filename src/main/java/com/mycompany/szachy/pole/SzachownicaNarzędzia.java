/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.szachy.pole;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author adamk
 */
public class SzachownicaNarzędzia 
{    
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);
    
    public static final boolean[] EIGHTH_RANK = initRow(0); //pola które zaczynają rząd
    public static final boolean[] SEVENTH_RANK = initRow(8);
    public static final boolean[] SIXTH_RANK = initRow(16);
    public static final boolean[] FIFTH_RANK = initRow(24);
    public static final boolean[] FOURTH_RANK = initRow(32);
    public static final boolean[] THIRD_RANK = initRow(40);
    public static final boolean[] SECOND_RANK = initRow(48);
    public static final boolean[] FIRST_RANK = initRow(56);
    
//    public static List<String> ALGEBRATIC_NOTATION = initializeAlgebraticNotation();
//    public static final Map<String, Integer> POSITION_TO_COORDINATE=initializePositionToCoordinateMap();
//    
    
    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    static String getCel(int cel) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

//    private static List<String> initializeAlgebraticNotation() {
//        return Collections.unmodifiableList(Arrays.asList(
//                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
//                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
//                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
//                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
//                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
//                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
//                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
//                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"));
//    }

//    private static Map<String, Integer> initializePositionToCoordinateMap()
//    {
//       final Map<String, Integer> positionToCoordinate = new HashMap<>();
//        for (int i = 0; i < NUM_TILES; i++) {
//            positionToCoordinate.put(ALGEBRETIC_NOTATION(i),i);
//        }
//        return Collections.unmodifiableMap(positionToCoordinate);
//    }
    
    private SzachownicaNarzędzia()
    {
        throw new RuntimeException("You cannot instantiate me");
    }
    
    private static boolean[] initRow(int numerWiersza)
    {
        final boolean[] wiersz = new boolean[NUM_TILES];  
        {
           do
           {
               wiersz[numerWiersza] = true;
               numerWiersza++;

           } while(numerWiersza % NUM_TILES_PER_ROW !=0); 
        }
        return wiersz;
    }
    private static boolean[] initColumn(int numerKolumny) 
    {
        final boolean[] kolumna = new boolean[64]; //przesuwa numer pola o 8 aby wejść do kolejnego rzędu kolumny  
        do
        {
            kolumna[numerKolumny] = true;
            numerKolumny += NUM_TILES_PER_ROW; //+= 8
            
        } while (numerKolumny < NUM_TILES);//< 64
        return kolumna;
    } 
     
    public static boolean isValidTileCoordinate(final int współ)
    {
        return współ >= 0 && współ < 64; //numer pola mieści się w przedziale szachwonicy
    }
    
//      public static int getCel(final String pozycja)
//     {
//         return POSITION_TO_COORDINATE.get(pozycja);
//     }
//     
//     public static String getWspół(final int współ)
//     {
//         return ALGEBRATIC_NOTATION.get[współ];
//     }

}
