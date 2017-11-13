/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.spec;

import java.util.ArrayList;
import java.util.List;
import nativelevel.KoM;
import nativelevel.Menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author venturus
 *
 */

public enum PlayerSpec implements Listener {

    /*
    // MAGO
     Bruxo     - Congelar Conjuração - Faz uma magia e congela ela, pra usar depois sem invocar os elementos.
     Sabio     - Bomba Gravitacional 
    
     // LADINO
     Ranger    - Super Atirador - atira flecha sem carregar o arco
     Assassino - Shadowjump + 2 adagas
    
     // PALADINO
     Guardião  - Toma 1/4 do dano dos aliados pra proteger eles
     Crusador  - Combo Armas (espada -> machado -> pá) (rotação de armas)
    
     // ENG
     Fuzileiro - Tiro Carregado (tipo epica, com timing)
     Inventor  - Autodispenser novo permanente de gente bem feito
    
     // ALQUIMISTA (REVER...)
     Cientista - Armadura Revestida - Causa slow e poison a atacantes físicos
     Granadeiro - Taca uma tnt muito melhor
    
     // FERREIRO
     Armeiro - nao gasta durabilidade de items
     Macador - hits com pá tacam fogo & 2 pas
    
     // MINERADOR
     Pedreiro = arremeça pedras (cada pedra tem um efeito diferente)
     Explorador = joga tochas de fogo
    
     // LENHADOR
     Barbaro = arremeça machados
     Carrasco = ganha vida ao acertar epica
    
     // FAZENDEIRO (REVER)
     Ceifador - Arremeça uma foice
     Druida = pode virar um papagaio e voar ou pousar no ombro de alguem
     */
    
    Guardiao("Paladino", "-Mana Escudo -Dano Fisico +Armadura -Stamina Escudada", 1), // ok
    Crusador("Paladino", "+Mana Escudo +Dano Fisico", 2), // ok
    Assassino("Ladino", "+Dano Fisico -Dano Arco -Armadura -Mana Bomba Fumaça", 1), // ok
    Ranger("Ladino", "+Dano Arco -Dano Fisico +Mana Bomba Fumaça", 2), // ok 
    Sabio("Mago", "+Inteligencia +Custo Mana", 1), // ok
    Sacerdote("Mago", "-Dano Magico +Magias de Suporte -Custo Mana", 2), // ok
    Fuzileiro("Engenheiro", "+Dano Bonka +Custo Mana -Custo Polvora", 1), // ok
    Inventor("Engenheiro", "+Todas Chances -Dano Bonka -Custo Bonka", 2), // ok 
    Cientista("Alquimista", "+Chance Craft Pocoes, Foice causa Wither", 1), // ok
    Envenenador("Alquimista", "+Foice tambem enfraquece", 2), // ok
    Forjador("Ferreiro", "+Chance Item Excepcional -Dano Fisico", 1), // ok
    Macador("Ferreiro", "+Dano Pás -Custo Golpe Esmagador", 2), // ok
    Desarmador("Minerador", "+Duracao Disarm +Custo Disarm", 1), // ok
    Explorador("Minerador", "+Chance Achar Minerio -Duracao Disarm +Custo Disarm", 2), // ok
    Barbaro("Lenhador", "+Velocidade Epica +Custo Epica +Cansaço Epica", 1), // ok
    Carrasco("Lenhador", "+Epica rouba vida instant", 2), // ok
    Ceifador("Fazendeiro", "+Dano Foice -Resist Fogo", 1), // ok
    Pescador("Fazendeiro", "-Cooldown Vara de Pescar +Resist Fogo", 2); // ok

    public String classe;
    public String desc;
    public int id = 1;

    private PlayerSpec(String classe, String desc, int id) {
        this.classe = classe;
        this.desc = desc;
        this.id = id;
    }

    public static PlayerSpec getSpec(String classe, int indice) {
        for (PlayerSpec spec : PlayerSpec.values()) {
            if (spec.classe.equalsIgnoreCase(classe) && spec.id == indice) {
                return spec;
            }
        }
        return null;
    }

    public static boolean temSpec(Player p, PlayerSpec spec) {
        int[] intSpecs = KoM.database.getSpecs(p.getUniqueId().toString());
        int indice = Menu.getId(spec.classe);
        if (intSpecs[indice] == spec.id) {
            return true;
        }
        return false;
    }

    @EventHandler
    public void clica(InventoryClickEvent ev) {
        if (ev.getInventory().getName().equalsIgnoreCase("Especializacoes") || ev.getInventory().getName().equalsIgnoreCase("Especializacoes")) {
            ev.setCancelled(true);
            Player p = (Player) ev.getWhoClicked();
            if (ev.getCurrentItem() == null || ev.getCurrentItem().getType() == Material.AIR) {
                return;
            }
            PlayerSpec spec = PlayerSpec.valueOf(ChatColor.stripColor(ev.getCurrentItem().getItemMeta().getDisplayName()));
            p.sendMessage(ChatColor.GREEN + "Voce como " + spec.classe + " se especializou como " + spec.name());
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (pl != p) {
                    pl.sendMessage(ChatColor.AQUA + p.getName() + " se especializou como " + spec.name());
                }
            }
            int[] specs = KoM.database.getSpecs(p.getUniqueId().toString());
            specs[Menu.getId(spec.classe)] = spec.id;
            if (getSpecs(p).size() == 0) {
                KoM.database.cadastraSpec(p.getUniqueId().toString());
            }
            KoM.database.atualizaSpecs(p.getUniqueId().toString(), specs);
            p.closeInventory();
        }
    }

    public static void abreSpecSelect(Player p) {
        int slot = 2;
        Inventory i = Bukkit.createInventory(p, 9, "Especializacoes");
        List<PlayerSpec> possiveis = getPossiveis(p);
        for (PlayerSpec possivel : possiveis) {
            ItemStack item = new ItemStack(Material.BOOK, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + possivel.name());
            List<String> lore = new ArrayList<String>();
            lore.add(ChatColor.YELLOW + "Especializacao de " + possivel.classe);
            lore.add(ChatColor.YELLOW + possivel.desc);
            lore.add("!");
            meta.setLore(lore);
            item.setItemMeta(meta);
            i.setItem(slot, item);
            slot++;
        }
        p.openInventory(i);
    }

    public static List<PlayerSpec> getSpecs(Player p) {
        List<PlayerSpec> specs = new ArrayList<PlayerSpec>();
        int[] intSpecs = KoM.database.getSpecs(p.getUniqueId().toString());
        for (int index = 0; index < intSpecs.length; index++) {
            int s = intSpecs[index];
            if (s != 0) {
                // ele tem uma especialização
                String nomeClasse = Menu.getNome(index);
                PlayerSpec spek = getSpec(nomeClasse, s);
                specs.add(spek);
            }
        }
        return specs;
    }

    public static List<PlayerSpec> getPossiveis(Player p) {
        List<PlayerSpec> possiveis = new ArrayList<PlayerSpec>();
        List<PlayerSpec> jaTem = getSpecs(p);
        int[] classes = KoM.database.getSkills(p.getUniqueId().toString());
        for (int classe = 0; classe < classes.length; classe++) {
            int nivel = classes[classe];
            if (nivel == 2) {
                // primario
                String nomeClasse = Menu.getNome(classe);
                // ja tem spec dessa classe ?
                boolean temClasse = false;
                for (PlayerSpec tem : jaTem) {
                    if (tem.classe.equalsIgnoreCase(nomeClasse)) {
                        temClasse = true;
                        break;
                    }
                }
                if (!temClasse) {
                    for (PlayerSpec sp : PlayerSpec.values()) {
                        if (sp.classe.equalsIgnoreCase(nomeClasse)) {
                            possiveis.add(sp);
                        }
                    }
                }

            }
        }
        return possiveis;
    }

}
