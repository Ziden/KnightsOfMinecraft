/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Classes.Alchemy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.PotionExtract;
import nativelevel.Custom.Items.RecipeBook;
import nativelevel.Jobs;
import nativelevel.Jobs.Sucesso;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.MetaShit;
import nativelevel.Custom.CustomPotion;
import nativelevel.Jobs.TipoClasse;
import nativelevel.sisteminhas.KomSystem;
import nativelevel.RecipeBooks.BookTypes;
import nativelevel.bencoes.TipoBless;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.sisteminhas.XP;
import nativelevel.utils.Cooldown;
import nativelevel.utils.GeneralUtils;
import nativelevel.utils.LocUtils;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
public class AlchemyCauldron extends KomSystem {

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

    public static void displayItem(Block cauld, ItemStack inHand) {
        ItemStack paraDropar = new ItemStack(inHand.getType(), 1, inHand.getDurability(), inHand.getData().getData());
        ItemMeta meta = paraDropar.getItemMeta();
        meta.setDisplayName(Jobs.rnd.nextInt(1000) + "");
        List<String> lore = new ArrayList<String>();
        lore.add("!");
        meta.setLore(lore);
        paraDropar.setItemMeta(meta);
        Item dropado = cauld.getWorld().dropItem(cauld.getLocation().add(0.5, 1.1, 0.5), paraDropar);
        dropado.setVelocity(new Vector(0, 1, 0));
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
            lista.clear();
            quemFlutua.remove(local);
        }
    }

    // SEGURAR SHIFT PRA POR NO CALDEIRAO
    public static boolean hasTheRightIngredients(ItemStack[] ingredients, List<ItemStack> ingredientsIn) {
        int ingredientsRight = 0;
        HashSet<ItemStack> using = new HashSet<ItemStack>();
        for (ItemStack ingred : ingredients) {
            CustomItem cuIngred = CustomItem.getItem(ingred);
            for (ItemStack used : ingredientsIn) {
                KoM.debug("vendo " + used.getType().name());
                if (cuIngred != null) {
                    KoM.debug("tem ingrediente customItem " + cuIngred.getName());
                    CustomItem cuUsed = CustomItem.getItem(used);
                    if (cuUsed != null) {
                        KoM.debug("Achei cu usando " + cuUsed.getName());
                    }
                    if (!using.contains(used) && cuUsed != null && cuUsed.getName().equalsIgnoreCase(cuIngred.getName())) {
                        ingredientsRight += 1;
                        using.add(used);
                        KoM.debug("ingred ok " + cuUsed.getName());
                    }
                } else {
                    if (!using.contains(used) && used.getType() == ingred.getType() && used.getData().getData() == ingred.getData().getData()) {
                        ingredientsRight += 1;
                        using.add(used);
                        KoM.debug("ingred ok " + used.getType().name());
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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void interage(PlayerInteractEvent ev) {
        // if (ev.getHand() == EquipmentSlot.OFF_HAND) {
        //      return;
        //  }

        if (Cooldown.isCooldown(ev.getPlayer(), "cauld")) {
            //ev.setCancelled(true);
            return;
        }

        Cooldown.setMetaCooldown(ev.getPlayer(), "cauld", 200);

        ItemStack inHand = ev.getPlayer().getInventory().getItemInMainHand();

        EquipmentSlot slot = ev.getHand();

        if (ev.getHand() == EquipmentSlot.OFF_HAND) {
            inHand = ev.getPlayer().getInventory().getItemInOffHand();
        }

        if (inHand.getType() == Material.AIR && ev.getHand() == EquipmentSlot.OFF_HAND) {
            slot = EquipmentSlot.HAND;
            inHand = ev.getPlayer().getInventory().getItemInMainHand();
        }

        if (ev.getAction() == Action.RIGHT_CLICK_BLOCK && inHand != null && inHand.getType() != Material.AIR && ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.CAULDRON) {
            ev.setCancelled(true);
            TipoClasse tipoClasse = Jobs.getJobLevel(Jobs.Classe.Alquimista, ev.getPlayer());
            if (tipoClasse == Jobs.TipoClasse.NADA) {
                ev.getPlayer().sendMessage(ChatColor.RED + "Apenas alquimistas experientes sabem usar um caldeirão.");
                return;
            }

            if (ev.getClickedBlock().getRelative(BlockFace.DOWN).getType() != Material.FIRE) {
                ev.getPlayer().sendMessage(ChatColor.RED + "Você precisa colocar fogo em baixo do caldeirão para aquece-lo.");
                return;
            }

            if (!ev.getPlayer().isSneaking()) {
                ev.getPlayer().sendMessage(ChatColor.RED + "Você precisa segurar shift para poder colocar ingredientes no caldeirão.");
                return;
            }

            if (ev.getPlayer().getLevel() < 10 && !ev.getPlayer().hasMetadata("funilz")) {
                ev.getPlayer().sendMessage("§e§l[Dica] §aVocê pode usar um funil para retirar ingredientes do caldeirão.");
                MetaShit.setMetaObject("funilz", ev.getPlayer(), true);
            }

            int waterLevel = ev.getClickedBlock().getData();
            List<ItemStack> ingredientsIn = new ArrayList<ItemStack>();

            Block cauld = ev.getClickedBlock();
            if (cauld.hasMetadata("ingredients")) {
                ingredientsIn = (List<ItemStack>) MetaShit.getMetaObject("ingredients", cauld);
            }
            if (ingredientsIn.size() != waterLevel) {
                waterLevel = ingredientsIn.size();
                cauld.setData((byte) ingredientsIn.size());
            }

            KoM.debug("CAULD MEIO");

            /////////////////////////////////////////////
            // Cauldron is not full, adding ingredient //
            /////////////////////////////////////////////
            if (waterLevel < 3) {

                KoM.debug("WATER LOW");

                if (inHand.getType() == Material.HOPPER) {
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Você pegou os ingredientes"));
                    ev.getPlayer().getInventory().addItem(ingredientsIn.toArray(new ItemStack[ingredientsIn.size()]));
                    cauld.removeMetadata("ingredients", KoM._instance);
                    AlchemyCauldron.clearItems(cauld);
                    cauld.setData((byte) 0);
                    return;
                }

                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Você colocou o ingrediente no caldeirão !"));
                if (inHand.getAmount() > 1) {
                    CustomItem ci = CustomItem.getItem(inHand);
                    if (ci == null) {
                        ingredientsIn.add(new ItemStack(inHand.getType(), 1, inHand.getData().getData()));
                    } else {
                        ingredientsIn.add(ci.generateItem());
                    }
                } else {
                    ingredientsIn.add(inHand);
                }

                // Dropando o item para display
                displayItem(cauld, inHand);

                KoM.efeitoBlocos(ev.getClickedBlock(), Material.LAPIS_BLOCK);

                cauld.setData((byte) ingredientsIn.size());
                MetaShit.setMetaObject("ingredients", cauld, ingredientsIn);
                if (inHand.getAmount() > 1) {
                    inHand.setAmount(inHand.getAmount() - 1);
                } else {
                    if (slot == EquipmentSlot.OFF_HAND) {
                        ev.getPlayer().getInventory().setItemInOffHand(null);
                    } else {
                        ev.getPlayer().setItemInHand(null);
                    }
                }
            } else {

                ///////////////////////////////////////////////////////////////
                // Cauldron is full, making potion or gettin ingredients out //
                ///////////////////////////////////////////////////////////////
                if (inHand.getType() == Material.WRITTEN_BOOK) {
                    CustomItem ci = CustomItem.getItem(inHand);
                    if (ci == null || !(ci instanceof RecipeBook) || ((RecipeBook) ci).getBookType(inHand) != BookTypes.Alquimia) {
                        ev.getPlayer().sendMessage(ChatColor.RED + L.m("Use um §2Livro de Receitas de Alquimia§4 para pegar a poção ou um Funil para pegar os ingredientes novamente !"));
                        return;
                    }

                    BookMeta meta = (BookMeta) inHand.getItemMeta();
                    if (meta.getAuthor() == null || meta.getAuthor().equalsIgnoreCase("ninguem...")) {
                        meta.setAuthor(ev.getPlayer().getName());
                        inHand.setItemMeta(meta);
                        if (slot == EquipmentSlot.OFF_HAND) {
                            ev.getPlayer().getInventory().setItemInOffHand(inHand);
                        } else {
                            ev.getPlayer().setItemInHand(inHand);
                        }
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
                    List<String> recipesInBook = book.getRecipes(inHand);
                    CustomPotion canMake = null;
                    boolean haveRecipe = false;
                    for (String recipe : recipesInBook) {
                        canMake = CustomPotion.getItem(recipe);
                        ItemStack[] ingredients = canMake.getRecipe();
                        if (!this.hasTheRightIngredients(ingredients, ingredientsIn)) {
                            continue;
                        } else {
                            haveRecipe = true;
                            break;
                        }
                    }
                    if (!haveRecipe) {
                        ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce não sabe nenhuma receita com estes ingredientes..."));
                        return;
                    } else {

                        if (!GeneralUtils.hasItem(ev.getPlayer().getInventory(), Material.GLASS_BOTTLE, 1, (byte) 0)) {
                            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Você precisa de uma garrafa vazia para colocar a poção !"));
                            return;
                        }

                        ///////////////////////
                        // Has made a potion //
                        ///////////////////////
                        int minSkill = canMake.getMinimumSkill();

                        double alchemySkill = ev.getPlayer().getLevel();
                        double expRatio = canMake.getExpRatio();

                        TipoBless tipo = TipoBless.save.getTipo(ev.getPlayer());
                        if (tipo != null && tipo == TipoBless.Alquimia) {
                            expRatio += 1;
                            minSkill -= 5;
                        }

                        Sucesso sucesso = Jobs.hasSuccess((int) Math.round(minSkill), Jobs.Classe.Alquimista, ev.getPlayer(), 0);

                        if (!sucesso.acertou) {

                            if (Jobs.rnd.nextInt(200) == 1) {
                                ev.getPlayer().sendMessage(ChatColor.RED + L.m("A poção explodiu !"));
                                cauld.removeMetadata("ingredients", KoM._instance);
                                AlchemyCauldron.clearItems(cauld);
                                cauld.setData((byte) 0);
                                double explosionDamage = minSkill / 5;
                                ev.getPlayer().damage(explosionDamage);
                                ev.getPlayer().getWorld().playEffect(ev.getPlayer().getLocation(), Effect.EXPLOSION_LARGE, 0);
                            } else {
                                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce falhou em criar a poção !"));
                                if (cauld.hasMetadata("ingredients")) {
                                    ingredientsIn = (List<ItemStack>) MetaShit.getMetaObject("ingredients", cauld);
                                    for (ItemStack ss : ingredientsIn) {
                                        if (ss != null && ss.getType().name().contains("BUCKET")) {
                                            ev.getPlayer().getInventory().addItem(new ItemStack(Material.BUCKET));
                                        }
                                    }
                                }
                                cauld.removeMetadata("ingredients", KoM._instance);
                                AlchemyCauldron.clearItems(cauld);
                                cauld.setData((byte) 0);
                            }
                        } else {
                            int xp = XP.getExpPorAcao(minSkill) * 5;
                            xp *= expRatio;
                            
                            if(tipoClasse==TipoClasse.SEGUNDARIA)
                                xp = xp / 3;
                            
                            XP.changeExp(ev.getPlayer(), xp, 1);
                            RankDB.addPontoCache(ev.getPlayer(), Estatistica.ALQUIMISTA, 1);

                            if (cauld.hasMetadata("ingredients")) {
                                ingredientsIn = (List<ItemStack>) MetaShit.getMetaObject("ingredients", cauld);
                                for (ItemStack ss : ingredientsIn) {
                                    if (ss != null && ss.getType().name().contains("BUCKET")) {
                                        ev.getPlayer().getInventory().addItem(new ItemStack(Material.BUCKET));
                                    }
                                }
                            }

                            GeneralUtils.removeInventoryItems(ev.getPlayer().getInventory(), Material.GLASS_BOTTLE, 1, (byte) 0);
                            ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Você criou o extrato da poção !"));
                            ItemStack extract = ((PotionExtract) CustomItem.getItem(PotionExtract.class)).createPotionExtract(canMake);
                            ev.getPlayer().getInventory().addItem(extract);
                            cauld.removeMetadata("ingredients", KoM._instance);
                            AlchemyCauldron.clearItems(cauld);
                            cauld.setData((byte) 0);
                        }
                    }

                } else if (inHand.getType() == Material.HOPPER) {
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Você pegou os ingredientes"));
                    ev.getPlayer().getInventory().addItem(ingredientsIn.toArray(new ItemStack[ingredientsIn.size()]));
                    cauld.removeMetadata("ingredients", KoM._instance);
                    AlchemyCauldron.clearItems(cauld);
                    cauld.setData((byte) 0);
                } else {
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Use um livro de receitas de alquimia para pegar a poção ou um funil para pegar novamente os ingredientes !"));
                    return;
                }

            }

        }
    }

}
