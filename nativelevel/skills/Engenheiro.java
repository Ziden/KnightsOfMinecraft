package nativelevel.skills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nativelevel.Classes.Mage.Wizard;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Engenheiro {

      public static void load() {
        
        List<Skill> skills = new ArrayList<Skill>();

        Skill s1 = new Skill("Criar Items com Redstone", 3, false);
        s1.setLore(new String[]{"§9Aumenta chances de sucesso ao craftar items com redstone.", "§9Quanto maior seu nivel, maior a chance"});
        s1.setSkillDeCraft(true);
        skills.add(s1);

        Skill s2 = new Skill("Implantar Mecanicas", 13, true);
        s2.setLore(new String[]{"§9Permite colocar mecanismos de redstone.", "§9Quanto maior seu nivel, maior a chance"});
        s2.setSkillDeCraft(true);
        skills.add(s2);

        Skill s3 = new Skill("Bonka Boom", 2, false);
        s3.setLore(new String[]{"§9Permite atirar com a pistola 'Bonka Boom'."});
        skills.add(s3);

        Skill s4 = new Skill("Coleira Elétrica", 10, true);
        s4.setLore(new String[]{"§9Permite usar uma coleira eletrica em inimigos."});
        skills.add(s4);
        
        Skill s5 = new Skill("Auto Dispenser", 9, true);
        s5.setLore(new String[]{"§9Cria um auto dispenser que atira em inimigos"});
        skills.add(s5);
        
        Skill s6 = new Skill("Mega Bomba C4", 20, true);
        s6.setLore(new String[]{"§9Pode explodir guildas com C4"});
        skills.add(s6);

        Skill s7 = new Skill("Resistência Elétrica", 5, true);
        s7.setLore(new String[]{"§9Se torna resistente a eletrecidade"});
        skills.add(s7);
        
        Skill s8 = new Skill("Para Raios", 7, true);
        s8.setLore(new String[]{"§9Permite usar um Para Raio para absorver relampagos."});
        skills.add(s8);
        
        Skill s9 = new Skill("Mina Explosiva", 18, true);
        s9.setLore(new String[]{"§9Pode colocar uma mina explosiva e criar um detonador"});
        skills.add(s9);

        skills.add(s1);
        skills.add(s2);
        skills.add(s3);
        skills.add(s4);
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

        SkillMaster.skills.put("engenheiro", skills);
        
    }

}
