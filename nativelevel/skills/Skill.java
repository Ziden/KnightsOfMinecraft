package nativelevel.skills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public class Skill {

    
    public Skill(String nome, int nivel, boolean precisaPrimaria) {
        this.nome = nome;
        this.nivel = nivel;
        this.precisaPrimaria = precisaPrimaria;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(String [] lore) {
        this.lore = Arrays.asList(lore);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public boolean isPrecisaPrimaria() {
        return precisaPrimaria;
    }

    public void setPrecisaPrimaria(boolean precisaPrimaria) {
        this.precisaPrimaria = precisaPrimaria;
    }

    public boolean isSkillDeCraft() {
        return skillDeCraft;
    }

    public void setSkillDeCraft(boolean skillDeCraft) {
        this.skillDeCraft = skillDeCraft;
    }

    private List<String> lore = new ArrayList<String>();
    private String nome;
    private int nivel;
    private boolean precisaPrimaria = false;
    private boolean skillDeCraft = false;
    
}
