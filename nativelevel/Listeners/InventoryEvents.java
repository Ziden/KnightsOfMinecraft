package nativelevel.Listeners;

import java.util.ArrayList;
import java.util.List;
import me.asofold.bpl.simplyvanish.SimplyVanish;
import me.asofold.bpl.simplyvanish.config.VanishConfig;
import nativelevel.Auras.Aura;
import nativelevel.Classes.Alchemy.Alchemist;
import nativelevel.Classes.Blacksmithy.Blacksmith;
import nativelevel.Classes.Farmer;
import nativelevel.Classes.Mage.Wizard;
import nativelevel.Crafting.CraftEvents;
import nativelevel.Custom.Buildings.Portal;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.CapaInvisvel;
import nativelevel.Custom.Items.Encaixe;
import nativelevel.Custom.Items.Runa;
import nativelevel.CustomEvents.BeginCraftEvent;
import nativelevel.CustomEvents.FinishCraftEvent;
import nativelevel.ExecutaSkill;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.MetaShit;
import nativelevel.Attributes.Mana;
import nativelevel.CFG;
import nativelevel.ComandosNovos.list.KomSubs.CmdOE;
import nativelevel.ComandosNovos.list.KomSubs.CraftSubs.CmdCraftCheck;
import nativelevel.ComandosNovos.list.KomSubs.HarvestSubs.CmdHarvestCheck;
import nativelevel.Custom.Items.BussolaMagica;
import nativelevel.skills.SkillMaster;
import nativelevel.sisteminhas.ClanLand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.BlockFace;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Ziden
 */
public class InventoryEvents implements Listener {

    @EventHandler
    public void prepareCraft(PrepareItemCraftEvent ev) {
        CraftEvents.prepareCraft(ev);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent ev) {
        if (ev.getInventory().getName().equalsIgnoreCase("Banco") || ev.getInventory().getTitle().equalsIgnoreCase("Banco")) {
            List<ItemStack> items = new ArrayList<ItemStack>();
            for (ItemStack ss : ev.getInventory().getContents()) {
                //if (ss != null) {
                items.add(ss);
                //}
            }
            KoM.database.setBanco(ev.getPlayer().getUniqueId(), items.toArray(new ItemStack[items.size()]));
        }
        CmdOE.invClick(ev);
    }

    public static void checkArmorEquippin(InventoryClickEvent ev) {
        Player p = ((Player) ev.getWhoClicked());
        //p.sendMessage("Slot: "+ev.getSlot()+ "tipo :" +ev.getSlotType().toString());
        // }
        //  return;
        //}

        if (ev.getSlot() >= 36 && ev.getSlot() <= 39 && (ev.getSlotType() == InventoryType.SlotType.ARMOR)) {
            // se ta tirando armadura
            if (ev.getCursor() == null || ev.getCursor().getType() == Material.AIR) {
                String ci = CustomItem.getCustomItem(ev.getCurrentItem());
                if (ci != null) {
                    if (ci.equalsIgnoreCase(L.m("Capacete da Visao"))) {
                        p.sendMessage(ChatColor.GREEN + L.m("Voce tirou o capacete da visao !"));
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " remove simplyvanish.see-all");
                        VanishConfig cfg = SimplyVanish.getVanishConfig(p.getName(), true);
                        cfg.see.state = false;
                        SimplyVanish.setVanishConfig(p.getName(), cfg, true);
                        SimplyVanish.updateVanishState(p);
                    }
                }
            }

            // se ta pondo armadura
            if (ev.getCursor() != null && ev.getCursor().getType() != Material.AIR) {
                String ci = CustomItem.getCustomItem(ev.getCursor());
                if (ci != null) {
                    if (ci.equalsIgnoreCase(L.m("Capacete da Visao"))) {
                        p.sendMessage(ChatColor.RED + "Use o item para equipa-lo !");
                        ev.setCancelled(true);
                    }
                }

                Material armadura = ev.getCursor().getType();
                if (armadura == null) {
                    return;
                }

                armadura = Blacksmith.getToolLevel(armadura);

                if (armadura != Material.LEATHER && p.getLevel() < 4) {
                    p.sendMessage(ChatColor.RED + L.m("Voce precisa chegar ao nivel 4 para equipar armaduras melhores"));
                    ev.setCancelled(true);
                    p.closeInventory();
                    return;
                }
                if (!CustomItem.podeUsar(p, ev.getCursor())) {
                    p.sendMessage(ChatColor.RED + L.m("Este equipamento não é de sua classe !!"));
                    ev.setCancelled(true);
                    p.closeInventory();
                    return;
                }
            }
        }
    }

    @EventHandler
    public void beginCraft(BeginCraftEvent ev) {
        if (ev.getCraftable().classe == Jobs.Classe.Ferreiro) {
            Blacksmith.criaItem(ev);
        }
    }

    @EventHandler
    public void finishCraft(FinishCraftEvent ev) {
        if (ev.getCraftable().classe == Jobs.Classe.Ferreiro) {
            Blacksmith.terminaDeCriarItem(ev);
        } else if (ev.getCraftable().classe == Jobs.Classe.Fazendeiro) {
            Farmer.depoisCraft(ev);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void pegaItem(final InventoryClickEvent event) {

        CmdHarvestCheck.invClick(event);
        if (event.isCancelled()) {
            return;
        }

        CmdCraftCheck.invClick(event);
        if (event.isCancelled()) {
            return;
        }

        BussolaMagica.incClick(event);
        if (event.isCancelled()) {
            return;
        }

        CapaInvisvel.aparece((Player) event.getWhoClicked());

        if (event.getInventory().getType() == InventoryType.ANVIL) {

            if ((event.getAction() == InventoryAction.HOTBAR_SWAP || event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || event.getAction() == InventoryAction.SWAP_WITH_CURSOR)) {
                ((Player) event.getWhoClicked()).sendMessage(ChatColor.RED + L.m("Voce nao pode fazer isto"));
                event.setCancelled(true);
                return;
            } else {

            }

        }

        if (event.getInventory().getType() == InventoryType.FURNACE) {
            if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
                event.setCancelled(true);
            }
        }

        if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.WRITTEN_BOOK) {
            if (event.getInventory().getType() == InventoryType.ANVIL) {
                event.setCancelled(true);
                return;
            }
        }
        if (event.getCursor() != null && event.getCursor().getType() == Material.WRITTEN_BOOK) {
            if (event.getInventory().getType() == InventoryType.ANVIL) {
                event.setCancelled(true);
                return;
            }
        }

        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        if (event.getCurrentItem() == null) {
            return;
        }
        if (event.getSlotType() == null) {
            return;
        }

        SkillMaster.invClick(event);
        if (event.isCancelled()) {
            return;
        }

        Aura.clica(event);

        if (event.isCancelled()) {
            return;
        }

        if (event.getInventory().getName().equalsIgnoreCase("mob.villager")) {
            return;
        }

        if (KoM.debugMode) {
            KoM.log.info("Acao inventario : " + event.getAction().name());
        }

        if (KoM.debugMode && event.getWhoClicked().isOp()) {
            ((Player) event.getWhoClicked()).sendMessage("Slot:" + event.getSlot());
        }

        if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.WRITTEN_BOOK) {
            BookMeta meta = (BookMeta) event.getCurrentItem().getItemMeta();
            if (meta != null && meta.getTitle().equalsIgnoreCase("Quest journal")) {
                if (meta.getAuthor() == null || meta.getAuthor().length() < 1) {
                    event.setCurrentItem(null);
                    event.setCancelled(true);
                    event.getInventory().setItem(event.getSlot(), null);
                }
            }
        }
        //////// RUNAS
        if (event.getWhoClicked().hasMetadata("RUNA")) {
            event.setCancelled(true);
            Player p = (Player) event.getWhoClicked();
            event.getWhoClicked().removeMetadata("RUNA", KoM._instance);
            if (!p.getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda)) {
                p.sendMessage(ChatColor.RED + "Voce nao pode fazer isto aqui");
            } else {
                String ci = CustomItem.getCustomItem(event.getCurrentItem());
                if (ci == null) {
                    ((Player) event.getWhoClicked()).sendMessage(ChatColor.RED + L.m("Isto nao eh uma runa !"));
                } else if (!ci.equalsIgnoreCase(L.m("Runa"))) {
                    ((Player) event.getWhoClicked()).sendMessage(ChatColor.RED + L.m("Isto nao eh uma runa !"));
                } else {
                    Location destino = Runa.getLocal(event.getCurrentItem());
                    Location origem = p.getLocation();
                    if (destino != null) {
                        // fazendo o portal
                        String clan = ClanLand.getTypeAt(destino);
                        if (!clan.equalsIgnoreCase("WILD")) {
                            p.sendMessage(ChatColor.RED + "Voce so pode fazer isto de terras sem dono para terras sem dono.");
                        } else {
                            clan = ClanLand.getTypeAt(origem);
                            if (!clan.equalsIgnoreCase("WILD")) {
                                p.sendMessage(ChatColor.RED + "Voce so pode fazer isto de terras sem dono para terras sem dono.");
                            } else if (destino.getBlock().getType() != Material.AIR || destino.getBlock().getRelative(BlockFace.UP).getType() != Material.AIR) {
                                p.sendMessage(ChatColor.RED + "A saida do portal esta bloqueda !.");
                            } else {// if (testForSpell(((Player) event.getWhoClicked()), 90, Jobs.getJobLevel("Mago", p))) {
                                if (Mana.spendMana(p, 150)) {
                                    new Portal().constroi(origem, destino);
                                    p.sendMessage(ChatColor.GREEN + "Voce abriu o portal");
                                }
                            }
                        }
                    } else {
                        String clan = ClanLand.getTypeAt(p.getLocation());
                        if (!clan.equalsIgnoreCase("WILD")) {
                            p.sendMessage(ChatColor.RED + "Voce so pode fazer isto de terras sem dono para terras sem dono.");
                        }
                        int job = Jobs.getJobLevel("Mago", p);
                        //if (testForSpell(((Player) event.getWhoClicked()), 90, job)) {
                        if (Mana.spendMana(p, 150)) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 0));
                            ItemStack marcada = CustomItem.getItem(Runa.class).generateItem(1);
                            Runa.marca(marcada, event.getWhoClicked().getLocation(), p.getName());
                            event.setCurrentItem(marcada);
                            ((Player) event.getWhoClicked()).sendMessage(ChatColor.GREEN + "Voce marcou a runa !");

                        }
                        //} else {
                        //    p.sendMessage(ChatColor.RED + "Voce falhou em marcar a runa !");
                        //   Mana.spendMana(p, 5);
                        //}

                    }
                }
            }

        }

        String customItem = CustomItem.getCustomItem(event.getCurrentItem());
        if (customItem != null) {
            KoM.addGlow(event.getCurrentItem());
        }

        Player p = ((Player) event.getWhoClicked());
        if (p.isOp() && KoM.debugMode) {
            p.sendMessage(event.getInventory().getName());
        }

        if (p.hasMetadata("click")) {
            long tempo = (long) MetaShit.getMetaObject("click", p);
            if (tempo + 30 > System.currentTimeMillis()) {
                p.sendMessage(ChatColor.RED + L.m("Voce clicou muito rapido !"));
                if (p.hasMetadata("clicadas")) {
                    int clicadas = (int) MetaShit.getMetaObject("clicadas", p);
                    if (clicadas >= 10) {
                        p.kickPlayer(L.m("Admins foram informados do seu AutoClick !!"));
                        for (Player p2 : Bukkit.getOnlinePlayers()) {
                            if (p2.isOp()) {
                                p2.sendMessage(ChatColor.AQUA + "----- Gambeta Anti AutoClick -------- !!");
                                p2.sendMessage(ChatColor.AQUA + "ATENCAO: O MANOLO " + p.getName() + " TA TENTANDO AUTOCLICK POSSIVELMENTE !!");
                                p2.sendMessage(ChatColor.AQUA + "ELE FOI KIKADO DO SERVER POR ISTO ! FICA DE ZOIO NELE !!");
                            }
                        }
                        event.setCancelled(true);
                        return;
                    } else {
                        clicadas++;
                    }
                    MetaShit.setMetaObject("clicadas", p, clicadas);
                } else {
                    MetaShit.setMetaObject("clicadas", p, 1);
                }
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
                MetaShit.setMetaObject("click", p, System.currentTimeMillis());
                return;
            }
        }
        MetaShit.setMetaObject("click", p, System.currentTimeMillis());
        p.removeMetadata("clicadas", KoM._instance);

        ItemMeta meta = event.getCurrentItem().getItemMeta();
        if (meta != null && meta.getLore() != null && meta.getLore().size() > 0) {
            for (String l : meta.getLore()) {
                if (l.contains("[Fixo]")) {
                    p.sendMessage(L.m("Voce nao pode mecher este item !"));
                    event.setCancelled(true);
                }
            }
        }

        if (event.getInventory().getTitle().equalsIgnoreCase("Zoiudo !") || event.getInventory().getName().equalsIgnoreCase("Zoiudo !") || event.getInventory().getTitle().equalsIgnoreCase("KoM") || event.getInventory().getName().equalsIgnoreCase("KoM")) {
            event.setCancelled(true);
        }

        if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
            List<String> lore = meta.getLore();
            if (lore != null && lore.size() > 3) {
                int criado = 0;
                for (String l : lore) {
                    if (l.contains("Criado Por")) {
                        criado++;
                    }
                }
                if (criado >= 3) {
                    event.setCurrentItem(null);
                    p.sendMessage(ChatColor.AQUA + L.m("* o item bugado se desfaz *"));
                }
            }
        }

        if ((event.getInventory().getType() == InventoryType.ANVIL || event.getInventory().getType() == InventoryType.DISPENSER || event.getInventory().getType() == InventoryType.FURNACE || event.getInventory().getType() == InventoryType.ENCHANTING || event.getInventory().getType() == InventoryType.BREWING) || !event.getInventory().getTitle().contains("!")) {
            if (meta != null && meta.getLore() != null && meta.getLore().size() > 0) {
                for (String lore : meta.getLore()) {
                    if (lore.contains("!")) {

                        event.setCurrentItem(null);
                        event.setCursor(null);
                        p.sendMessage(ChatColor.AQUA + L.m("* o item ilusório se desfaz *"));
                    }
                }
            }
        }
        ///// BLOCK STACK PICK TO AVOYD BUG !
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.BREAD
                && event.getCurrentItem().getType() != Material.SUGAR_CANE
                && event.getCurrentItem().getType() != Material.TORCH
                && event.getCurrentItem().getType() != Material.CHEST
                && event.getCurrentItem().getType() != Material.BED
                && event.getCurrentItem().getType() != Material.FENCE
                && event.getCurrentItem().getType() != Material.LADDER
                && event.getCurrentItem().getType() != Material.STONE_BUTTON) {

            
           
        }
        //
        // COLOCAR BANNER EM CIMA DE ESCUDO PINTA O ESCUDO
        //
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.SHIELD) {
            if (event.getCursor() != null && event.getCursor().getType() == Material.BANNER) {
                ItemStack shield = new ItemStack(Material.SHIELD);
                BannerMeta bannerMeta = (BannerMeta) event.getCursor().getItemMeta();
                ItemMeta metameta = shield.getItemMeta();
                BlockStateMeta bmeta = (BlockStateMeta) metameta;
                Banner escudo = (Banner) bmeta.getBlockState();

                //KoM.debug("Setando base color " + bannerMeta.getBaseColor().name());
                escudo.setBaseColor(bannerMeta.getBaseColor());
                for (Pattern pat : bannerMeta.getPatterns()) {
                    KoM.debug("Addando pattern ");
                    escudo.addPattern(pat);

                }
                //EDIT 
                KoM.debug("Atualizando");
                DyeColor corBanner = DyeColor.getByDyeData(event.getCursor().getData().getData());
                KoM.debug("Cor banner = " + corBanner.name());
                escudo.setBaseColor(corBanner);
                escudo.update();

                //EDIT end
                bmeta.setBlockState(escudo);
                shield.setItemMeta(bmeta);
                event.setCurrentItem(shield);
                p.sendMessage(ChatColor.GREEN + "Voce colocou o a imagem no escudo");
                event.setCancelled(true);
            }
        }
        //  }
        if (event.getCursor() != null && event.getCursor().getAmount() == 64) {
            return;
        }
        if (event.getInventory() != null && event.getInventory().getName() != null && event.getInventory().getName().equalsIgnoreCase("container.furnace") && event.getSlotType() == InventoryType.SlotType.RESULT) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.INK_SACK) {
                event.setCancelled(true);
            }
            if (event.getInventory() != null && event.getInventory().getItem(event.getSlot()) != null && (event.getInventory().getItem(event.getSlot()).getType() == Material.IRON_INGOT || event.getInventory().getItem(event.getSlot()).getType() == Material.GOLD_INGOT || event.getInventory().getItem(event.getSlot()).getType() == Material.QUARTZ)) {
                if (event.getCursor() != null && event.getCursor().getType() != Material.AIR) {
                    p.sendMessage(ChatColor.RED + L.m("Largue o item da mao antes de retirar as barras !"));
                    event.setCancelled(true);
                    return;
                }
                CraftEvents.forjaLingote(event);
                return;
            }
        }

        if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
            checkArmorEquippin(event);
            return;
        }
        Encaixe.encaixa(event);
    }

}
