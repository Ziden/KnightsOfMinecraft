package nativelevel.Comandos;

import nativelevel.Equipment.Generator.EquipGenerator;
import nativelevel.KoM;
import nativelevel.gemas.Raridade;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Ziden
 */
public class CmdGerador implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (cmnd.getName().equalsIgnoreCase("gerador")) {
            if (!cs.isOp()) {
                return false;
            }
            if (args.length != 2) {
                cs.sendMessage("�bUse /gerador {Numero} [Player]");
                return false;
            }
            Player p = Bukkit.getPlayerExact(args[1]);
            if (p == null) {
                cs.sendMessage("�bJogador selecionado invalido!");
                return false;
            }
            try {
                int a = Integer.valueOf(args[0]).intValue();
                if ((a > 5) || (a < 1)) {
                    cs.sendMessage("�6Use um numero de 1 a 5 como argumento!");
                    cs.sendMessage("�aItem Incomum:1,�9Item Raro:2,�5Item �pico:3,�6Item Lendario:4,�cItem Unico:5");
                    return false;
                }
                Raridade r = Raridade.Comum;
                if (a == 1) {
                    r = Raridade.Incomum;
                } else if (a == 2) {
                    r = Raridade.Raro;
                } else if (a == 3) {
                    r = Raridade.Epico;
                } else if (a == 4) {
                    r = Raridade.Lendario;
                }

                ItemStack gerado = EquipGenerator.gera(r, 100);
                p.getInventory().addItem(gerado);
                p.sendMessage(KoM.tag + "" + ChatColor.GREEN + "Voce ganhou " + gerado.getItemMeta().getDisplayName() + "  !");
            } catch (NumberFormatException ex) {
                cs.sendMessage("�6Use um numero de 1 a 5 como argumento!");
                cs.sendMessage("�aItem Incomum:1,�9Item Raro:2,�5Item �pico:3,�6Item Lendario:4,�cItem Unico:5");
                return false;
            }
        }
        return false;
    }

}
