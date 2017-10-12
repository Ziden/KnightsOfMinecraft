    /*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, Gabripj, Feldmann
 Patrocionio: InstaMC

 */
package nativelevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import nativelevel.Menu.Menu;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.spec.PlayerSpec;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Jobs {

    public final static int success = 1;
    public final static int fail = -1;

    public static Random rnd = new Random();

    public static int getJobLevel(String job, Player p) {
        // metadata
        String jogador = p.getUniqueId().toString();
        int[] skills;
        skills = KoM.database.getSkills(jogador);

        int jobId = Menu.getId(job);

        int skill = skills[jobId];
        if (skill == 2) {
            return 1;
        } else if (skill == 1) {
            return 2;
        }
        return 0;
    }

    public static String getNomeDificuldade(int d) {
        if (d < 10) {
            return ChatColor.AQUA + "Extremamente Facil";
        } else if (d < 25) {
            return ChatColor.AQUA + "Muito Facil";
        } else if (d < 50) {
            return "§9Fácil";
        } else if (d < 75) {
            return ChatColor.YELLOW + "Média";
        } else if (d < 100) {
            return "§6Dificil";
        } else {
            return "§cMuito Dificil";
        }
    }

    public static List<String> getPrimarias(Player p) {
        List<String> primarias = new ArrayList<String>();
        int[] skills;
        skills = KoM.database.getSkills(p.getUniqueId().toString());
        int skillId = 0;
        while (skillId < skills.length) {
            int skill = skills[skillId];
            if (skill == 2) {
                primarias.add(Menu.getNome(skillId));
            }
            skillId++;
        }
        return primarias;
    }
    
    public static List<String> getSecundarias(Player p) {
        List<String> primarias = new ArrayList<String>();
        int[] skills;
        skills = KoM.database.getSkills(p.getUniqueId().toString());
        int skillId = 0;
        while (skillId < skills.length) {
            int skill = skills[skillId];
            if (skill == 1) {
                primarias.add(Menu.getNome(skillId));
            }
            skillId++;
        }
        return primarias;
    }

    // usado no metodo de dano para aliviar o lag com 1 cache 'momenentaneo' só pro evento
    public static int getJobLevel(String job, Player p, Integer[] skillcache) {
        String jogador = p.getUniqueId().toString();
        int jobId = Menu.getId(job);
        if (skillcache[jobId] != null) {
            return skillcache[jobId];
        } else {
            int[] skills = KoM.database.getSkills(jogador);
            int skill = skills[jobId];
            skillcache[jobId] = skill;
            if (skill == 2) {
                return 1;
            } else if (skill == 1) {
                return 2;
            }
            return 0;
        }
    }

    public static TipoClasse getJobLevel(Classe job, Player p) {
        int lvl = getJobLevel(job.name(), p);
        if (lvl == 1) {
            return TipoClasse.PRIMARIA;
        } else if (lvl == 2) {
            return TipoClasse.SEGUNDARIA;
        } else {
            return TipoClasse.NADA;
        }
    }

    private static double SORTE_PRIMARIA = 25;

    private static double SORTE_SECUNDARIA = 9;

    private static double SORTE_NADA = -50;

    public static void main(String[] args) {

        //double chance = getFinalChangeToSucess(110, 100, TipoClasse.PRIMARIA);
        int level = 10;
        int difficulty = 85;
        double chance = 0;
        if (level < 10) {
            level = 10;
        }
        if (level < difficulty) {
            double diferenca = (difficulty - level); // menor conforme eu upo
            chance = 25 - (diferenca * 2); // precisa diferença 25 pra ter 100% 
            if (chance < 0) {
                chance = 0;
            }
        } else if (level >= difficulty) {
            double diferenca = (level - difficulty); // menor conforme eu upo
            chance = 25 + (diferenca);
        } else {
            chance = 25;
        }
        TipoClasse jobLevel = TipoClasse.PRIMARIA;
        if (jobLevel == TipoClasse.PRIMARIA) // skill primaria
        {
            double ratio = (0.5 + (level / 200d));
            System.out.println("RATIO " + ratio);
            chance += (SORTE_PRIMARIA * ratio);

        } else if (jobLevel == TipoClasse.SEGUNDARIA) // skill secundaria
        {
            chance += SORTE_SECUNDARIA;
            KoM.debug("Chance secundaria: +" + SORTE_SECUNDARIA + "%");
        } else {
            chance += (SORTE_NADA * (level / 100d));
            KoM.debug("Chance sem classe: +" + SORTE_NADA + "%");
            if (chance > 26) {
                chance = 26;
            }
        }

        int chanceBase = (int) Math.round(chance);

        double diferencaChances = chance - chanceBase;

        int chanceBonus = (int) Math.round(diferencaChances);

        System.out.println("BASE " + chanceBase + " BONUS " + chanceBonus);
    }

    public static double getChancesToSuccess(double difficulty, double level) {
        if (level < 10) {
            level = 10;
        }
        if (level < difficulty) {
            double diferenca = (difficulty - level); // menor conforme eu upo
            double chance = 25 - (diferenca * 2); // precisa diferença 25 pra ter 100% 
            if (chance < 0) {
                chance = 0;
            }
            return chance;
        } else if (level >= difficulty) {
            double diferenca = (level - difficulty); // menor conforme eu upo
            double chance = 25 + (diferenca);
            return chance;
        } else {
            return 25;
        }
    }

    public static double getFinalChangeToSucess(double dif, double level, TipoClasse jobLevel, Player p) {

        return getFinalChangeToSucess(dif, level, jobLevel);
    }

    public static double getFinalChangeToSucess(double dif, double level, TipoClasse jobLevel) {
        double chance = getChancesToSuccess(dif, level);
        if (jobLevel == TipoClasse.PRIMARIA) // skill primaria
        {
            chance += (SORTE_PRIMARIA * (0.5 + (level / 200d)));
            KoM.debug("Chance primaria: +" + SORTE_PRIMARIA + "%");
        } else if (jobLevel == TipoClasse.SEGUNDARIA) // skill secundaria
        {
            chance += SORTE_SECUNDARIA;
            KoM.debug("Chance secundaria: +" + SORTE_SECUNDARIA + "%");
        } else {
            chance += (SORTE_NADA * (level / 100d));
            KoM.debug("Chance sem classe: +" + SORTE_NADA + "%");
            if (chance > 26) {
                chance = 26;
            }
        }
        if (dif == 1) {
            if (level < 25) {
                if (jobLevel == TipoClasse.PRIMARIA) {
                    chance = 75;
                } else if (jobLevel == TipoClasse.SEGUNDARIA) {
                    chance = 40;
                } else {
                    chance = 30;
                }
            } else {
                if (jobLevel == TipoClasse.PRIMARIA) {
                    chance = 100;
                } else if (jobLevel == TipoClasse.SEGUNDARIA) {
                    chance = 75;
                } else {
                    chance = 30;
                }
            }
        }
        if (level + 35 < dif) {
            chance = 0;
        }
        return chance;
    }

    public static Sucesso hasSuccess(int i, Classe classe, Player player) {
        return Jobs.hasSuccess(i, classe, player, 0);
    }

    public static enum TipoClasse {

        PRIMARIA, SEGUNDARIA, NADA;
    }

    public static enum Classe {

        Engenheiro, Alquimista, Fazendeiro, Ferreiro, Ladino, Paladino, Lenhador, Mago, Minerador;
    }

    public static class Sucesso {

        public boolean acertou;
        public int chanceBase;
        public int chanceBonus;

    }

    public static Sucesso hasSuccess(int difficulty, Classe job, Player p, int bonus) {

        Sucesso sucesso = new Sucesso();

        int nivel = p.getLevel();
        if (nivel < 10) {
            nivel = 10;
        }
        TipoClasse jobLevel = getJobLevel(job, p);

        double chance = getFinalChangeToSucess(difficulty, p.getLevel(), jobLevel, p);

        sucesso.chanceBase = (int) Math.round(chance);

        KoM.debug("Dificuldade: " + difficulty);
        KoM.debug("Formula: random(75) - 25 + dificuldade");
        KoM.debug("Chance% = " + chance);

        // se tiver algum bonus
        chance += bonus;

        // inventor +5% chance
        if (PlayerSpec.temSpec(p, PlayerSpec.Inventor)) {
            chance += 5;
        }

        double diferencaChances = chance - sucesso.chanceBase;

        sucesso.chanceBonus = (int) Math.round(diferencaChances);

        if (chance < rnd.nextInt(100)) {
            sucesso.acertou = false;

        } else {
            sucesso.acertou = true;
        }
        return sucesso;
    }

    // metodo antigo
    public static int hasSuccess(int difficulty, String job, Player p) {
        Sucesso sucesso = hasSuccess(difficulty, Classe.valueOf(job), p, 0);
        if (sucesso.acertou) {
            return Jobs.success;
        } else {
            return Jobs.fail;
        }
    }

}
