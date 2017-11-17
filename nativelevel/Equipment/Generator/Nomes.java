package nativelevel.Equipment.Generator;

import java.util.HashSet;
import java.util.Set;
import nativelevel.Equipment.Atributo;
import nativelevel.KoM;

/**
 *
 * @author Ziden
 *
 */
public class Nomes {

    private static Set<Mod> prefixo = new HashSet<Mod>();
    private static Set<Mod> sufixo = new HashSet<Mod>();

    static {
        prefixo.add(new Mod(new String[]{"Afiado", "Muito Afiado", "Dilacerador"}, Atributo.Dano_Fisico));
        sufixo.add(new Mod(new String[]{"da Pancada", "da Bordoada", "do Pipoco"}, Atributo.Dano_Fisico));

        prefixo.add(new Mod(new String[]{"Protetor", "Rigido", "Solido"}, Atributo.Armadura));
        sufixo.add(new Mod(new String[]{"da Sobrevivencia", "da Fortitude", "da Invulnerabilidade"}, Atributo.Armadura));

        prefixo.add(new Mod(new String[]{"Lazarento", "Sortudo", "Milagroso"}, Atributo.Chance_Critico));
        sufixo.add(new Mod(new String[]{"da Criticidade", "da Sorte", "do Milagre"}, Atributo.Chance_Critico));

        prefixo.add(new Mod(new String[]{"Leve", "Muito Leve", "Levissimo"}, Atributo.Chance_Esquiva));
        sufixo.add(new Mod(new String[]{"da Pena", "da Nuvem", "do Guepardo"}, Atributo.Chance_Esquiva));

        prefixo.add(new Mod(new String[]{"Estonteador", "Atordoador", "Paralizante"}, Atributo.Chance_Stun));
        sufixo.add(new Mod(new String[]{"da Paradinha", "do Quieto", "da Paralizia"}, Atributo.Chance_Stun));

        prefixo.add(new Mod(new String[]{"Violento", "Sanguinario", "Degolador"}, Atributo.Dano_Critico));
        sufixo.add(new Mod(new String[]{"do Porretão", "do Lapadão", "do Pancadão"}, Atributo.Dano_Critico));

        prefixo.add(new Mod(new String[]{"Preciso", "Atirador", "Mirador"}, Atributo.Dano_Distancia));
        sufixo.add(new Mod(new String[]{"do Arqueiro", "do Espreitador", "do Gavião"}, Atributo.Dano_Distancia));

        prefixo.add(new Mod(new String[]{"Vivo", "Vitalizado", "Energizado"}, Atributo.Vida));
        sufixo.add(new Mod(new String[]{"da Vida", "da Luz", "da Divindade"}, Atributo.Vida));

        prefixo.add(new Mod(new String[]{"Cortador", "Penetrador", "Perfurador"}, Atributo.Penetr_Armadura));
        sufixo.add(new Mod(new String[]{"da Discordia", "da Invasão", "da Peladisse"}, Atributo.Penetr_Armadura));

        prefixo.add(new Mod(new String[]{"Atrasador", "Distanciador", "Dormidor"}, Atributo.Tempo_Stun));
        sufixo.add(new Mod(new String[]{"do Sono", "do Tempo", "do Tempo Perdido"}, Atributo.Tempo_Stun));
        
        prefixo.add(new Mod(new String[]{"Espiritual", "Espiritualista", "Astral"}, Atributo.Mana));
        sufixo.add(new Mod(new String[]{"dos Astros", "dos Espiritos", "das Almas"}, Atributo.Mana));
        
        prefixo.add(new Mod(new String[]{"Persistente", "Persistente", "Barbaro"}, Atributo.Stamina));
        sufixo.add(new Mod(new String[]{"da Persistencia", "da Continuidade", "da Barbarie"}, Atributo.Stamina));
        
        prefixo.add(new Mod(new String[]{"Regenerador", "Espiritual", "Meditador"}, Atributo.Regen_Mana));
        sufixo.add(new Mod(new String[]{"da Meditação", "da Meditação", "da Meditação"}, Atributo.Regen_Mana));
        
        prefixo.add(new Mod(new String[]{"Continuador", "Continuador", "Perseverador"}, Atributo.Regen_Stamina));
        sufixo.add(new Mod(new String[]{"da Força de Vontade", "da Força de Vontade", "da Força de Vontade"}, Atributo.Regen_Stamina));

        prefixo.add(new Mod(new String[]{"Magico", "Arcano", "Cabalistico"}, Atributo.Magia));
        sufixo.add(new Mod(new String[]{"da Magia", "do Elementalismo", "do Arcano"}, Atributo.Magia));

    }

    public static Mod getMod(Atributo a, Set<Mod> lista) {
        for (Mod m : lista) {
            if (m.getAttr() == a) {
                return m;
            }
        }
        KoM.log.info("NAO ACHEI ATRIBUTO PRA MOD "+a.name());
        return null;
    }

    public static String getPrefixo(Atributo a, double pct) {
        int i = 0;
        if (pct > 33 && pct <= 66) {
            i = 1;
        } else if (pct > 66) {
            i = 2;
        }
        return getMod(a, prefixo).getDesc()[i];
    }

    public static String getSufixo(Atributo a, double pct) {
        int i = 0;
        if (pct > 33 && pct <= 66) {
            i = 1;
        } else if (pct > 66) {
            i = 2;
        }
        return getMod(a, sufixo).getDesc()[i];
    }

    public static String f(String m) {
        String ultimo = m.substring(m.length() - 1);
        if (ultimo.equalsIgnoreCase("r")) {
            return m + "a";
        } else if (ultimo.equalsIgnoreCase("o")) {
            return m.substring(0, m.length() - 1) + "a";
        }
        return m;
    }

}
