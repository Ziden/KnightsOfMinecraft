/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericos.komzin.libzinha.comandos;

import genericos.komzin.libzinha.InstaMCLibKom;
import genericos.komzin.libzinha.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Gabriel
 */
public class ComandoR implements CommandExecutor {

    public ComandoR() {
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        Player p = (Player) cs;

        PlayerInfo meta = InstaMCLibKom.getinfo(p);
        if (meta.lastPlayerMessage == null) {
            p.sendMessage(ChatColor.RED + "Ninguem falou com voce para voce dar /r <mensagem>");
            return true;
        }
        Player target = Bukkit.getPlayer(meta.lastPlayerMessage);

        if (target == null) {
            p.sendMessage(ChatColor.RED + "Este jogador nao esta online !");
            return true;
        }
        if (p.hasMetadata("Silenciado")) {
            p.sendMessage("§f[§4!§f]§8 Voce esta silenciado. Talves voce tenha falado algo inadequado no chat!");
            return true;
        }
        if (args.length == 0) {
            p.sendMessage(ChatColor.YELLOW + "Iniciando conversa privada com " + ChatColor.WHITE + target.getName() + ChatColor.YELLOW + ".");
            meta.talkingTo = target.getName();
            return true;
        }

        StringBuffer str = new StringBuffer();
        str.append(ChatColor.DARK_AQUA + "De " + p.getName() + ": " + ChatColor.AQUA);

        StringBuffer strFrom = new StringBuffer();
        strFrom.append(ChatColor.DARK_AQUA + "Para " + target.getName() + ": " + ChatColor.AQUA);

        for (int x = 0; x < args.length; x++) {
            str.append(args[x] + " ");
            strFrom.append(args[x] + " ");
        }
        p.sendMessage(strFrom.toString());

        target.sendMessage(str.toString());
        PlayerInfo InfoTarget = InstaMCLibKom.getinfo(target);
        InfoTarget.lastPlayerMessage = p.getName();
        return true;
    }
}
