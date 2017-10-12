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
package nativelevel.Custom.Items;

import java.util.HashSet;
import nativelevel.Custom.CustomItem;
import nativelevel.Lang.L;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class LogoutTrap extends CustomItem {

    public static HashSet<String> trapeados = new HashSet<String>();

    public LogoutTrap() {
        super(Material.STICK, L.m("Logout Trap"), L.m("Nao deixa alvo deslogar"), CustomItem.INCOMUM);
    }

    public limpaTrap getLimpaTrap(String nome) {
        return new limpaTrap(nome);
    }

    @Override
    public boolean onItemInteract(Player p) {
        return true;
    }

    public class limpaTrap implements Runnable {

        String nome;

        public limpaTrap(String nome) {
            this.nome = nome;
        }

        @Override
        public void run() {
            trapeados.remove(nome);
            Player p = Bukkit.getPlayer(nome);
            if (p != null) {
                p.sendMessage(ChatColor.GOLD + L.m("O Efeito da Logout Trap se foi !"));
            }
        }
    }
}
