package nativelevel.skills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nativelevel.Classes.Mage.Wizard;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Mago {

      public static void load() {
        List<Skill> skills = new ArrayList<Skill>();

        // ENCHANTS
        Skill s0 = new Skill("Conjurador", 10, false);
        s0.setLore(new String[]{"§9Aumenta chances de sucesso ao soltar magias.", "§9Quanto maior seu nivel, maior a chance"});
        s0.setSkillDeCraft(true);
        skills.add(s0);
        
        // ENCHANTS
        Skill s1 = new Skill("Encantamento", 10, false);
        s1.setLore(new String[]{"§9Aumenta chances de sucesso ao encantar.", "§9Quanto maior seu nivel, maior a chance"});
        skills.add(s1);

        // FOGO FOGO FOGO
        // FIREBALL GRANDE
        Skill s2 = new Skill("Bomba de Fogo", 20, false);
        s2.setLore(new String[]{"§9Combine elementos com um livro", Wizard.FIRE + " " + Wizard.FIRE + " " + Wizard.FIRE, "§9Solta uma bola de fogo imensa", "Precisa da magia no livro de magias"});
        skills.add(s2);

        // FOGO FOGO RAIO
        // fireball pequena
        Skill s3 = new Skill("Bola de Fogo", 2, false);
        s3.setLore(new String[]{"§9Combine elementos com um livro", Wizard.FIRE + " " + Wizard.FIRE + " " + Wizard.LIGHT, "§9Solta uma bola de fogo", "Precisa da magia no livro de magias"});
        skills.add(s3);

        // FOGO RAIO RAIO
        // FIRE NOVA
        Skill s4 = new Skill("Anel de Fogo", 10, false);
        s4.setLore(new String[]{"§9Combine elementos com um livro", Wizard.FIRE + " " + Wizard.LIGHT + " " + Wizard.LIGHT, "§9Um anel de fogo que revela inimigos", "Precisa da magia no livro de magias"});
        skills.add(s4);

        // TERRA TERRA RAIO
        // PRISAO
        Skill s5 = new Skill("Prisão de Teia", 5, false);
        s5.setLore(new String[]{"§9Combine elementos com um livro", Wizard.TERRA + " " + Wizard.TERRA + " " + Wizard.LIGHT, "§9Prende um inimigo em teias", "Precisa da magia no livro de magias"});
        skills.add(s5);

        // RAIO RAIO RAIO
        // RAIO
        Skill s6 = new Skill("Raio", 8, false);
        s6.setLore(new String[]{"§9Combine elementos com um livro", Wizard.LIGHT + " " + Wizard.LIGHT + " " + Wizard.LIGHT, "§9Solta um raio no inimigo", "Precisa da magia no livro de magias"});
        skills.add(s6);

        // FOGO RAIO TERRA
        // CONVERSÂO DE ALMA
        Skill s7 = new Skill("Conversão de Alma", 15, false);
        s7.setLore(new String[]{"§9Combine elementos com um livro", Wizard.FIRE + " " + Wizard.LIGHT + " " + Wizard.TERRA, "§9Buffa aliados proximos", "Precisa da magia no livro de magias"});
        skills.add(s7);

        // TERRA TERRA FOGO
        // REPULSAO
        Skill s8 = new Skill("Repulsão", 25, false);
        s8.setLore(new String[]{"§9Combine elementos com um livro", Wizard.TERRA + " " + Wizard.TERRA + " " + Wizard.FIRE, "§9Repele inimigos proximos", "Precisa da magia no livro de magias"});
        skills.add(s8);

        // TERRA TERRA TERRA
        // MARCAR RUNA
        Skill s9 = new Skill("Marcar Runa", 50, false);
        s9.setLore(new String[]{"§9Combine elementos com um livro", Wizard.TERRA + " " + Wizard.TERRA + " " + Wizard.TERRA, "§9Marca uma runa no local", "§9Pode criar um portal para o local marcado"});
        skills.add(s9);

        // FOGO FOGO TERRA
        // ESCUDO REFLETOR
        Skill s10 = new Skill("Escudo Refletor", 35, false);
        s10.setLore(new String[]{"§9Combine elementos com um livro", Wizard.FIRE + " " + Wizard.FIRE + " " + Wizard.TERRA, "§9Previne e reflete dano de um ataque", "Precisa da magia no livro de magias"});
        skills.add(s10);

        // TERRA RAIO RAIO
        // LAMPEJO
        Skill s11 = new Skill("Lampejo", 22, false);
        s11.setLore(new String[]{"§9Combine elementos com um livro", Wizard.TERRA + " " + Wizard.LIGHT + " " + Wizard.LIGHT, "§9Se teleporta para um local proximo", "Precisa da magia no livro de magias"});
        skills.add(s11);

        Skill sMob = new Skill("Caçador de Mobs", 6, false);
        sMob.setLore(new String[]{"§9Ganha mais XP ao matar Monstros"});
        skills.add(sMob);
        
        Collections.sort(skills, new Comparator<Skill>() {
            @Override
            public int compare(Skill p1, Skill p2) {
                return p1.getNivel() - p2.getNivel(); 
            }
        });

        SkillMaster.skills.put("mago", skills);
        
    }

}
