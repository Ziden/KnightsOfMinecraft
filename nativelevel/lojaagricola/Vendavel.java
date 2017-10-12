package nativelevel.lojaagricola;

import org.bukkit.Material;

public class Vendavel {

    public Vendavel(Material m, int precoPack, String nome) {
        this.m = m;
        this.precoPack = precoPack;
        this.nome = nome;
    }
    
    public String getNomeTecnico() {
        return nome.replace(" ", "_");
    }

    public String nome;
    public Material m;
    public int precoPack = 20;
}
