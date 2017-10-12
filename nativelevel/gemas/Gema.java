/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.gemas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nativelevel.Jobs;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Gema {

   // Amarela(4, "§E", new String[]{"+1 Regeneracao"}, new String[]{"+2 Regeneracao"}),
    Laranja(1, "§6", new String[]{"+3% Chance Critico", "+1 Dano Critico"}, new String[]{"+7% Chance Critico", "+5% Dano Critico"}),
   // Rosa(6, "§D", new String[]{"+1 Dano Magico"}, new String[]{"+3 Dano Magico"}),
    Aqua(3, "§B", new String[]{"+3% Dano Distancia"}, new String[]{"+8% Dano Distancia"}),
    Preta(15, "§1", new String[]{"+1 Tempo Stun"}, new String[]{"+4 Tempo Stun"}),
    Vermelha(14, "§C", new String[]{"+2% Dano Fisico"}, new String[]{"+8% Dano Fisico"}),
    Roxa(2, "§5", new String[]{"+1 Penetracao Armadura"}, new String[]{"+4 Penetracao Armadura"}),
    Branca(0, "§f", new String[]{"+1 Vida"}, new String[]{"+2 Vida"}),
    Verde(5, "§A", new String[]{"+1 Armadura"}, new String[]{"+4 Armadura"});

    public static String iconeSlot = "⁤"; // só aparece na resource pack
    public int id;
    public String cor;
    public String[] loreNormal;
    public String[] loreRara;

    private Gema(int idCor, String cor, String[] loreNormal, String[] loreRara) {
        this.id = idCor;
        this.loreNormal = loreNormal;
        this.loreRara = loreRara;
        this.cor = cor;
    }

    public static Gema getGemaRandom(ItemStack ss) {
        return Gema.values()[Jobs.rnd.nextInt(Gema.values().length)];
    }

    public static Gema getGema(ItemStack ss) {
        if (ss.getType() != Material.STAINED_GLASS) {
            return null;
        }
        ItemMeta meta = ss.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null || lore.size() == 0) {
            return null;
        }
        if (!lore.get(0).contains("Gema")) {
            return null;
        }
        for (Gema g : Gema.values()) {
            if ((byte) g.id == ss.getData().getData()) {
                return g;
            }
        }
        return null;
    }

    public static ItemStack gera(Gema gem, Raridade r) {
        ItemStack gema = new ItemStack(Material.STAINED_GLASS, 1,(short)0, (byte)gem.id);
        int qtd = 1;
        ItemMeta meta = gema.getItemMeta();
        List<String> lore = meta.getLore();
        if(lore==null)
            lore = new ArrayList<String>();
        lore.add("§9§lIsto e uma Gema " + gem.cor + "§l " + gem.name());
        lore.add(" ");
        lore.add("§9§lColoque isto em um item");
        lore.add("§9§lQue tenha um encaixe " + gem.cor + "§l " + gem.name());
        lore.add("  ");
        lore.add("§6Raridade: " + r.cor + "" + r.name());
        meta.setLore(lore);
        String raridade = ChatColor.BLUE + "♦ ";
        if (r == Raridade.Epico) {
            raridade = ChatColor.LIGHT_PURPLE + "♦ ";
        }
        meta.setDisplayName(raridade + gem.cor + "§l Gema " + gem.name());
        gema.setItemMeta(meta);
        return gema;
    }

    public static Raridade getRaridade(ItemStack gema) {
        ItemMeta meta = gema.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore != null) {
            for (String s : lore) {
                if (s.contains("§6Raridade")) {
                    return Raridade.valueOf(ChatColor.stripColor(s.split(":")[1]).trim());
                }
            }
        }
        return Raridade.Comum;
    }

    public static void addGemaToItem(ItemStack ss, Gema g, ItemStack gemaItem) {
        ItemMeta meta = ss.getItemMeta();
        List<String> lore = meta.getLore();
        Raridade rar = getRaridade(gemaItem);
        String novaLore = "§9[X] Gema " + g.cor + g.name() + rar.cor + " " + rar.name() + "§9";
        int indexSlot = -1;
        if (lore != null) {
            for (String s : lore) {
                if (s.contains(iconeSlot)) {
                    String[] split = s.split("~");
                    String ultimo = split[split.length - 1].replace("]", "").trim();
                    Gema cor = Gema.valueOf(ultimo);
                    // achei o socket
                    if (cor == g) {
                        indexSlot = lore.indexOf(s);
                        break;
                    }
                }
            }
            // tirando o slot
            if (indexSlot != -1) {
                lore.set(indexSlot, novaLore);
            }
            meta.setLore(lore);
            ss.setItemMeta(meta);
            // adicionando os stats
            lore.add(ChatColor.GREEN + "~");
            if (rar == Raridade.Epico) {
                lore.addAll(Arrays.asList(g.loreRara));
            } else {
                lore.addAll(Arrays.asList(g.loreNormal));
            }
            meta.setLore(lore);
             ss.setItemMeta(meta);
        }
    }

     public static boolean temSockets(ItemStack ss) {
        ItemMeta meta = ss.getItemMeta();
        if (meta == null) {
            return false;
        }
        List<String> lore = meta.getLore();
        if (lore != null) {
            for (String s : lore) {
                if (s.contains(iconeSlot) || s.contains("[X]")) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static List<Gema> getSocketsLivres(ItemStack ss) {
        List<Gema> sockets = new ArrayList<Gema>();
        ItemMeta meta = ss.getItemMeta();
        if (meta == null) {
            return sockets;
        }
        List<String> lore = meta.getLore();
        if (lore != null) {
            for (String s : lore) {
                if (s.contains(iconeSlot)) {
                    String[] split = s.split("~");
                    String ultimo = split[split.length - 1].replace("]", "").trim();
                    Gema cor = Gema.valueOf(ultimo);
                    sockets.add(cor);
                }
            }
        }
        return sockets;
    }

    public static void addSocket(ItemStack item, Gema g) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        if(lore==null)
            lore = new ArrayList<String>();
        int index = lore.indexOf("      ");
        lore.add(index + 1, g.cor + "§l" + iconeSlot + " [Encaixe~" + g.name() + "]");
        lore.add(index + 2, "     ");
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

}
