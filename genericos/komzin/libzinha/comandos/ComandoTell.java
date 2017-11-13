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
public class ComandoTell implements CommandExecutor {
    
    public ComandoTell() {
    }
    //§6Chega de encantamento ruim!
    //§dUse o comando §f/desencantar
    //§dpara remover os encantamentos.
    //§ddo seu item.

    
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        Player p = (Player) cs;
        if (p.hasMetadata("Silenciado")) {
            p.sendMessage("§f[§4!§f]§8 Voce esta silenciado. Talves voce tenha falado algo inadequado no chat!");
            return true;
        }
        PlayerInfo info = InstaMCLibKom.getinfo(p);
        if (args.length < 1) {
            if (info.talkingTo == null) {
                p.sendMessage(ChatColor.YELLOW + "Digite " + ChatColor.WHITE + "/tell" + ChatColor.RED + " <quem> <mensagem> " + ChatColor.YELLOW + "Para iniciar o chat privado !");
            } else {
                info.talkingTo = null;
                p.sendMessage(ChatColor.YELLOW + "Agora voce esta falando no local !");
                return true;
            }
            return true;
        }
        
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("on")) {
                p.sendMessage(ChatColor.GREEN + "Tell ligado !");
                info.ignoreTell = false;
                return true;
            } else if (args[0].equalsIgnoreCase("off")) {
                p.sendMessage(ChatColor.GREEN + "Tell desligado !");
                info.ignoreTell = true;
                return true;
            }
        }
        
        String player = args[0];
        
        Player target = Bukkit.getPlayer(player);
        if (target == null) {
            p.sendMessage(ChatColor.RED + "Nick nao encontrado.");
            return true;
        }
        PlayerInfo InfoTarget = InstaMCLibKom.getinfo(target);
        if (InfoTarget.ignoreTell == true && target.isOp()) {
            p.sendMessage(ChatColor.RED + "Nick nao encontrado.");
            return false;
        }
        if (InfoTarget.ignoreTell == true && !p.isOp()) {
            p.sendMessage(ChatColor.RED + "Este jogador esta ocupado.");
            return false;
        }
        
        if (args.length == 1) {
            p.sendMessage(ChatColor.YELLOW + "Iniciando conversa privada com " + ChatColor.WHITE + target.getName() + ChatColor.YELLOW + ".");
            info.talkingTo = target.getName();
        } else {
            StringBuffer str = new StringBuffer();
            StringBuffer strFrom = new StringBuffer();
            
            strFrom.append(ChatColor.DARK_AQUA + "Para " + target.getName() + ": " + ChatColor.AQUA);
            str.append(ChatColor.DARK_AQUA + "De " + p.getName() + ": " + ChatColor.AQUA);
            for (int x = 1; x < args.length; x++) {
                str.append(args[x] + " ");
                strFrom.append(args[x] + " ");
            }
          
            target.sendMessage(str.toString());
            p.sendMessage(strFrom.toString());
            InfoTarget.lastPlayerMessage = p.getName();
        }
        return true;
    }
    
}
