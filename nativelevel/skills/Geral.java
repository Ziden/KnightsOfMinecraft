package nativelevel.skills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nativelevel.Classes.Mage.Wizard;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Geral {

     public static void load() {
        
        List<Skill> skills = new ArrayList<Skill>();

        Skill s1 = new Skill("Nadar", 80, false);
        s1.setLore(new String[]{"§9Permite nadar se estiver pelado."});
        skills.add(s1);

        skills.add(s1);
       
        Collections.sort(skills, new Comparator<Skill>() {
            @Override
            public int compare(Skill p1, Skill p2) {
                return p1.getNivel() - p2.getNivel(); 
            }
        });

        SkillMaster.skills.put("geral", skills);
        
    }

}
