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
package nativelevel.Comandos;

import com.sk89q.worldedit.bukkit.selections.Selection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Classes.Farmer;
import nativelevel.Custom.Items.TeleportScroll;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Menu.netMenu;
import nativelevel.MetaShit;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.Attributes.Mana;
import nativelevel.Attributes.MenuAtributos;
import nativelevel.config.Config;
import nativelevel.integration.BungeeCordKom;
import nativelevel.spec.PlayerSpec;
import nativelevel.sisteminhas.BookPortal;
import nativelevel.utils.BungLocation;
import nativelevel.sisteminhas.Html;
import nativelevel.sisteminhas.Reseter;
import nativelevel.sisteminhas.XP;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Doar implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            if (args.length == 0) {
                p.sendMessage("§4§lComo Funciona ?");
                p.sendMessage("§aVoce digita §4§l/doar <esmeraldas>§a e doa esmeraldas para o Deus Jabu.");
                p.sendMessage("§a§lQuando alguem ativar um Double XP Jabu o recompensará com tudo que foi doado !");
                p.sendMessage("§6§lSaldo do Deus Jabu: §e§l"+Config.getDoacoes()+" Moedas");
            } else if (args.length == 1) {
                try {
                    int qtd = Integer.valueOf(args[0]);
                    if(qtd < 32) {
                       p.sendMessage("§a§lA Humildade faz o homem, porém não o Deus. Jabu só aceita mais de 32 Moedas");
                       return true; 
                    }
                    if(!ClanLand.econ.has(p.getName(), qtd)) {
                        p.sendMessage("§a§lJabu gosta do seu amor no coração, mas as Moedas que voce quer doar voce não tem :(");
                        return true;
                    }
                    ClanLand.econ.withdrawPlayer(p.getName(), qtd);
                    int doacoes = Config.getDoacoes()+qtd;
                    Config.setDoacao(doacoes);
                    p.sendMessage(ChatColor.GREEN+"Voce fez sua doação a Jabu.");
                    for(Player pl : Bukkit.getOnlinePlayers()) {
                        if(pl!=p) {
                            pl.sendMessage("§a§l"+p.getName()+" foi a §6§l/doar§9§l "+qtd+" para Deus Jabu.");
                        }
                    }
                } catch (Exception e) {
                    p.sendMessage("§aUse §4§l/doar <Esmeraldas>§a.");
                }
            }
        }
        return true;
    }

}
