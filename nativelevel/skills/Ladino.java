package nativelevel.skills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nativelevel.Classes.Mage.Wizard;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Ladino {

      public static void load() {
        
        List<Skill> skills = new ArrayList<Skill>();

        Skill sMob = new Skill("Caçador de Mobs", 6, false);
        sMob.setLore(new String[]{"§9Ganha mais XP ao matar Monstros"});
        skills.add(sMob);

        
        Skill s1 = new Skill("Bomba de Fumaça", 15, false);
        s1.setLore(new String[]{"§9Permite usar bomba de fumaça para ficar invisivel."});
        skills.add(s1);

        Skill s2 = new Skill("Backstab", 13, true);
        s2.setLore(new String[]{"§9Pode atacar por traz para causar grande dano."});
        skills.add(s2);

        Skill s3 = new Skill("Esquiva Perfeita", 2, false);
        s3.setLore(new String[]{"§9Aumenta chance de esquiva a ataques."});
        skills.add(s3);

        Skill s4 = new Skill("Mira com Arcos", 10, true);
        s4.setLore(new String[]{"§9Aumenta a precisão com arcos.", "Aumentar o nível aumenta as chances."});
        skills.add(s4);
        
        Skill s5 = new Skill("Sniper Shot", 9, true);
        s5.setLore(new String[]{"§9Se atirar a longa distância causa grande dano"});
        skills.add(s5);
        
        Skill s6 = new Skill("Lockpick", 20, true);
        s6.setLore(new String[]{"§9Pode espiar baús de guildas inimigas"});
        skills.add(s6);

        Skill s7 = new Skill("Pé de Cabra", 5, true);
        s7.setLore(new String[]{"§9Permite arromar e roubar items de baús inimigos"});
        skills.add(s7);
        
        Skill s8 = new Skill("Ender Pearl", 7, true);
        s8.setLore(new String[]{"§9Permite usar perolas do Ender."});
        skills.add(s8);

        skills.add(s1);
        skills.add(s2);
        skills.add(s3);
        skills.add(s4);
        skills.add(s4);
        skills.add(s5);
        skills.add(s6);
        skills.add(s7);
        skills.add(s8);
        
        Collections.sort(skills, new Comparator<Skill>() {
            @Override
            public int compare(Skill p1, Skill p2) {
                return p1.getNivel() - p2.getNivel(); 
            }
        });

        SkillMaster.skills.put("ladino", skills);
        
    }

}
