package nativelevel.skills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nativelevel.Classes.Mage.Wizard;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Lenhador {

      public static void load() {
        
        List<Skill> skills = new ArrayList<Skill>();

        Skill s1 = new Skill("Cortar Madeira", 15, false);
        s1.setLore(new String[]{"§9Aumenta chances de sucesso cortar madeira.", "§9Quanto maior seu nivel, maior a chance"});
        s1.setSkillDeCraft(true);
        skills.add(s1);

        Skill s2 = new Skill("Criar Tabuas", 13, true);
        s2.setLore(new String[]{"§9Aumenta chances de sucesso criar tabuas.", "§9Quanto maior seu nivel, maior a chance"});
        s1.setSkillDeCraft(true);
        skills.add(s2);

        Skill s3 = new Skill("Coletor de Boas Madeiras", 2, false);
        s3.setLore(new String[]{"§9Coleta recursos extras cortando madeira."});
        skills.add(s3);

        Skill s4 = new Skill("Machadada Epica", 10, true);
        s4.setLore(new String[]{"§9Carrega um machado para um golpe mortal."});
        skills.add(s4);
        
        Skill s5 = new Skill("Salto do Lenhador", 9, true);
        s5.setLore(new String[]{"§9Permite dar um salto a frente."});
        skills.add(s5);


        skills.add(s1);
        skills.add(s2);
        skills.add(s3);
        skills.add(s4);
        skills.add(s4);
        skills.add(s5);
        
        Collections.sort(skills, new Comparator<Skill>() {
            @Override
            public int compare(Skill p1, Skill p2) {
                return p1.getNivel() - p2.getNivel(); 
            }
        });

        SkillMaster.skills.put("lenhador", skills);
        
    }

}
