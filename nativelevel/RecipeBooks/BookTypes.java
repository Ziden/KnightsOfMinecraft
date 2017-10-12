/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.RecipeBooks;

import nativelevel.Lang.L;
import org.bukkit.ChatColor;

/**
 *
 * @author User
 */
public enum BookTypes {

    
    Magia, Alquimia, Ferraria;

    
    public static String getFirstPage(BookTypes type) {
        String name = getRecipeTitle(type);
        String page = "";
        page += ChatColor.RED + L.m("Livro de " + type.name() + "\n\n" + ChatColor.DARK_BLUE + "Encontre " + name + "s e complete o livro !\n");
        if (type == Alquimia) {
            page += ChatColor.DARK_AQUA + L.m("Para criar poções, adicione os ingredientes no caldeirão\n\nDepois aqueça o extrato com o ingrediente correto para fazer a poção !");
        } else if (type == Magia) {
            page += ChatColor.DARK_AQUA + L.m("Use os clicks do mouse com ou sem shift para combinar elementos e criar magias !");
        } else if (type == Ferraria) {
            page += ChatColor.DARK_AQUA + L.m("Coloque um item para aprimorar na bigorna, depois materiais em cima dele e use uma pá para bater");
        }
        return page;
    }
    
    
    public static String getRecipeTitle(BookTypes t) {
        if (t == Magia) {
            return "Pergaminho";
        } else {
            return "Receita";
        }
    }

}
