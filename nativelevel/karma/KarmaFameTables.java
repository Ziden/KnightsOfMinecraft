/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.karma;

import java.util.HashMap;
import java.util.UUID;
import nativelevel.utils.GeneralUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author Ziden
 *
 */
public class KarmaFameTables {

    public static HashMap<UUID, String> cacheTitulos = new HashMap<UUID, String>();
    
    /*
    
     */
    private static String[][] karmaFameTable = new String[][]{
        /*
         + Fame ---->
         + Karma 
         ^
         |
         |
         */
        {"Digno", "Honrado", "Admiravel", "Glorioso", "Glorioso"},
        {"Bondoso", "Estimado", "Extraordinario", "Grandioso", "Grandioso"},
        {"Confiavel", "Respeitavel", "Veneravel", "Eminente", "Eminente"},
        {"Gentil", "Qualificado", "Nobre", "Ilustre", "Ilustre"},
        {"Correto", "Honesto", "Justo", "Probo", "Probo"},
        
        {"", "Conhecido", "Renomado", "Famoso", "Iconico"},
        
        {"", "Estranho", "Proeminente", "Renomado", "Heroi"},
        {"Rude", "Desonesto", "Estupido", "Infame", "Infame"},
        {"Ruim", "Malicioso", "Ignobil", "Sinistro", "Sinistro"},
        {"Funesto", "Perverso", "Vil", "Maligno", "Maligno"},
        {"Trevoso", "Abominavel", "Depravado", "Cruel", "Terrivel"}, // a tag mais má q tem rs
        //{"Funesto", "Fascinora", "Abominavel", "Terrivel", "Terrivel"}
    };

    public static String getTitle(int karma, int fame) {
        if (karma == 0) {
            karma = 1;
        }
        if (fame == 0) {
            fame = 1;
        }
        int fameColumn = 0;
        int karmaLine = 5;
        karmaLine -= Math.round(karma/6400);
        
        fameColumn += Math.round(fame/8000);

        return karmaFameTable[karmaLine][fameColumn];
    }

    public static void main(String [] args) {
        int karma = 32000;
        int fama = 32000;
        
        System.out.println(getTitle(karma, fama));
    }
 
}
