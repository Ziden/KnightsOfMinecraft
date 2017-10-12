package nativelevel.skills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nativelevel.Classes.Mage.Wizard;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Fazendeiro {

      public static void load() {
        
        List<Skill> skills = new ArrayList<Skill>();

        Skill s1 = new Skill("Plantador", 2, false);
        s1.setLore(new String[]{"§9Aumenta chances de sucesso ao plantar e colher.", "§9Quanto maior seu nivel, maior a chance"});
        s1.setSkillDeCraft(true);
        skills.add(s1);

        Skill s2 = new Skill("Vara de Pescar", 10, true);
        s2.setLore(new String[]{"§9Permite puxar inimigos com vara de pescar."});
        skills.add(s2);

        Skill s3 = new Skill("Pescador", 5, false);
        s3.setLore(new String[]{"§9Aumenta chances de sucesso ao pescar.", "§9Quanto maior seu nivel, maior a chance e mais raridades"});
        s3.setSkillDeCraft(true);
        skills.add(s3);

        Skill s4 = new Skill("Slimeball Envenenada", 9, true);
        s4.setLore(new String[]{"§9Permite craftar e usar slimeballs envenenadas"});
        skills.add(s4);
        
        Skill s5 = new Skill("Domador de Lobos", 13, true);
        s5.setLore(new String[]{"§9Pode ter um lobo usando um osso magico"});
        skills.add(s5);
        
        // parei AKE
        
        Skill s6 = new Skill("Pokeovos", 30, true);
        s6.setLore(new String[]{"§9Permite guardar animais em ovos"});
        skills.add(s6);

        Skill s7 = new Skill("Folhas de Mana", 16, true);
        s7.setLore(new String[]{"§9Encontra folhas de mana pegando folhas normais"});
        skills.add(s7);
        
        Skill s8 = new Skill("Expert em Couro", 22, true);
        s8.setLore(new String[]{"§9Permite criar armaduras de couro muito melhores"});
        skills.add(s8);
        
        Skill s9 = new Skill("Recursos Extras de Animais", 17, true);
        s9.setLore(new String[]{"§9Permite coletar recursos extras de animais usando uma tesoura"});
        skills.add(s9);
        
        Skill s99 = new Skill("Mestre das Inxadas", 16, true);
        s99.setLore(new String[]{"§9Bonus ao bater com inxadas"});
        skills.add(s99);
        
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
        skills.add(s99);
        
        Collections.sort(skills, new Comparator<Skill>() {
            @Override
            public int compare(Skill p1, Skill p2) {
                return p1.getNivel() - p2.getNivel(); 
            }
        });

        SkillMaster.skills.put("fazendeiro", skills);
        
    }

}
