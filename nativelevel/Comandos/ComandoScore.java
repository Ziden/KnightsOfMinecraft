/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Comandos;

import java.util.HashSet;
import java.util.UUID;
import nativelevel.scores.SBCore;
import nativelevel.scores.ScoreboardManager;
import nativelevel.utils.Cooldown;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

/**
 *
 * @author Carlos
 */
public class ComandoScore implements CommandExecutor {

    public static HashSet<UUID> remove = new HashSet<UUID>();

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        Player p = (Player) cs;
        if (Cooldown.isCooldown(p, "comandoscore")) {
            p.sendMessage("§4§l[!] §eEspere um pouco...");
            return false;
        }
        Cooldown.setMetaCooldown(p, "comandoscore", 10000);
        if (remove.contains(p.getUniqueId())) {
            remove.remove(p.getUniqueId());
            p.sendMessage("§c§l[!] Scoreboard adicionado!");

            SBCore.AtualizaObjetivos();
        } else {
            remove.add(p.getUniqueId());
            ScoreboardManager.clearScoreboard(p, DisplaySlot.SIDEBAR);
            p.sendMessage("§c§l[!] Scoreboard removido!");
        }
        return false;
    }

}
