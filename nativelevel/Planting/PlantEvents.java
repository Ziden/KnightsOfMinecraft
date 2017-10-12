package nativelevel.Planting;


import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.CustomEvents.BlockHarvestEvent;
import nativelevel.CustomEvents.BlockPlantEvent;
import nativelevel.Harvesting.HarvestTypes;
import nativelevel.Jobs;
import nativelevel.Jobs.TipoClasse;
import nativelevel.KoM;
import nativelevel.Lang.LangMinecraft;
import nativelevel.MetaShit;
import nativelevel.sisteminhas.XP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.CropState;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;

/**
 *
 * @author Ziden
 *
 */
public class PlantEvents {

    public static void plantaBlock(final BlockPlaceEvent ev) {
        
        Plantable plant = PlantCache.getPlantable(ev.getBlock());

        if (plant != null) {
            KoM.debug("Plantable");
            Jobs.Classe classe = plant.classe;

            int dificuldade = plant.difficulty;
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
                exp *= plant.expRatio;

                if (tipoClasse == TipoClasse.NADA) {
                    exp = exp / 10;
                } else if (tipoClasse == TipoClasse.SEGUNDARIA) {
                    exp = exp / 2;
                }

                XP.changeExp(ev.getPlayer(), exp, 1);
                
                if (HarvestTypes.engenhoca(ev.getBlock())) {
                    ev.getPlayer().sendMessage(KoM.tag + "§a Voce colocou a engenhoca com " + ChatColor.UNDERLINE + ChatColor.AQUA + "sucesso " + pctFinal);
                } else {
                    ev.getPlayer().sendMessage(KoM.tag + "§a Voce plantou o recurso com " + ChatColor.UNDERLINE + ChatColor.AQUA + "sucesso " + pctFinal);
                }
                
                BlockPlantEvent plantEvent = new BlockPlantEvent(ev.getPlayer(), ev.getBlock(), plant, sucesso);
                Bukkit.getServer().getPluginManager().callEvent(plantEvent);
 
                // falha
            } else if (!sucesso.acertou) {
                //ev.setCancelled(true);
                PlayEffect.play(VisualEffect.SPELL, (ev.getBlock().getLocation().add(0.5D, 0.5D, 0.5D)), "num:5");
              
                Runnable r = new Runnable() {
                    public void run() {
                        ev.getBlock().setType(Material.AIR);
                    }
                };
                Bukkit.getScheduler().runTaskLater(KoM._instance, r, 1);
                
                if (HarvestTypes.engenhoca(ev.getBlock())) {
                     ev.getPlayer().playEffect(ev.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), Effect.STEP_SOUND, Material.IRON_BLOCK.getId());
                    ev.getPlayer().sendMessage(KoM.tag + "§c Voce §4§nfalhou§c em implementar a engenhoca " + pctFinal);
                } else {
                     ev.getPlayer().playEffect(ev.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), Effect.STEP_SOUND, Material.LEAVES.getId());
                    ev.getPlayer().sendMessage(KoM.tag + "§c Voce §4§nfalhou§c em plantar " + pctFinal);
                }
                
                //ev.getBlock().setType(Material.AIR);
                
                // Tutorialzin
                if (ev.getPlayer().getLevel() < 10) {
                    if (!ev.getPlayer().hasMetadata("1erro")) {
                        MetaShit.setMetaObject("1erro", ev.getPlayer(), 1);
                        ev.getPlayer().sendMessage("§e[Dica] §aVoce irá falhar menos quanto maior seu nivel !");
                    }
                }
            }
        }

    }

}
