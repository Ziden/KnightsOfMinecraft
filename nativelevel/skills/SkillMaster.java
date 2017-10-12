package nativelevel.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Ziden
 */
public class SkillMaster {

    public static void load() {
        Paladino.load();
        Alquimista.load();
        Lenhador.load();
        Mago.load();
        Minerador.load();
        Engenheiro.load();
        Ladino.load();
        Ferreiro.load();
        Fazendeiro.load();
    }

    public static HashMap<String, List<Skill>> skills = new HashMap<String, List<Skill>>();

    public static boolean temSkill(Player p, String job, String nome) {
        List<Skill> lista = skills.get(job.toLowerCase());
        for (Skill s : lista) {
            if (s.getNome().equalsIgnoreCase(nome)) {
                if (p.getLevel() >= s.getNivel()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        KoM.log.info("Nao encontrei a skill " + nome + " do job " + job);
        return false;
    }

    public static void abreSkills(Player p) {
        Inventory primarias = Bukkit.createInventory(p, 9, "Minhas Skills");
        List<String> listaPrimarias = Jobs.getPrimarias(p);
        int slot = 2;
        for (String primaria : listaPrimarias) {
            int skillId = Menu.getId(primaria);
            Material icone = Menu.getDesenho(skillId);
            ItemStack ss = new ItemStack(icone, 1);
            ItemMeta meta = ss.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + primaria);
            ss.setItemMeta(meta);
            primarias.setItem(slot, ss);
            slot = 6;
        }
        p.openInventory(primarias);
    }

    public static void invClick(InventoryClickEvent ev) {
        if (ev != null && ev.getClickedInventory() != null && (ev.getClickedInventory().getName() != null && ev.getClickedInventory().getName().equalsIgnoreCase("Vendo Skills")) || (ev.getClickedInventory().getTitle() != null && ev.getClickedInventory().getTitle().equalsIgnoreCase("Vendo Skills"))) {
            ev.setCancelled(true);
        }
        if (ev.getClickedInventory() != null && ev.getClickedInventory().getName().equalsIgnoreCase("Minhas Skills") || ev.getClickedInventory().getTitle().equalsIgnoreCase("Minhas Skills")) {
            ev.setCancelled(true);
            if (ev.getCurrentItem() != null && ev.getCurrentItem().getItemMeta().getDisplayName() != null) {
                String clicado = ChatColor.stripColor(ev.getCurrentItem().getItemMeta().getDisplayName());
                List<Skill> lista = skills.get(clicado.toLowerCase());
                ev.getWhoClicked().closeInventory();
                Inventory skills = Bukkit.createInventory(ev.getWhoClicked(), InventoryType.CHEST, "Vendo Skills");
                Player p = (Player) ev.getWhoClicked();
                for (Skill s : lista) {
                    if (p.getLevel() >= s.getNivel()) {
                        ItemStack skill = new ItemStack(Material.EMERALD_BLOCK, 1);
                        ItemMeta meta = skill.getItemMeta();
                        String nome = s.getNome();
                        List<String> lore = new ArrayList<String>(s.getLore());
                        if (s.isSkillDeCraft()) {
                            int nivelDaSkill = p.getLevel() / 2;
                            nome += " lvl " + ChatColor.YELLOW + nivelDaSkill;
                            lore.add(ChatColor.GREEN + "Bonus Atual: " + ChatColor.YELLOW + nivelDaSkill + "%");
                        }
                        meta.setDisplayName(ChatColor.GREEN + nome);
                        meta.setLore(lore);
                        skill.setItemMeta(meta);
                        skills.addItem(skill);
                    } else {
                        ItemStack skill = new ItemStack(Material.REDSTONE_BLOCK, 1);
                        ItemMeta meta = skill.getItemMeta();
                        meta.setDisplayName(ChatColor.RED + "... SEGREDO ...");
                        List<String> lore = new ArrayList<String>();
                        lore.add(ChatColor.RED + "Aprende no nivel " + s.getNivel());
                        meta.setLore(lore);
                        skill.setItemMeta(meta);
                        skills.addItem(skill);
                    }
                }
                p.openInventory(skills);
            }
        }
    }

    public static Skill aprendeuSkill(Player p, String job, int lvl) {
        if (KoM.debugMode) {
            KoM.log.info("Buscando skills do job " + job);
            KoM.log.info(skills.keySet().toString());
        }
        List<Skill> lista = skills.get(job.toLowerCase());
        for (Skill s : lista) {
            if (s.getNivel() == lvl) {
                return s;
            }
        }
        return null;
    }

}
