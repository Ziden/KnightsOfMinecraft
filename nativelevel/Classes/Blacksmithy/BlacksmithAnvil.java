/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Classes.Blacksmithy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.CustomPotion;
import nativelevel.Custom.Items.RecipeBook;
import nativelevel.Jobs;
import nativelevel.Jobs.Sucesso;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Lang.LangMinecraft;
import nativelevel.MetaShit;
import nativelevel.sisteminhas.KomSystem;
import nativelevel.RecipeBooks.BookTypes;
import nativelevel.bencoes.TipoBless;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.sisteminhas.XP;
import nativelevel.utils.Cooldown;
import nativelevel.utils.GeneralUtils;
import nativelevel.utils.LocUtils;
import nativelevel.utils.TitleAPI;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

/**
 *
 * @author User
 *
 */
public class BlacksmithAnvil extends KomSystem {

    public static HashSet<Item> flutuando = new HashSet<Item>();
    public static HashMap<String, List<Item>> quemFlutua = new HashMap<String, List<Item>>();

    @EventHandler
    public void itemSome(ItemDespawnEvent ev) {
        if (flutuando.contains(ev.getEntity())) {
            ev.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockBreak(BlockBreakEvent ev) {
        String loc = LocUtils.loc2str(ev.getBlock().getLocation());
        if (quemFlutua.containsKey(loc)) {
            clearItems(ev.getBlock());
        }
    }

    @EventHandler
    public void itemSome(PlayerPickupItemEvent ev) {
        if (flutuando.contains(ev.getItem())) {
            ev.setCancelled(true);
        }
    }

    public static void displayItem(Block cauld, ItemStack inHand, Vector offset) {
        ItemStack paraDropar = new ItemStack(inHand.getType(), 1, inHand.getDurability(), inHand.getData().getData());
        ItemMeta meta = paraDropar.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add("!");
        meta.setLore(lore);
        meta.setDisplayName(Jobs.rnd.nextInt(1000) + "");
        paraDropar.setItemMeta(meta);
        Item dropado = cauld.getWorld().dropItem(cauld.getLocation().add(offset), paraDropar);
        dropado.setVelocity(new Vector(0, 0.2, 0));
        flutuando.add(dropado);
        String local = LocUtils.loc2str(cauld.getLocation());
        if (!quemFlutua.containsKey(local)) {
            ArrayList<Item> lista = new ArrayList<Item>();
            quemFlutua.put(local, lista);
        }
        List<Item> lista = quemFlutua.get(local);
        lista.add(dropado);
        KoM.debug("Dropei item pra tolo ver");
    }

    public static void clearItems(Block b) {
        String local = LocUtils.loc2str(b.getLocation());
        if (quemFlutua.containsKey(local)) {
            List<Item> lista = quemFlutua.get(local);
            for (Item u : lista) {
                flutuando.remove(u);
                u.remove();
            }
        }
    }

    public static boolean hasTheRightIngredients(ItemStack[] ingredients, List<ItemStack> ingredientsIn) {
        int ingredientsRight = 0;
        HashSet<ItemStack> using = new HashSet<ItemStack>();
        for (ItemStack ingred : ingredients) {
            KoM.debug("Vendo se tem " + ingred.getType().name());
            CustomItem cuIngred = CustomItem.getItem(ingred);
            for (ItemStack used : ingredientsIn) {
                KoM.debug("Comparando com um q eu usei " + used.getType().name());
                if (cuIngred != null) {
                    CustomItem cuUsed = CustomItem.getItem(used);
                    if (!using.contains(used) && cuUsed != null && cuUsed.getName().equalsIgnoreCase(cuIngred.getName())) {
                        ingredientsRight += 1;
                        KoM.debug("Acertei ingrediente " + ingred.toString());
                        using.add(used);
                    }
                } else {
                    if (!using.contains(used) && used.getType() == ingred.getType() && used.getData().getData() == ingred.getData().getData()) {
                        ingredientsRight += 1;
                        using.add(used);
                        KoM.debug("Acertei ingrediente");
                    }
                }

            }
        }
        if (ingredientsRight == 3) {
            return true;
        } else {
            return false;
        }
    }

    public Vector getLocal() {
        Vector teste;
        int rnd = Jobs.rnd.nextInt(4);
        if (rnd == 0) {
            return new Vector(0.1d, 1, 0.1d);
        } else if (rnd == 1) {
            return new Vector(0.1d, 1, 0.9d);
        } else if (rnd == 2) {
            return new Vector(0.9d, 1, 0.1d);
        } else {
            return new Vector(0.9d, 1, 0.9d);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void interage(PlayerInteractEvent ev) {
        if (ev.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.ANVIL && ev.getAction() == Action.RIGHT_CLICK_BLOCK) {

            ev.setCancelled(true);

            if (Jobs.getJobLevel(Jobs.Classe.Ferreiro, ev.getPlayer()) != Jobs.TipoClasse.PRIMARIA) {
                ev.getPlayer().sendMessage(ChatColor.RED + "Apenas ferreiros experientes sabem usar uma bigorna.");
                return;
            }

            if (ev.getPlayer().getLevel() < 10 && !ev.getPlayer().hasMetadata("funilzz")) {
                ev.getPlayer().sendMessage("§e§l[Dica] §aVocê pode retirar os items segurando shift e clicando.");
                MetaShit.setMetaObject("funilzz", ev.getPlayer(), true);
            }

            int itemstem = 0;

            Block bigorna = ev.getClickedBlock();

            if (bigorna.getRelative(BlockFace.UP).getType() != Material.AIR) {
                ev.getPlayer().sendMessage(ChatColor.RED + "O Bloco de cima da bigorna deve estar livre.");
                return;
            }

            if (ev.getPlayer().getItemInHand() == null || ev.getPlayer().getItemInHand().getType() == Material.AIR && !ev.getPlayer().hasMetadata("dicaitem") && ev.getPlayer().getLevel() < 20) {
                MetaShit.setMetaObject("dicaitem", bigorna, true);
                ev.getPlayer().sendMessage(ChatColor.RED + "Coloque um item na mão para colocar na bigorna ou segure shift para retirar items da bigorna ");
            }

            ItemStack inHand = ev.getPlayer().getItemInHand();

            List<ItemStack> ingredientsIn = new ArrayList<ItemStack>();

            if (bigorna.hasMetadata("ingredients")) {
                ingredientsIn = (List<ItemStack>) MetaShit.getMetaObject("ingredients", bigorna);
            }

            if (ingredientsIn.size() != itemstem) {

                itemstem = ingredientsIn.size();

            }

            if (ev.getPlayer().isSneaking()) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Você pegou os materiais"));
                ev.getPlayer().getInventory().addItem(ingredientsIn.toArray(new ItemStack[ingredientsIn.size()]));
                bigorna.removeMetadata("ingredients", KoM._instance);
                 bigorna.removeMetadata("hits", KoM._instance);
                if (bigorna.hasMetadata("mainitem")) {
                    ItemStack mainItem = (ItemStack) MetaShit.getMetaObject("mainitem", bigorna);
                    ev.getPlayer().getInventory().addItem(mainItem);
                }
                BlacksmithAnvil.clearItems(bigorna);
                bigorna.removeMetadata("mainitem", KoM._instance);
                return;
            }

            if (inHand != null && inHand.getType() != Material.AIR) {
                if (!bigorna.hasMetadata("mainitem")) {
                    ItemStack mainItem = new ItemStack(inHand);
                    mainItem.setAmount(1);
                    MetaShit.setMetaObject("mainitem", bigorna, mainItem);
                    ev.getPlayer().sendMessage(ChatColor.GREEN + "Voce colocou o item na bigorna.");
                    BlacksmithAnvil.displayItem(bigorna, inHand, new Vector(0.5, 1, 0.5));
                    if (ev.getPlayer().getItemInHand().getAmount() > 1) {
                        ev.getPlayer().getItemInHand().setAmount(ev.getPlayer().getItemInHand().getAmount() - 1);
                    } else {
                        ev.getPlayer().setItemInHand(null);
                    }
                    return;
                }

                /////////////////////////////////////////////
                // Anvil is not full, adding ingredient //
                /////////////////////////////////////////////
                if (itemstem < 3) {

                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Você colocou o material na bigorna !"));
                    if (inHand.getAmount() > 1) {
                        ingredientsIn.add(new ItemStack(inHand.getType(), 1, inHand.getData().getData()));
                    } else {
                        ingredientsIn.add(inHand);
                    }

                    // Dropando o item para display
                    displayItem(bigorna, inHand, this.getLocal());

                    MetaShit.setMetaObject("ingredients", bigorna, ingredientsIn);
                    if (ev.getPlayer().getItemInHand().getAmount() > 1) {
                        ev.getPlayer().getItemInHand().setAmount(ev.getPlayer().getItemInHand().getAmount() - 1);
                    } else {
                        ev.getPlayer().setItemInHand(null);
                    }
                } else {

                    ///////////////////////////////////////////////////////////////
                    // Anvil is full, making potion or gettin ingredients out //
                    ///////////////////////////////////////////////////////////////
                    if (ev.getPlayer().getItemInHand().getType() == Material.WRITTEN_BOOK) {
                        CustomItem ci = CustomItem.getItem(inHand);
                        if (ci == null || !(ci instanceof RecipeBook) || ((RecipeBook) ci).getBookType(inHand) != BookTypes.Ferraria) {
                            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Use um §2Livro de Receitas de Ferraria§4 para pegar o item ou click com shift para pegar os materiais novamente !"));
                            return;
                        }

                        BookMeta meta = (BookMeta) inHand.getItemMeta();
                        if (meta.getAuthor() == null || meta.getAuthor().equalsIgnoreCase("ninguem...")) {
                            meta.setAuthor(ev.getPlayer().getName());
                            inHand.setItemMeta(meta);
                        }

                        if (!meta.getAuthor().equalsIgnoreCase(ev.getPlayer().getName())) {
                            ev.getPlayer().sendMessage(ChatColor.RED + "Esse livro pertence a " + meta.getAuthor());
                            ev.setCancelled(true);
                            return;
                        }

                        RecipeBook book = (RecipeBook) ci;

                        //////////////////////////////////////////
                        // searching for the recipe in the book //
                        //////////////////////////////////////////
                        ItemStack mainItem = (ItemStack) MetaShit.getMetaObject("mainitem", bigorna);

                        List<String> recipesInBook = book.getRecipes(inHand);
                        int hitsPrecisa = 0;
                        CustomCrafting canMake = null;
                        boolean haveRecipe = false;
                        for (String recipe : recipesInBook) {
                            canMake = CustomCrafting.getItem(recipe);
                            ItemStack[] ingredients = canMake.getRecipe();
                            if (!this.hasTheRightIngredients(ingredients, ingredientsIn)) {
                                KoM.debug("Ingredientes diferentess");
                                continue;
                            } else {
                                if (mainItem.getType() != canMake.getItemPrincipal().getType()) {
                                    KoM.debug("Item era diferente");
                                    continue;
                                }
                                hitsPrecisa = canMake.getHammerHits();
                                haveRecipe = true;
                                break;
                            }
                        }
                        if (!haveRecipe) {
                            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce não sabe o que fazer com estes materiais..."));
                            return;
                        } else {

                            int hits = this.getHits(bigorna);

                            boolean danificou = false;

                            if (hits > hitsPrecisa) {

                                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Você bateu muito no item, danificando ele"));
                                ItemStack mainitem = (ItemStack) MetaShit.getMetaObject("mainitem", bigorna);
                                mainitem.setDurability((short) (mainitem.getDurability() + (mainitem.getType().getMaxDurability() / 4)));
                                if (mainitem.getDurability() < mainitem.getType().getMaxDurability()) {
                                    ev.getPlayer().getWorld().dropItem(bigorna.getLocation().add(0.5, 2, 0.5), mainitem);
                                }
                                bigorna.removeMetadata("ingredients", KoM._instance);
                                bigorna.removeMetadata("mainitem", KoM._instance);
                                bigorna.removeMetadata("hits", KoM._instance);
                                BlacksmithAnvil.clearItems(bigorna);

                                return;

                            } else if (hits < hitsPrecisa) {
                                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Você nao forjou suficientemente para conseguir modelar o item e apenas danificou ele"));
                                ItemStack mainitem = (ItemStack) MetaShit.getMetaObject("mainitem", bigorna);
                                mainitem.setDurability((short) (mainitem.getDurability() + (mainitem.getType().getMaxDurability() / 4)));

                                if (mainitem.getDurability() < mainitem.getType().getMaxDurability()) {
                                    ev.getPlayer().getWorld().dropItem(bigorna.getLocation().add(0.5, 2, 0.5), mainitem);
                                }
                                bigorna.removeMetadata("ingredients", KoM._instance);
                                bigorna.removeMetadata("mainitem", KoM._instance);
                                bigorna.removeMetadata("hits", KoM._instance);
                                BlacksmithAnvil.clearItems(bigorna);
                                return;
                            }

                            ///////////////////////
                            // Has made a craft //
                            ///////////////////////
                            // se o manolo tem a receita no livro e acertou tudo bonitinho
                            int minSkill = canMake.getMinimumSkill();

                            double smithySkill = ev.getPlayer().getLevel();
                            double expRatio = canMake.getExpRatio();

                            Sucesso sucesso = Jobs.hasSuccess((int) Math.round(minSkill), Jobs.Classe.Ferreiro, ev.getPlayer(), 0);

                            if (!sucesso.acertou) {

                                if (Jobs.rnd.nextInt(500) == 1) {

                                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce derrubou o material quente !"));
                                    bigorna.removeMetadata("ingredients", KoM._instance);
                                    bigorna.removeMetadata("mainitem", KoM._instance);
                                    bigorna.removeMetadata("hits", KoM._instance);
                                    BlacksmithAnvil.clearItems(bigorna);
                                    bigorna.getRelative(BlockFace.UP).setType(Material.LAVA);

                                } else {

                                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce falhou em forjar o item !"));
                                    ItemStack mainitem = (ItemStack) MetaShit.getMetaObject("mainitem", bigorna);
                                    mainitem.setDurability((short) (mainitem.getDurability() + (mainitem.getType().getMaxDurability() / 3)));

                                    if (mainitem.getDurability() < mainitem.getType().getMaxDurability()) {
                                        ev.getPlayer().getWorld().dropItem(bigorna.getLocation().add(0.5, 2, 0.5), mainitem);
                                    }
                                    bigorna.removeMetadata("ingredients", KoM._instance);
                                    bigorna.removeMetadata("mainitem", KoM._instance);
                                    bigorna.removeMetadata("hits", KoM._instance);
                                    BlacksmithAnvil.clearItems(bigorna);
                                }
                            } else {
                                int xp = XP.getExpPorAcao(minSkill);
                                xp *= expRatio;
                                XP.changeExp(ev.getPlayer(), xp, 1);
                                RankDB.addPontoCache(ev.getPlayer(), Estatistica.FERREIRO, 3);
                                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Você forjou o item !"));
                                ItemStack mainitem = (ItemStack) MetaShit.getMetaObject("mainitem", bigorna);
                                mainitem = canMake.aplica(mainitem);

                                ItemMeta metaa = mainitem.getItemMeta();
                                List<String> lore = metaa.getLore();
                                if (lore == null) {
                                    lore = new ArrayList<String>();
                                }
                                if(lore.size()> 1)
                                    lore.add(lore.size() - 1, ChatColor.GREEN + "Aprimorado por " + ChatColor.YELLOW + ev.getPlayer().getName());
                                else
                                    lore.add(ChatColor.GREEN + "Aprimorado por " + ChatColor.YELLOW + ev.getPlayer().getName());
                                metaa.setLore(lore);
                                mainitem.setItemMeta(metaa);

                                ev.getPlayer().getInventory().addItem(mainitem);
                                ev.getPlayer().updateInventory();
                                bigorna.removeMetadata("mainitem", KoM._instance);
                                bigorna.removeMetadata("hits", KoM._instance);
                                bigorna.removeMetadata("ingredients", KoM._instance);
                                BlacksmithAnvil.clearItems(bigorna);
                            }
                        }

                    } else if (ev.getPlayer().isSneaking()) {

                        ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Você pegou os materiais"));
                        ev.getPlayer().getInventory().addItem(ingredientsIn.toArray(new ItemStack[ingredientsIn.size()]));
                        ItemStack mainitem = (ItemStack) MetaShit.getMetaObject("mainitem", bigorna);
                        ev.getPlayer().getInventory().addItem(mainitem);
                        bigorna.removeMetadata("ingredients", KoM._instance);
                        bigorna.removeMetadata("mainitem", KoM._instance);
                        bigorna.removeMetadata("hits", KoM._instance);
                        BlacksmithAnvil.clearItems(bigorna);
                    } else {
                        ev.getPlayer().sendMessage(ChatColor.RED + L.m("Use um livro de Ferraria para pegar o item pronto !"));
                        return;
                    }

                }
            }

        } else if (ev.getAction() == Action.LEFT_CLICK_BLOCK && ev.getClickedBlock().getType() == Material.ANVIL) {

            KoM.debug("LEFT NA BIGORNA");

            if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType().name().contains("SPADE")) {

                ev.setCancelled(true);

                KoM.debug("com martelo");

                int hits = getHits(ev.getClickedBlock());

                if (Cooldown.isCooldown(ev.getPlayer(), "bigorna")) {
                    return;
                }

                if (ev.getClickedBlock().hasMetadata("mainitem")) {
                    List<ItemStack> items = getIngredientes(ev.getClickedBlock());

                    ItemStack mainitem = (ItemStack) MetaShit.getMetaObject("mainitem", ev.getClickedBlock());

                    if (mainitem.getType().getMaxDurability() > 0) {
                        if (items.size() == 0) {
                            if (mainitem.getDurability() == 0) {
                                ev.getPlayer().sendMessage(ChatColor.RED + "Esse item já está como novo.");
                            } else {
                                // se só tem o item, ta reparando
                                if (hits == 0) {
                                    ev.getPlayer().sendMessage(ChatColor.GREEN + "Voce ajeitou o item. Bata mais vezes para começar a reparação.");
                                } else {
                                    int dificuldade = 50;
                                    ItemStack custo = new ItemStack(Material.COAL, 1);

                                    if (mainitem.getType().name().contains("STONE")) {
                                        custo = new ItemStack(Material.STONE, 1);
                                        dificuldade = 35;
                                    } else if (mainitem.getType().name().contains("WOOD")) {
                                        custo = new ItemStack(Material.WOOD, 1);
                                        dificuldade = 25;
                                    } else if (mainitem.getType().name().contains("IRON") || mainitem.getType().name().contains("CHAIN")) {
                                        custo = new ItemStack(Material.IRON_INGOT, 1);
                                        dificuldade = 55;
                                    } else if (mainitem.getType().name().contains("GOLD")) {
                                        dificuldade = 55;
                                        custo = new ItemStack(Material.GOLD_INGOT, 1);
                                    } else if (mainitem.getType().name().contains("DIAMOND")) {
                                        dificuldade = 85;
                                        custo = new ItemStack(Material.DIAMOND, 1);
                                    }

                                    if (!KoM.inventoryContains(ev.getPlayer().getInventory(), custo)) {
                                        ev.getPlayer().sendMessage(ChatColor.RED + "Voce precisa de " + LangMinecraft.get().get(custo) + " para arrumar isso");
                                        return;
                                    }

                                    GeneralUtils.removeInventoryItems(ev.getPlayer().getInventory(), custo.getType(), 1, custo.getData().getData());

                                    short danoDoItem = mainitem.getDurability();
                                  
                                    dificuldade += mainitem.getEnchantments().size() * 12;
                                    int raridadeItem = CustomItem.getRaridadeItem(mainitem);
                                    if (raridadeItem == CustomItem.INCOMUM) {
                                        dificuldade += 10;
                                    }
                                    if (raridadeItem == CustomItem.RARO) {
                                        dificuldade += 20;
                                    }
                                    if (raridadeItem == CustomItem.EPICO) {
                                        dificuldade += 35;
                                    }

                                    TipoBless ativo = TipoBless.save.getTipo(ev.getPlayer());
                                    if (ativo != null && ativo == TipoBless.Reparar) {
                                        dificuldade -= 15;
                                    }

                                    Sucesso sucesso = Jobs.hasSuccess(dificuldade, Jobs.Classe.Ferreiro, ev.getPlayer(), 0);
                                    if (sucesso.acertou) {

                                        int qtoTira = mainitem.getType().getMaxDurability() / 28 - (ev.getPlayer().getLevel() / 4);
                                        danoDoItem += qtoTira;
                                        int faltam = (int) Math.floor(danoDoItem / qtoTira);
                                        ev.getPlayer().sendMessage(ChatColor.GREEN + "Você arrumou um pouco do item." + " Segure shift para pegar o item.");
                                        mainitem.setDurability(danoDoItem);
                                    } else {

                                        ev.getPlayer().sendMessage(ChatColor.RED + "Você acabou batendo errado no item e o danificou um pouco.");

                                        mainitem.setDurability((short) (mainitem.getDurability() + (mainitem.getType().getMaxDurability() / 2)));
                                        if (mainitem.getDurability() >= mainitem.getType().getMaxDurability()) {

                                            ev.getClickedBlock().removeMetadata("mainitem", KoM._instance);
                                            ev.getClickedBlock().removeMetadata("hits", KoM._instance);
                                            BlacksmithAnvil.clearItems(ev.getClickedBlock());
                                        }
                                    }

                                }

                            }
                        }

                    } else {
                        ev.getPlayer().sendMessage(ChatColor.RED + "Você não sabe como poderia arrumar ou melhorar esse item.");
                    }
                }

                ItemStack martelo = ev.getPlayer().getItemInHand();
                ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 0);
                ev.getPlayer().getWorld().playEffect(ev.getClickedBlock().getLocation(), Effect.LAVA_POP, 1);
                PlayEffect.play(VisualEffect.LAVA, ev.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), "num:2");

                String loc = LocUtils.loc2str(ev.getClickedBlock().getLocation());
                if (BlacksmithAnvil.quemFlutua.containsKey(loc)) {
                    List<Item> coisinhas = BlacksmithAnvil.quemFlutua.get(loc);
                    for (Item i : coisinhas) {
                        if (i != null && i.isValid()) {
                            i.setVelocity(new Vector(0, 0.2, 0));
                        }
                    }
                }

                KoM.efeitoBlocos(ev.getClickedBlock(), Material.IRON_BLOCK);
                Cooldown.setMetaCooldown(ev.getPlayer(), "bigorna", 1000);
                hits++;
                MetaShit.setMetaObject("hits", ev.getClickedBlock(), hits);
            }
        }
    }

    public List<ItemStack> getIngredientes(Block bigorna) {
        List<ItemStack> ingredientsIn = new ArrayList<ItemStack>();
        if (bigorna.hasMetadata("ingredients")) {
            ingredientsIn = (List<ItemStack>) MetaShit.getMetaObject("ingredients", bigorna);
        }
        return ingredientsIn;
    }

    public int getHits(Block bigorna) {
        if (bigorna.hasMetadata("hits")) {
            return (Integer) MetaShit.getMetaObject("hits", bigorna);
        } else {
            return 0;
        }
    }

}
