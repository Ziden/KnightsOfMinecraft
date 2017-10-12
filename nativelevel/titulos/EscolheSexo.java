package nativelevel.titulos;

import nativelevel.playerboolean.Stage;
import nativelevel.playerboolean.Stage.PredefinedStages;
import nativelevel.playerboolean.StageDB;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Carlos André Feldmann Júnior
 * 
 */
public class EscolheSexo implements Listener {

    private static String menuname = "§8Genero";

    public static void open(Player p) {

        Inventory i = Bukkit.createInventory(null, 9, menuname);
        ItemStack homi = new ItemStack(Material.WOOD_PICKAXE);
        ItemStack muie = new ItemStack(Material.RED_ROSE);
        ItemUtils.SetItemName(muie, "§d§lFeminino");
        ItemUtils.SetItemName(homi, "§9§lMasculino");
        i.setItem(3, homi);
        i.setItem(5, muie);
        // i.setItem(8, ItemUtils.CreateStack(Material.REDSTONE_COMPARATOR, (byte) 0, 1, "§c§lSair"));
        p.openInventory(i);
    }

    @EventHandler
    public void click(InventoryClickEvent ev) {
        Inventory i = ev.getClickedInventory();
        Player p = (Player) ev.getWhoClicked();
        if (i == null) {
            return;
        }
        if (i.getTitle().equalsIgnoreCase(menuname) || i.getName().equalsIgnoreCase(menuname) || ev.getInventory().getTitle().equalsIgnoreCase(menuname)) {
            ev.setCancelled(true);
            ItemStack item = ev.getCurrentItem();
            if (item != null) {
                if (item.hasItemMeta()) {
                    if (item.getItemMeta().hasDisplayName()) {

                        Sexo s = null;
                        if (item.getType() == Material.WOOD_PICKAXE) {
                            s = Sexo.HOMEM;
                        } else if (item.getType() == Material.RED_ROSE) {
                            s = Sexo.MULHER;
                        } else {
                            p.closeInventory();
                            return;
                        }
                        if (TituloDB.getPlayerData(p).getSexo() == s) {
                            p.sendMessage("§cVocê já é do sexo " + s.name());
                            p.closeInventory();
                            return;
                        }
                        final String name = s == Sexo.HOMEM ? "Masculino" : "Feminino";
                        boolean jaTem = StageDB.getPlayerStage(p).stagesCompleted.contains(PredefinedStages.ESCOLHEUSEXO);
                        if (jaTem) {

                            p.sendMessage(ChatColor.RED+"Voce ja escolheu seu gênero");

                        } else {
                            TituloDB.PData data = TituloDB.getPlayerData(p);
                            data.setSexo(s);
                            Stage.completa(p, PredefinedStages.ESCOLHEUSEXO);
                            p.sendMessage("§a§lVocê alterou seu gênero para " + name);
                            p.closeInventory();
                            String title = data.getTitulo();
                            Titulos.update(p, title, data.getCortitulo());
                        }

                    }
                }
            }
        }

    }

}
