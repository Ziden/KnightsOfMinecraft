/*

 */
package nativelevel.ComandosNovos;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Carlos André Feldmann Júnior
 */
public abstract class Comando extends Command {

    public void showSubCommands(CommandSender cs) {
          cs.sendMessage(ChatColor.YELLOW+"|________________oO "+getName()+" Oo_____________");
             for(SubCmd cmd : subs) {
                 if(cmd.type!=CommandType.OP) {
                     cs.sendMessage(ChatColor.YELLOW+"|"+ChatColor.GREEN+" - /"+getName()+" "+cmd.cmd);
                 } else if(cs.isOp()) {
                      cs.sendMessage(ChatColor.YELLOW+"|"+ChatColor.BLUE+" - /"+getName()+" "+cmd.cmd+" (Op)");
                 }
             }
              cs.sendMessage(ChatColor.YELLOW+"|_______________________________________");
    }
    
    protected List<SubCmd> subs = new ArrayList<SubCmd>();
    
    CommandType tipo;
    public String permission = null;
    public String cmd;

    public abstract void usouComando(CommandSender cs, String[] args);
    private CommandExecutor exe = null;

    protected Comando(String name, CommandType c) {
        super(name);
        this.cmd = name;
        tipo = c;
    }

    @Override
    public boolean execute(CommandSender cs, String commandLabel, String[] strings) {
        if (exe != null) {

            if (commandLabel.equalsIgnoreCase(getName()) || getAliases().contains(commandLabel)) {

                if (tipo == CommandType.CONSOLE && cs instanceof Player) {
                    cs.sendMessage("§aComando só pode ser executado no console!");
                    return true;
                }
                if ((tipo == CommandType.OP || tipo == CommandType.PLAYER) && !(cs instanceof Player)) {
                    cs.sendMessage("Comando só pode ser executado em jogo");
                    return false;
                }
                if (tipo == CommandType.PERMISSION) {
                    if (permission != null && !cs.hasPermission(permission)) {
                        cs.sendMessage("§cVocê não tem permissão para esse comando!");
                        return false;
                    }
                }
                if (tipo == CommandType.OPCONSOLE) {
                    if (cs instanceof Player) {
                        if (!cs.isOp()) {
                            cs.sendMessage("§cVocê não tem permissão para esse comando!");
                            return true;
                        }
                    }

                }
                if (tipo == CommandType.OP && !((Player) cs).isOp()) {
                    cs.sendMessage("§cVocê não tem permissão para esse comando!");
                    return false;
                }

                usouComando(cs, strings);

            }
            exe.onCommand(cs, this, commandLabel, strings);
        }
        return false;
    }

    public void setExecutor(CommandExecutor exe) {
        this.exe = exe;
    }

    public static enum CommandType {

        PLAYER, OP, CONSOLE, TODOS, OPCONSOLE, PERMISSION;

    }
}
