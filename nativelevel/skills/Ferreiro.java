package nativelevel.skills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nativelevel.Classes.Mage.Wizard;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Ferreiro {

      public static void load() {
        List<Skill> skills = new ArrayList<Skill>();

        Skill s1 = new Skill("Forjar Equipamento", 3, false);
        s1.setLore(new String[]{"§9Aumenta chances de sucesso ao forjar equips.", "§9Quanto maior seu nivel, maior a chance"});
        s1.setSkillDeCraft(true);
        skills.add(s1);

        Skill s2 = new Skill("Usar Bigorna", 30, false);
        s2.setLore(new String[]{"§9Forje com uma bigorna proxima para aumentar chance de sucesso"});
        skills.add(s2);

        Skill s3 = new Skill("Pele Rigida", 6, false);
        s3.setLore(new String[]{"§9Aumenta resistência contra ataques"});
        skills.add(s3);

        Skill s4 = new Skill("Especialização com Porretes", 4, false);
        s4.setLore(new String[]{"§9Aumenta o dano usando Pás"});
        skills.add(s4);

        Skill s5 = new Skill("Brasão Flamejante", 35, false);
        s5.setLore(new String[]{"§9Permite usar uma blaze rod", "§9para colocar inimigos no fogo"});
        skills.add(s5);

        Skill s6 = new Skill("Golpe Esmagador", 14, false);
        s6.setLore(new String[]{"§9Permite um golpe esmagador", "§9Segurando shift ao bater"});
        skills.add(s6);

        Skill s7 = new Skill("Forjar Barras", 5, false);
        s7.setLore(new String[]{"§9Aumenta chances de sucesso ao forjar barras.", "§9Quanto maior seu nivel, maior a chance"});
        skills.add(s7);
        
        Skill s8 = new Skill("Reparar Items", 25, false);
        s8.setLore(new String[]{"§9Aumenta chances de sucesso ao reparar items.", "§9Quanto maior seu nivel, maior a chance"});
        skills.add(s8);
        
        Skill s9 = new Skill("Pele Queimada", 3, false);
        s9.setLore(new String[]{"§9Não recebe dano por ataques de fogo."});
        skills.add(s9);

        skills.add(s1);
        skills.add(s2);
        skills.add(s3);
        skills.add(s4);
        skills.add(s5);
        skills.add(s6);
        skills.add(s7);
        skills.add(s8);
        skills.add(s9);

        Collections.sort(skills, new Comparator<Skill>() {
            @Override
            public int compare(Skill p1, Skill p2) {
                return p1.getNivel() - p2.getNivel(); 
            }
        });

        SkillMaster.skills.put("ferreiro", skills);
        
    }

}
