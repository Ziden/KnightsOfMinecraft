/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Crafting;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Classes.Blacksmithy.Blacksmith;
import nativelevel.Custom.CustomItem;
import nativelevel.CustomEvents.BeginCraftEvent;
import nativelevel.CustomEvents.FinishCraftEvent;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Lang.LangMinecraft;
import nativelevel.Listeners.GeneralListener;
import nativelevel.Menu.Menu;
import nativelevel.Equipment.WeaponDamage;
import nativelevel.Classes.Blacksmithy.CustomCrafting;
import nativelevel.Classes.Blacksmithy.RecipeLoader;
import nativelevel.Jobs.TipoClasse;
import nativelevel.bencoes.TipoBless;
import nativelevel.gemas.Raridade;
import nativelevel.sisteminhas.XP;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Furnace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author User
 *
 */
public class CraftEvents {

    public static String getCorChance(double chance) {
        String cor = null;
        if (chance < 25) {
            cor = ChatColor.RED + "§l";
        } else if (chance > 75) {
            cor = ChatColor.AQUA + "§l";
        } else if (chance > 50) {
            cor = ChatColor.GREEN + "§l";
        } else if (chance >= 25) {
            cor = ChatColor.YELLOW + "§l";
        }
        return cor;
    }

    public static String getNomeChance(double chance) {
        ChatColor cor = null;
        if (chance == 0) {
            return "Impossível";
        } else if (chance < 25) {
            return "Muito Difícil";
        } else if (chance > 75) {
            return "Facinho";
        } else if (chance > 50) {
            return "Tranquilo";
        } else {
            return "Difícil";
        }
    }

    public static void tiraDescricao(ItemStack ss, Player p) {
        ItemMeta meta = ss.getItemMeta();
        if (meta.getLore() != null && meta.getLore().size() > 5) {
            KoM.debug("Tirando lore");
            if (meta.getLore().get(meta.getLore().size() - 1).contains("Clique para Craftar")) {
                KoM.debug("tem lore pra tirar");
                int ultimaLinhaValida = meta.getLore().size() - 6;
                // nothing more on items lore
                if (ultimaLinhaValida == 0) {

                    List<String> lore = new ArrayList<String>();
                    /*
                     lore.add("");
                     lore.add(ChatColor.GREEN + "Criado por " + ChatColor.YELLOW + p.getName());
                     lore.add("");
                     */
                    meta.setLore(lore);
                } else {
                    KoM.debug("Ultima linha: " + ultimaLinhaValida);
                    ArrayList<String> novaLore = new ArrayList<String>();
                    /*
                     novaLore.add("");
                     novaLore.add(ChatColor.GREEN + "Criado por " + ChatColor.YELLOW + p.getName());
                     novaLore.add("");
                     */
                    for (int x = 0; x < ultimaLinhaValida; x++) {
                        novaLore.add(meta.getLore().get(x));
                    }
                    meta.setLore(novaLore);
                }
            }
        }
        ss.setItemMeta(meta);
    }

    public static void addDescricao(Player p, ItemStack ss, Craftable craft) {
        ItemMeta meta = ss.getItemMeta();
        if (meta.getLore() != null && meta.getLore().size() > 4) {
            if (meta.getLore().get(meta.getLore().size() - 1).contains("Clique para Craftar")) {
                return;
            }
        }
        TipoClasse tipo = Jobs.getJobLevel(craft.classe, p);
        double chance = Jobs.getFinalChangeToSucess(craft.diff, p.getLevel(), tipo, p);
        if (meta.getDisplayName() == null) {
            String nome = LangMinecraft.get().get(ss);
            Raridade raridade = Raridade.Comum;
            if (craft.diff > 99) {
                raridade = Raridade.Raro;
            } else if (craft.diff > 80) {
                raridade = Raridade.Incomum;
            }
            meta.setDisplayName(raridade.cor + "♦ " + ChatColor.WHITE + nome);
        }

        List<String> lore = null;
        if (meta.getLore() != null) {
            lore = new ArrayList<String>(meta.getLore());
        } else {
            lore = new ArrayList<String>();
        }
        lore.add("§a§lDados de Craft:");
        lore.add("§a| §2Classe: §6" + craft.classe.name());
        lore.add("§a| §2Dificuldade: §6" + Jobs.getNomeDificuldade(craft.diff));
        // lore.add(getCorChance(chance) + getNomeChance(chance) + " §avoce criar isso");
        if (p.getLevel() - 15 > craft.diff || p.getLevel() + 20 < craft.diff) {
            lore.add("§a| §2Isto dará menos XP pois não é do seu nivel");
        } else {
            lore.add("§a| §2XP: §6" + (formatter.format(craft.expRatio * XP.getExpPorAcao(craft.diff))));
        }
        lore.add(getCorChance(chance) + getNomeChance(chance) + " §avoce criar isso");
        lore.add("§a§l[ Clique para Craftar ]");
        meta.setLore(lore);
        ss.setItemMeta(meta);
    }

    private static NumberFormat formatter = new DecimalFormat("#0.00");

    public static void addInfoItem(ItemStack ss) {
        if (ss == null) {
            return;
        }
        ItemMeta meta = ss.getItemMeta();
        if (meta == null) {
            return;
        }
        Material m = ss.getType();
        if (meta.getLore() == null || meta.getLore().size() == 0) {
            List<String> lore = new ArrayList<String>();
            if (m == Material.CARROT_ITEM) {
                lore.add(ChatColor.GREEN + "Boa comida para Ladinos");
            } else if (m == Material.COOKED_BEEF) {
                lore.add(ChatColor.GREEN + "Boa comida para Lenhadores");
            } else if (m == Material.CAKE) {
                lore.add(ChatColor.GREEN + "Otima comida para salas de guildas.");
            } else if (m == Material.COOKED_CHICKEN) {
                lore.add(ChatColor.GREEN + "Boa comida para Engenheiros");
            } else if (m == Material.COOKIE) {
                lore.add(ChatColor.GREEN + "Boa comida para Magos");
            } else if (m == Material.GRILLED_PORK) {
                lore.add(ChatColor.GREEN + "Boa comida para Paladinos");
            } else if (m == Material.COOKED_RABBIT) {
                lore.add(ChatColor.GREEN + "Boa comida para Alquimistas");
            } else if (m == Material.MELON) {
                lore.add(ChatColor.GREEN + "Boa comida para Mineradores");
            } else if (m == Material.COOKED_FISH && ss.getData().getData() == 1) {
                lore.add(ChatColor.GREEN + "Boa comida para Fazendeiros");
            } else if (m == Material.PUMPKIN_PIE) {
                lore.add(ChatColor.GREEN + "Boa comida para Ferreiros");
            } else if (m == Material.MUSHROOM_SOUP) {
                lore.add(ChatColor.GREEN + "Comida nutritiva.");
                lore.add(ChatColor.GREEN + "Cura Venenos e Slow");
            } else if (m == Material.RABBIT_STEW) {
                lore.add(ChatColor.GREEN + "Comida nutritiva.");
                lore.add(ChatColor.GREEN + "Cura Fraqueza");
            }
            meta.setLore(lore);
            ss.setItemMeta(meta);
            KoM.debug("ADDEI LORE");
        }
    }

    public static void prepareCraft(PrepareItemCraftEvent ev) {

        if (ev.getInventory() == null || ev.getInventory().getResult() == null) {
            return;
        }

        if (ev.getInventory().getResult().getType().name().contains("SHULKER_BOX") || ev.getInventory().getResult().getType() == Material.TIPPED_ARROW) {
            ev.getInventory().setResult(new ItemStack(Material.AIR));
        }

        if (ev != null && ev.getRecipe() != null && ev.getRecipe().getResult() != null && ev.getRecipe().getResult().getType() != null && (ev.getRecipe().getResult().getType() == Material.TIPPED_ARROW || ev.getRecipe().getResult().getType() == Material.SPECTRAL_ARROW)) {
            ev.getInventory().setResult(new ItemStack(Material.AIR));
        }

        if (ev.getInventory().getResult().getType().name().contains("INGOT") && ev.getInventory().getType() != InventoryType.FURNACE) {
            return;
        }

        if (ev.getView().getPlayer() != null && ev.getView().getPlayer().getType() == EntityType.PLAYER && ev.getRecipe() != null && ev.getRecipe().getResult() != null) {

            Player craftando = (Player) ev.getView().getPlayer();

            ItemStack craft = ev.getRecipe().getResult();

            for (CustomCrafting receita : RecipeLoader.customItems.values()) {
                craft = receita.aplicaNoCraftNormal(craft);
            }

            ev.getInventory().setResult(craft);

        }

        if (ev != null && ev.getRecipe() != null && ev.getInventory().getResult() != null) {
            ItemStack modded = WeaponDamage.checkForMods(ev.getInventory().getResult());

            ev.getInventory().setResult(modded);

            addInfoItem(ev.getInventory().getResult());

            if (ev.getInventory().getResult() != null && ev.getInventory().getResult().getType() != Material.AIR) {
                Craftable craft = CraftCache.getCraftable(ev.getInventory().getResult());
                if (craft != null) {
                    addDescricao((Player) ev.getView().getPlayer(), ev.getInventory().getResult(), craft);
                }
            }
        }

    }

    public static void forjaLingote(final InventoryClickEvent event) {

        Player p = ((Player) event.getWhoClicked());

        boolean acaoPerigosa = (event.getAction() == InventoryAction.CLONE_STACK || event.getAction() == InventoryAction.NOTHING || event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || event.getAction() == InventoryAction.HOTBAR_SWAP) || ((event.getAction() == InventoryAction.DROP_ONE_SLOT || event.getAction() == InventoryAction.PICKUP_HALF) && event.getInventory().getName().equalsIgnoreCase("container.furnace"));
        if ((event.isShiftClick() || acaoPerigosa) && (event.getInventory().getName().equalsIgnoreCase("container.crafting") || event.getInventory().getName().equalsIgnoreCase("container.furnace") || event.getInventory().getName().equalsIgnoreCase("Enchant") || event.getInventory().getName().equalsIgnoreCase("container.brewing"))) {
            event.setCancelled(true);
            p.sendMessage(ChatColor.GOLD + L.m("Voce nao consegue mover items desta maneira ! "));
            p.updateInventory();
            return;
        }

        KoM.debug("Forjando");

        Craftable craft = CraftCache.getCraftable(event.getInventory().getItem(event.getSlot()));

        if (craft == null) {
            return;
        }

        int acertos = 0;

        if (event.getCursor() != null && event.getCursor().getAmount() > 63) {
            return;
        }

        int diff = craft.diff;

        TipoBless ativo = TipoBless.save.getTipo(p);
        if (ativo == TipoBless.Forjar) {
            diff -= 5;
        }

        Jobs.Sucesso sucesso = null;
        for (int suc = 0; suc < event.getCurrentItem().getAmount(); suc++) {
            sucesso = Jobs.hasSuccess(diff, craft.classe, p, 0);
            if (sucesso.acertou) {
                acertos += 1;
            }
        }

        String pctFinal = "";
        if (p.hasMetadata("mostra%")) {
            pctFinal = sucesso.chanceBase + "%";
            if (sucesso.chanceBase < 25) {
                pctFinal = "§4§l" + pctFinal;
            } else if (sucesso.chanceBase >= 50) {
                pctFinal = "§a§l" + pctFinal;
            } else if (sucesso.chanceBase >= 50) {
                pctFinal = "§e§l" + pctFinal;
            }
            if (sucesso.chanceBonus != 0) {
                if (sucesso.chanceBonus > 0) {
                    pctFinal += " §e(+" + sucesso.chanceBonus + "%)";
                } else if (sucesso.chanceBonus < 0) {
                    pctFinal += " §4(" + sucesso.chanceBonus + "%)";
                }
            }
            pctFinal = "§6[" + pctFinal + "§6]";
        }

        String nomeItem = LangMinecraft.get().get(event.getCurrentItem());
        PlayEffect.play(VisualEffect.SOUND, event.getWhoClicked().getLocation(), "type:anvil");
        if (acertos > 0) {
            p.sendMessage(ChatColor.AQUA + KoM.tag + " " + ChatColor.GOLD + L.m("Voce craftou %  !", acertos + " " + nomeItem + " com " + ChatColor.UNDERLINE + ChatColor.AQUA + "sucesso " + pctFinal));
            event.getInventory().setItem(event.getSlot(), new ItemStack(event.getInventory().getItem(event.getSlot()).getType(), acertos));
        } else if (acertos == 0) {
            p.sendMessage(ChatColor.AQUA + KoM.tag + " §c" + L.m("Voce §4§nfalhou§c  em craftar " + nomeItem + " " + pctFinal));
            event.getInventory().setItem(event.getSlot(), new ItemStack(Material.COAL, 1));
        }

        int exp = XP.getExpPorAcao(craft.diff);

        if (craft.classe == Jobs.Classe.Ferreiro && ativo == TipoBless.Forjar) {
            exp *= 2;
        }
        XP.changeExp(p, exp * acertos, 1);
    }

    public static boolean criado(ItemStack ss) {
        if (ss.hasItemMeta()) {
            ItemMeta meta = ss.getItemMeta();
            List<String> lore = meta.getLore();
            if (lore != null) {
                for (String s : lore) {
                    if (s == null) {
                        continue;
                    }
                    if (s.contains("Criado Por")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void craft(CraftItemEvent ev) {

        final Craftable craftable = CraftCache.getCraftable(ev.getCurrentItem());

        if (craftable == null) {
            return;
        }

        if (ev.getInventory().getResult().getType().name().contains("INGOT") && ev.getInventory().getType() != InventoryType.FURNACE) {
            ev.setCancelled(true);
            return;
        }

        final Player p = (Player) ev.getWhoClicked();

        tiraDescricao(ev.getCurrentItem(), p);

        ///////////
        // TO-DO //
        ///////////
        // MAKE IT WORK WITH SHIFT+CLICK !!!!!!!!
        if (ev.isShiftClick()) {
            ev.setCancelled(true);
            p.updateInventory();
            return;
        }

        if (ev.getWhoClicked().getItemOnCursor() != null && ev.getWhoClicked().getItemOnCursor().getType() != Material.AIR) {
            if (ev.getAction().name().contains("DROP")) {
                ev.setCancelled(true);
                p.sendMessage(ChatColor.RED + "Voce nao pode fazer isto no crafting");
                return;
            }

            if (!GeneralUtils.isEqual(ev.getWhoClicked().getItemOnCursor(), ev.getCurrentItem())) {
                ev.setCancelled(true);
                p.sendMessage(ChatColor.RED + "Largue o item do mouse para craftar o item");
                return;
            }

            if (CraftEvents.criado(ev.getCurrentItem())) {
                List<String> lore = new ArrayList<String>();
                ItemMeta meta = ev.getCurrentItem().getItemMeta();
                meta.setLore(lore);
                ev.getCurrentItem().setItemMeta(meta);
                ev.setCurrentItem(new ItemStack(Material.AIR));
            }

        }

        boolean acaoPerigosa = (ev.getAction() == InventoryAction.CLONE_STACK || ev.getAction() == InventoryAction.NOTHING || ev.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || ev.getAction() == InventoryAction.HOTBAR_SWAP) || ((ev.getAction() == InventoryAction.DROP_ONE_SLOT || ev.getAction() == InventoryAction.PICKUP_HALF) && ev.getInventory().getName().equalsIgnoreCase("container.furnace"));
        if ((ev.isShiftClick() || acaoPerigosa) && (ev.getInventory().getName().equalsIgnoreCase("container.crafting") || ev.getInventory().getName().equalsIgnoreCase("container.furnace") || ev.getInventory().getName().equalsIgnoreCase("Enchant") || ev.getInventory().getName().equalsIgnoreCase("container.brewing"))) {
            ev.setCancelled(true);
            p.sendMessage(ChatColor.GOLD + L.m("Voce nao consegue mover items desta maneira ! "));
            p.updateInventory();
            return;
        }

        CraftingInventory inventory = ev.getInventory();
        ItemStack contents[] = inventory.getContents();

        BeginCraftEvent beginCraftEvent = new BeginCraftEvent(p, inventory, ev.getCurrentItem(), ev.getCursor(), craftable);
        Bukkit.getServer().getPluginManager().callEvent(beginCraftEvent);

        //////////////////////
        // DEFAULT CRAFTING //
        //////////////////////
        final ItemStack fazendo = ev.getCurrentItem();
        int mySkill = p.getLevel();
        Jobs.Classe classe = craftable.classe;
        Jobs.TipoClasse tipoClasse = Jobs.getJobLevel(classe, p);
        double expRatio = craftable.expRatio;
        int amt = fazendo.getAmount();
        int finalAmt = 0;
        int bonus = 0;
        Jobs.Sucesso sucesso = null;
        bonus += beginCraftEvent.getChanceBonus();
        for (int x = 0; x < amt; x++) {
            sucesso = Jobs.hasSuccess(craftable.diff, craftable.classe, p, bonus);
            if (sucesso.acertou) {
                finalAmt += 1;
            }
        }

        if (craftable.m == Material.WOOD) {
            if (tipoClasse == tipoClasse.SEGUNDARIA && finalAmt > 3) {
                finalAmt = 3;
            } else if (tipoClasse == tipoClasse.NADA && finalAmt > 1) {
                finalAmt = 1;
            }
        }

        CustomItem customItem = CustomItem.getItem(fazendo);
        String nomeItem = LangMinecraft.get().get(fazendo);

        String pctFinal = "";
        if (p.hasMetadata("mostra%")) {
            pctFinal = sucesso.chanceBase + "%";
            if (sucesso.chanceBase < 25) {
                pctFinal = "§4§l" + pctFinal;
            } else if (sucesso.chanceBase >= 50) {
                pctFinal = "§a§l" + pctFinal;
            } else if (sucesso.chanceBase >= 50) {
                pctFinal = "§e§l" + pctFinal;
            }
            if (sucesso.chanceBonus != 0) {
                if (sucesso.chanceBonus > 0) {
                    pctFinal += " §e(+" + sucesso.chanceBonus + "%)";
                } else if (sucesso.chanceBonus < 0) {
                    pctFinal += " §4(" + sucesso.chanceBonus + "%)";
                }
            }
            pctFinal = "§6[" + pctFinal + "§6]";
        }

        if (finalAmt > 0) {
            double ratioXp = 1;
            double xp = XP.getExpPorAcao(craftable.diff);
            xp *= craftable.expRatio;
            xp *= beginCraftEvent.getMultiplyExp();
            if (tipoClasse == Jobs.TipoClasse.SEGUNDARIA) {
                xp /= 2;
            }
            if (tipoClasse == Jobs.TipoClasse.NADA) {
                xp /= 10;
            }

            if (mySkill < craftable.diff - 15) {
                ratioXp = 0.20;
            }
            if (mySkill - 20 > craftable.diff) {
                ratioXp = 0.5;
            }

            xp *= finalAmt;
            XP.changeExp(p, xp, ratioXp);
            p.sendMessage(KoM.tag + "§a Voce criou " + finalAmt + " " + nomeItem + " com " + ChatColor.UNDERLINE + ChatColor.AQUA + "sucesso " + pctFinal);

            ev.getCurrentItem().setAmount(finalAmt);

            FinishCraftEvent finishCraftEvent = new FinishCraftEvent(p, inventory, ev.getCurrentItem(), ev.getCursor(), craftable);
            Bukkit.getServer().getPluginManager().callEvent(finishCraftEvent);

            //SUUCEEEEEEEEEEEEEEESSO
            if (customItem == null) {
                List<String> lore = finishCraftEvent.getResult().getItemMeta().getLore() == null ? new ArrayList<String>() : new ArrayList<String>(finishCraftEvent.getResult().getItemMeta().getLore());
                lore.add("");
                lore.add(ChatColor.GREEN + "Criado por " + ChatColor.YELLOW + p.getName());
                lore.add("");
                ItemMeta meta = finishCraftEvent.getResult().getItemMeta();
                meta.setLore(lore);
                ev.getCurrentItem().setItemMeta(meta);
                KoM.debug("lore setada");
            } else {
                List<String> lore = finishCraftEvent.getResult().getItemMeta().getLore() == null ? new ArrayList<String>() : new ArrayList<String>(finishCraftEvent.getResult().getItemMeta().getLore());
                lore.add(lore.size() - 2, "");
                lore.add(lore.size() - 2, ChatColor.GREEN + "Criado por " + ChatColor.YELLOW + p.getName());
                lore.add(lore.size() - 2, "");
                ItemMeta meta = finishCraftEvent.getResult().getItemMeta();
                meta.setLore(lore);
                ev.getCurrentItem().setItemMeta(meta);
            }

        } else {
            ev.setCancelled(true);
            p.sendMessage(KoM.tag + "§c Voce §4§nfalhou§c ao tentar criar " + amt + " " + nomeItem + " " + pctFinal);
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1.2F, 1);
            for (int i = 1; i < contents.length; i++) {
                Material item = contents[i].getType();
                if (!item.equals(Material.AIR)) {
                    int amount;
                    if ((amount = contents[i].getAmount()) > 1) {
                        contents[i].setAmount(amount - 1);
                    } else {
                        inventory.setItem(i, new ItemStack(Material.AIR));
                    }
                }
            }
        }
    }

}
