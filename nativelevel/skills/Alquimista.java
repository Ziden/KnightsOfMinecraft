package nativelevel.skills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nativelevel.Classes.Mage.Wizard;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Alquimista {

    public static void load() {
        
        List<Skill> skills = new ArrayList<Skill>();

        Skill s1 = new Skill("Criar Poções", 2, false);
        s1.setLore(new String[]{"§9Aumenta chances de sucesso ao criar poções.", "§9Quanto maior seu nivel, maior a chance"});
        s1.setSkillDeCraft(true);
        skills.add(s1);

        Skill s2 = new Skill("Foice Mestra", 4, true);
        s2.setLore(new String[]{"§9Bonus ao bater com foices."});
        skills.add(s2);

        Skill s3 = new Skill("Sangue Tóxico", 5, false);
        s3.setLore(new String[]{"§9Não sente efeitos de veneno."});
        skills.add(s3);
        
        Skill s5 = new Skill("Arremeçar TNT", 20, true);
        s5.setLore(new String[]{"§9Pode jogar TNT longe."});
        skills.add(s5);
        
        Skill s6 = new Skill("Caldeirão de Poções", 30, true);
        s6.setLore(new String[]{"§9Pode misturar poções no caldeirão"});
        skills.add(s6);

        Skill s7 = new Skill("Usar Poções de Splash", 15, true);
        s7.setLore(new String[]{"§9Pode arremeçar Poções"});
        
        skills.add(s7);

        skills.add(s1);
        skills.add(s2);
        skills.add(s3);
        skills.add(s5);
        skills.add(s6);
        skills.add(s7);
        
        Collections.sort(skills, new Comparator<Skill>() {
            @Override
            public int compare(Skill p1, Skill p2) {
                return p1.getNivel() - p2.getNivel(); 
            }
        });

        SkillMaster.skills.put("alquimista", skills);
        
    }

}
