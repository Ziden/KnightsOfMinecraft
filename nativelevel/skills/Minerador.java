package nativelevel.skills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nativelevel.Classes.Mage.Wizard;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Minerador {

      public static void load() {
        List<Skill> skills = new ArrayList<Skill>();

        Skill s1 = new Skill("Obter Minérios", 5, false);
        s1.setLore(new String[]{"§9Aumenta chances de sucesso ao minerar.", "§9Quanto maior seu nivel, maior a chance"});
        s1.setSkillDeCraft(true);
        skills.add(s1);

        Skill s2 = new Skill("Craftar Blocos", 20, false);
        s2.setLore(new String[]{"§9Aumenta chances de sucesso ao criar blocos.", "§9Quanto maior seu nivel, maior a chance"});
        s2.setSkillDeCraft(true);
        skills.add(s2);

        Skill s3 = new Skill("Escavador Experiente", 10, false);
        s3.setLore(new String[]{"§9Chance de encontrar items raros minerando"});
        skills.add(s3);

        Skill s4 = new Skill("Desarmar", 2, false);
        s4.setLore(new String[]{"§9Permite desarmar inimigos usando picaretas"});
        skills.add(s4);
        
        Skill s5 = new Skill("Escalar", 3, false);
        s4.setLore(new String[]{"§9Permite escalar paredes com uma picareta"});
        skills.add(s4);

        skills.add(s1);
        skills.add(s2);
        skills.add(s3);
        skills.add(s4);
        
        Collections.sort(skills, new Comparator<Skill>() {
            @Override
            public int compare(Skill p1, Skill p2) {
                return p1.getNivel() - p2.getNivel(); 
            }
        });

        SkillMaster.skills.put("minerador", skills);
        
    }

}
