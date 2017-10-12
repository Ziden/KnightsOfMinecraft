package instamc.coders.libkom.comandos;

import instamc.coders.libkom.InstaMCLibKom;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandoG implements CommandExecutor {

    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if ((cs instanceof Player)) {
            Player p = (Player) cs;
            int custo = Integer.parseInt(InstaMCLibKom.conf.getConfig().getProperty("ValorGlobal"));
            String moeda = InstaMCLibKom.conf.getConfig().getProperty("Moeda");

            if (p.getLevel() < 10) {
                custo = 1;
            }

            if (strings.length == 0) {
                return false;
            }
            if (!cs.hasPermission("chat.globalfree")) {
                if (!InstaMCLibKom.economy.has(p.getName(), custo)) {
                    p.sendMessage(ChatColor.RED + "Voce precisa de " + custo + " " + moeda + " para mandar uma mensagem no global");
                    return true;
                }
                p.sendMessage(ChatColor.GREEN + "Voce gastou " + custo + " " + moeda + " pelo chat global !");
                InstaMCLibKom.economy.withdrawPlayer(p.getName(), custo);
            }
            String clan = "";
            String channel = "§7[g]";
            String prefixo = "";
            String msg = "";
            String mensagem = "";
            String nick = p.getName();
            if(p.isOp() || p.hasPermission("kom.staff"))
                nick = ChatColor.GOLD+nick;
            
            prefixo = prefixo + InstaMCLibKom.chat.getPlayerPrefix(p);
            prefixo = prefixo.replaceAll("\\&", "§");
            ClanPlayer cp;
            if (InstaMCLibKom.sc != null) {
                cp = InstaMCLibKom.sc.getClanManager().getClanPlayer(p);
                if ((cp != null) && (cp.getClan() != null)) {
                    clan = cp.getTagLabel();
                }
            }
            for (String arg : strings) {
                msg = msg + arg + " ";
            }
            if (p.hasPermission("manialib.coresglobal")) {
                msg = msg.replaceAll("\\&", "§");
            }
            mensagem = channel + " " + prefixo + clan + "§f" + nick + "§7: " + msg;
            for (Player pl : org.bukkit.Bukkit.getOnlinePlayers()) {
                if (pl.hasMetadata("tutorial") || pl.hasMetadata("silencio")) {
                    continue;
                }
                pl.sendMessage(mensagem);
            }
        }
        return false;
    }
}