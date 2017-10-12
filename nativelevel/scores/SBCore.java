package nativelevel.scores;

import cashgame.principal.VipManiaEpic;
import java.text.DecimalFormat;
import nativelevel.Comandos.ComandoScore;
import nativelevel.KoM;
import nativelevel.Attributes.Mana;
import nativelevel.Attributes.Stamina;
import nativelevel.karma.Criminoso;
import nativelevel.scores.ScoreboardManager;
import nativelevel.titulos.Sexo;
import nativelevel.titulos.TituloDB;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 *
 * @author NeT32
 *
 */
public class SBCore {

    //public static String fixedName = ChatColor.GOLD + "§lKnights" + ChatColor.YELLOW + "§lOf" + ChatColor.GOLD + "§lMinecraft";
    //public static String fixedName = ChatColor.YELLOW+""+ChatColor.BOLD+"Knights Of Minecraft";
    public static String fixedName = "§6§lKnights §e§lOf §6§lMinecraft";

    public static void setDisplayName(String NovoNome) {
        ScoreboardManager.setDisplayName(DisplaySlot.SIDEBAR, fixedName + NovoNome);
    }

    public static void CriarObjetivos() {
        SBCore.setDisplayName("");
        ScoreboardManager.makeScore(DisplaySlot.BELOW_NAME, "health", 0);
        ScoreboardManager.setDisplayName(DisplaySlot.BELOW_NAME, ChatColor.RED + "❤");
        SBCore.AtualizaObjetivos();
    }

    public static void limpa() {
        for (Player pOn : Bukkit.getOnlinePlayers()) {
            ScoreboardManager.clearScoreboard(pOn, DisplaySlot.SIDEBAR);
        }
    }

    public static void setLevelPoints(Player p, int ScoreKill) {
        ScoreboardManager.makeScore(DisplaySlot.PLAYER_LIST, p.getName(), ScoreKill);
    }

    public static void AtualizaObjetivos() {
        for (Player pOn : Bukkit.getOnlinePlayers()) {
            if (ComandoScore.remove.contains(pOn.getUniqueId())) {
                continue;
            }
            SBCore.AtualizaObjetivos(pOn);
        }
    }

    public static void AtualizaObjetivos(Player p) {
        int i = 15;
        if (p == null) {
            return;
        }
        ScoreboardManager.setScoreLine(p, i--, ChatColor.GREEN + "" + ChatColor.BOLD + "Cash");
        ScoreboardManager.setScoreLine(p, i--, "" + VipManiaEpic.databaseCodigos.getCash(p));
        ScoreboardManager.setScoreLine(p, i--, " ");
        ScoreboardManager.setScoreLine(p, i--, ChatColor.AQUA + "" + ChatColor.BOLD + "Mana");
        Mana mana = Mana.getMana(p);
        ScoreboardManager.setScoreLine(p, i--, mana.mana + " / " + mana.maxMana);
        ScoreboardManager.setScoreLine(p, i--, "   ");
        ScoreboardManager.setScoreLine(p, i--, ChatColor.YELLOW + "" + ChatColor.BOLD + "Stamina");
        Stamina stamina = Stamina.getStamina(p);
        ScoreboardManager.setScoreLine(p, i--, stamina.stamina + " / " + stamina.maxStamina);
        ScoreboardManager.setScoreLine(p, i--, "    ");
        if (Criminoso.isCriminoso(p)) {
            Sexo s = TituloDB.getPlayerData(p).getSexo();
            String criminoso = s.feminiza("Criminoso");
            ScoreboardManager.setScoreLine(p, i--, ChatColor.GRAY + "" + ChatColor.BOLD + " " + criminoso.toUpperCase() + " ");
            ScoreboardManager.setScoreLine(p, i--, "");
        }
        int quests = KoM.quests.getQuests().size();
        int feitas = KoM.quests.getQuester(p.getUniqueId()).completedQuests.size();

        int pct = (100 * feitas) / quests;
        
        ScoreboardManager.setScoreLine(p, i--, ChatColor.BLUE + "" + ChatColor.BOLD + "Quests");
        ScoreboardManager.setScoreLine(p, i--, feitas +" / "+quests +" ("+pct+"%)");
    }
}
