package nativelevel.Harvesting;

import nativelevel.Harvesting.HarvestTypes;
import nativelevel.Harvesting.Harvestable;
import nativelevel.Harvesting.HarvestCache;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Attributes.Stamina;
import nativelevel.Crafting.CraftCache;
import nativelevel.Crafting.CraftEvents;
import nativelevel.Crafting.Craftable;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.Wikipedia;
import nativelevel.CustomEvents.BlockHarvestEvent;
import nativelevel.Jobs;
import nativelevel.Jobs.TipoClasse;
import nativelevel.KoM;
import nativelevel.Lang.LangMinecraft;
import nativelevel.MetaShit;
import nativelevel.Planting.PlantCache;
import nativelevel.Planting.Plantable;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.sisteminhas.XP;
import nativelevel.utils.CategoriaUtils;
import nativelevel.utils.CategoriaUtils.CategoriaItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.CropState;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;

/**
 *
 * @author Ziden
 *
 */
public class HarvestEvents {

    public static void interage(PlayerInteractEvent ev) {
        if (ev.getPlayer().getInventory().getItemInMainHand().getType() == Material.BOOK) {
            CustomItem cu = CustomItem.getItem(ev.getPlayer().getInventory().getItemInMainHand());
            if (cu != null && cu instanceof Wikipedia) {
                ev.setCancelled(true);

                if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    Harvestable h = HarvestCache.getHarvestable(ev.getClickedBlock());
                    Plantable plant = PlantCache.getPlantable(ev.getClickedBlock());
                    Craftable c = CraftCache.getCraftable(ev.getClickedBlock().getType());
                    if (c != null) {
                        double chance = Jobs.getChancesToSuccess(c.diff, ev.getPlayer().getLevel());
                        ev.getPlayer().sendMessage(CraftEvents.getCorChance(chance) + CraftEvents.getNomeChance(chance) + " §2você conseguiu criar esse bloco. Este bloco é de conhecimento de " + c.classe.name() + "s");
                    }
                    if (h != null) {
                        double chance = Jobs.getChancesToSuccess(h.difficulty, ev.getPlayer().getLevel());
                        ev.getPlayer().sendMessage(CraftEvents.getCorChance(chance) + CraftEvents.getNomeChance(chance) + " §2você coletar esse bloco com sua experiência. Este bloco é de conhecimento de " + h.classe.name() + "s");
                    }
                    if (plant != null) {
                        double chance = Jobs.getChancesToSuccess(plant.difficulty, ev.getPlayer().getLevel());
                        ev.getPlayer().sendMessage(CraftEvents.getCorChance(chance) + CraftEvents.getNomeChance(chance) + " §2você plantar esse bloco com sua experiência. Este bloco é de conhecimento de " + plant.classe.name() + "s");
                    }

                    if (plant == null && h == null && c == null) {
                        ev.getPlayer().sendMessage("§2Nada especial neste bloco.");
                    }

                }

            }
        }
    }

    public static void quebraBlock(BlockBreakEvent ev) {

        Harvestable harv = HarvestCache.getHarvestable(ev.getBlock());
        KoM.debug("Block Break");

        if (ev.getBlock().getType()==Material.CROPS) {
          
            if (ev.getBlock().getData() < 7) {
                ev.getPlayer().sendMessage(ChatColor.RED + "Esta planta não estava pronta para colheita");
                ev.setCancelled(true);
                ev.getBlock().setType(Material.AIR);
                ev.getPlayer().playEffect(ev.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), Effect.STEP_SOUND, ev.getBlock().getType().getId());
                PlayEffect.play(VisualEffect.SPELL, (ev.getBlock().getLocation().add(0.5D, 0.5D, 0.5D)), "num:5");
                return;
            }
        } else if (ev.getBlock().getType()==Material.NETHER_WART_BLOCK || ev.getBlock().getType()==Material.NETHER_WARTS) {
            if (ev.getBlock().getData() < 3) {
                ev.getPlayer().sendMessage(ChatColor.RED + "Esta planta não estava pronta para colheita");
                ev.setCancelled(true);
                ev.getBlock().setType(Material.AIR);
                ev.getPlayer().playEffect(ev.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), Effect.STEP_SOUND, ev.getBlock().getType().getId());
                PlayEffect.play(VisualEffect.SPELL, (ev.getBlock().getLocation().add(0.5D, 0.5D, 0.5D)), "num:5");
                return;
            }
        } else if (ev.getBlock().getType() == Material.POTATO || ev.getBlock().getType() == Material.CARROT) {
            if (ev.getBlock().getData() < 7) {
                ev.getPlayer().sendMessage(ChatColor.RED + "Esta planta não estava pronta para colheita");
                ev.setCancelled(true);
                ev.getBlock().setType(Material.AIR);
                ev.getPlayer().playEffect(ev.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), Effect.STEP_SOUND, ev.getBlock().getType().getId());
                PlayEffect.play(VisualEffect.SPELL, (ev.getBlock().getLocation().add(0.5D, 0.5D, 0.5D)), "num:5");
                return;
            }
        } else if (ev.getBlock().getType() == Material.BEETROOT_BLOCK) {
            if (ev.getBlock().getData() < 3) {
                ev.getPlayer().sendMessage(ChatColor.RED + "Esta planta não estava pronta para colheita");
                ev.setCancelled(true);
                ev.getBlock().setType(Material.AIR);
                ev.getPlayer().playEffect(ev.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), Effect.STEP_SOUND, ev.getBlock().getType().getId());
                PlayEffect.play(VisualEffect.SPELL, (ev.getBlock().getLocation().add(0.5D, 0.5D, 0.5D)), "num:5");
                return;
            }
        } 

        // ANTI BUG DO CACAU
        if(ev.getBlock().getType()==Material.LOG || ev.getBlock().getType() == Material.LOG_2) {
            
            if(ev.getBlock().getRelative(BlockFace.EAST).getType()==Material.COCOA) {
                ev.getBlock().getRelative(BlockFace.EAST).setType(Material.AIR);
            }
            if(ev.getBlock().getRelative(BlockFace.NORTH).getType()==Material.COCOA) {
                ev.getBlock().getRelative(BlockFace.NORTH).setType(Material.AIR);
            }
            if(ev.getBlock().getRelative(BlockFace.SOUTH).getType()==Material.COCOA) {
                ev.getBlock().getRelative(BlockFace.SOUTH).setType(Material.AIR);
            }
            if(ev.getBlock().getRelative(BlockFace.WEST).getType()==Material.COCOA) {
                ev.getBlock().getRelative(BlockFace.WEST).setType(Material.AIR);
            }
            
        }
        
        
        
        if (harv != null) {

            if (ev.getBlock().hasMetadata("playerpois")) {
                if (harv.classe != Jobs.Classe.Fazendeiro) {
                    return;
                }
            }

            KoM.debug("Harvest");

            CategoriaItem categoria = CategoriaUtils.getCategoria(ev.getBlock());

            int stamina = harv.difficulty / 30;

            if (HarvestTypes.ehMinerio(ev.getBlock()) || HarvestTypes.ehLenha(ev.getBlock())) {
                stamina *= 3;
            }

            if (!Stamina.spendStamina(ev.getPlayer(), stamina)) {
                ev.setCancelled(true);
                return;
            }

            Jobs.Classe classe = harv.classe;

            int dificuldade = harv.difficulty;
            Jobs.Sucesso sucesso = Jobs.hasSuccess(dificuldade, classe, ev.getPlayer(), 0);

            Jobs.TipoClasse tipoClasse = Jobs.getJobLevel(classe, ev.getPlayer());

            String pctFinal = "";
            if (ev.getPlayer().hasMetadata("mostra%")) {

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

            // acerto
            if (sucesso.acertou) {

                double exp = XP.getExpPorAcao(dificuldade);
                exp *= harv.expRatio;

                KoM.debug("Vou dar exp " + exp);
                if (tipoClasse == TipoClasse.NADA) {
                    exp = exp / 10d;
                } else if (tipoClasse == TipoClasse.SEGUNDARIA) {
                    exp = exp / 2d;
                }
                KoM.debug("mas ai depois dei " + exp + " exp");

                XP.changeExp(ev.getPlayer(), exp, 1);
                if (categoria == CategoriaItem.Engenharia || HarvestTypes.engenhoca(ev.getBlock())) {
                    ev.getPlayer().sendMessage(KoM.tag + "§a Voce recolheu o bloco com " + ChatColor.UNDERLINE + ChatColor.AQUA + "sucesso " + pctFinal);
                } else if (categoria == CategoriaItem.Minerio || HarvestTypes.ehMinerio(ev.getBlock())) {
                    if (tipoClasse == Jobs.TipoClasse.PRIMARIA) {
                        RankDB.addPontoCache(ev.getPlayer(), Estatistica.MINERADOR, 1);
                    }
                    ev.getPlayer().sendMessage(KoM.tag + "§a Voce minerou com " + ChatColor.UNDERLINE + ChatColor.AQUA + "sucesso " + pctFinal);
                } else if (HarvestTypes.ehLenha(ev.getBlock())) {
                    if (tipoClasse == Jobs.TipoClasse.PRIMARIA) {
                        KoM.debug("== TIPO PRIMARIA ==");
                        RankDB.addPontoCache(ev.getPlayer(), Estatistica.LENHADOR, 1);
                    }
                    ev.getPlayer().sendMessage(KoM.tag + "§a Voce cortou a madeira com " + ChatColor.UNDERLINE + ChatColor.AQUA + "sucesso " + pctFinal);
                } else {

                    if (categoria == CategoriaItem.Minerio) {
                        RankDB.addPontoCache(ev.getPlayer(), Estatistica.FAZENDEIRO, 1);
                    }
                    ev.getPlayer().sendMessage(KoM.tag + "§a Voce coletou o recurso com " + ChatColor.UNDERLINE + ChatColor.AQUA + "sucesso " + pctFinal);
                }
                BlockHarvestEvent harvEvent = new BlockHarvestEvent(ev.getPlayer(), ev.getBlock(), harv, sucesso);
                Bukkit.getServer().getPluginManager().callEvent(harvEvent);
                // falha
            } else if (!sucesso.acertou) {
                ev.setCancelled(true);
                ev.getPlayer().playEffect(ev.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), Effect.STEP_SOUND, ev.getBlock().getType().getId());
                PlayEffect.play(VisualEffect.SPELL, (ev.getBlock().getLocation().add(0.5D, 0.5D, 0.5D)), "num:5");

                if (HarvestTypes.engenhoca(ev.getBlock())) {
                    ev.getPlayer().sendMessage(KoM.tag + "§c Voce §4§nfalhou§c em recolher o equipamento " + pctFinal);
                } else if (HarvestTypes.ehMinerio(ev.getBlock())) {
                    ev.getPlayer().sendMessage(KoM.tag + "§c Voce §4§nfalhou§c em minerar " + pctFinal);
                } else if (HarvestTypes.ehLenha(ev.getBlock())) {
                    ev.getPlayer().sendMessage(KoM.tag + "§c Voce §4§nfalhou§c em cortar a madeira " + pctFinal);
                } else {
                    ev.getPlayer().sendMessage(KoM.tag + "§c Voce §4§nfalhou§c em coletar o recurso " + pctFinal);
                }
                ev.getBlock().setType(Material.AIR);

                if (ev.getPlayer().getLevel() < 10) {

                    if (!ev.getPlayer().hasMetadata("1erroh")) {
                        MetaShit.setMetaObject("1erroh", ev.getPlayer(), 1);
                        ev.getPlayer().sendMessage("§e§l[Dica] §aVoce irá falhar menos quanto maior seu nivel ! Upe para acertar !");
                    }

                    if (harv.m == Material.LOG && harv.difficulty > 50) {
                        if (!ev.getPlayer().hasMetadata("1mad")) {
                            MetaShit.setMetaObject("1mad", ev.getPlayer(), 1);
                            ev.getPlayer().sendMessage("§e§l[Dica] §aNo KoM, cada madeira tem sua dificuldade de coleta.");
                            ev.getPlayer().sendMessage("§e§l[Dica] §aEssa madeira que voce quebrou é muito difícil de ser cortada.");
                            ev.getPlayer().sendMessage("§e§l[Dica] §a&6Acacia e Madeira Escura&a são difíceis, tente encontrar outros tipos de madeira.");
                        }
                    }
                }
            }
        } else {
            if (Jobs.getJobLevel(Jobs.Classe.Minerador, ev.getPlayer()) == TipoClasse.PRIMARIA && ev.getBlock().getType() == Material.STONE) {
                XP.changeExp(ev.getPlayer(), 1, 1);
            }
        }

    }

}
