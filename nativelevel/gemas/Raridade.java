/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.gemas;

/**
 *
 * @author User
 * 
 */

public enum Raridade {

    Comum("§f"),
    Incomum("§a"),
    Raro("§9"),
    Epico("§d"),
    Lendario("§e");
    
    public String cor;
    
    private Raridade(String cor) {
        this.cor = cor;
    }
    
    public String getIcone() {
        return cor + "♦";
    }
}
