/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Classes.Mage;

import java.util.ArrayList;
import java.util.List;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.RecipeBook;
import nativelevel.Jobs;
import nativelevel.Jobs.Sucesso;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.MetaShit;
import nativelevel.Attributes.Mana;
import nativelevel.Classes.Thief;
import nativelevel.Jobs.TipoClasse;
import nativelevel.sisteminhas.KomSystem;
import nativelevel.RecipeBooks.BookTypes;
import nativelevel.integration.WorldGuardKom;
import nativelevel.sisteminhas.XP;
import nativelevel.utils.Cooldown;
import nativelevel.utils.ScreenTitle;
import nativelevel.utils.TitleAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MageEvents extends KomSystem {

    @EventHandler
    public void interact(PlayerInteractEvent ev) {
        if (ev.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.WRITTEN_BOOK) {

            if (Thief.taInvisivel(ev.getPlayer())) {
                Thief.revela(ev.getPlayer());
            }

            CustomItem ci = CustomItem.getItem(ev.getPlayer().getItemInHand());
            if (ci instanceof RecipeBook) {
                ItemStack inHand = ev.getPlayer().getItemInHand();
                BookMeta meta = (BookMeta) inHand.getItemMeta();
                if (meta.getAuthor() == null || meta.getAuthor().equalsIgnoreCase("ninguem...")) {
                    meta.setAuthor(ev.getPlayer().getName());
                    inHand.setItemMeta(meta);
                }

                KoM.debug("META " + meta.getAuthor() + " NOME " + ev.getPlayer().getName());

                if (!meta.getAuthor().equalsIgnoreCase(ev.getPlayer().getName())) {
                    ev.getPlayer().sendMessage(ChatColor.RED + "Esse livro pertence a " + meta.getAuthor());
                    ev.setCancelled(true);
                    return;
                }

                RecipeBook book = (RecipeBook) ci;
                if (book.getBookType(ev.getPlayer().getItemInHand()) == BookTypes.Magia) {

                    if (WorldGuardKom.ehSafeZone(ev.getPlayer().getLocation())) {
                        ev.getPlayer().sendMessage(ChatColor.RED + "Você não pode usar magias em cidades. Imagina a loucura que seria ?");
                        ev.setCancelled(true);
                        return;
                    }

                    TipoClasse tipo = Jobs.getJobLevel(Jobs.Classe.Mago, ev.getPlayer());

                    Elements summoned = null;

                    if (ev.getPlayer().isSneaking() && (ev.getAction() == Action.RIGHT_CLICK_AIR || ev.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                        summoned = Elements.Raio;
                    } else if (ev.getPlayer().isSneaking() && (ev.getAction() == Action.LEFT_CLICK_AIR || ev.getAction() == Action.LEFT_CLICK_BLOCK)) {
                        summoned = Elements.Fogo;
                    } else if (!ev.getPlayer().isSneaking() && (ev.getAction() == Action.LEFT_CLICK_AIR || ev.getAction() == Action.LEFT_CLICK_BLOCK)) {
                        summoned = Elements.Terra;
                    }

                    if (summoned != null) {

                        if (tipo == TipoClasse.NADA) {
                            ev.getPlayer().sendMessage(ChatColor.RED + "Voce balança o livro no ar feito bobo, e nada acontece.");
                            ev.setCancelled(true);
                            return;
                        }

                        ev.setCancelled(true);
                        List<Elements> elements = new ArrayList<Elements>(3);
                        if (ev.getPlayer().hasMetadata("elements")) {
                            elements = (List<Elements>) MetaShit.getMetaObject("elements", ev.getPlayer());
                        }
                        // summonig elements
                        if (elements.size() < 3) {
                            ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Conjurou ") + summoned.icon + " " + summoned.name());
                            elements.add(summoned);
                            String title = "";
                            for (Elements e : elements) {
                                title += e.icon;
                            }
                            PlayEffect.play(VisualEffect.ENDER_SIGNAL, ev.getPlayer().getLocation().add(0, 1, 0), "num:1");
                            //ScreenTitle.show(ev.getPlayer(), title, "");
                            TitleAPI.sendTitle(ev.getPlayer(), 1, 20 * 3, 20, title, "");
                            //TitleAPI.sendActionBar(ev.getPlayer(), title);

                            MetaShit.setMetaObject("elements", ev.getPlayer(), elements);
                        } else {
                            // checking if i have the spell recipe
                            boolean casted = false;
                            List<String> recipes = book.getRecipes(ev.getPlayer().getItemInHand());
                            for (String recipe : recipes) {
                                MageSpell spell = SpellLoader.spells.get(recipe);
                                Elements[] summonedElements = elements.toArray(new Elements[3]);
                                if (spell.getElements()[0].name() == summonedElements[0].name() && spell.getElements()[1].name() == summonedElements[1].name() && spell.getElements()[2].name() == summonedElements[2].name()) {

                                    TitleAPI.sendTitle(ev.getPlayer(), 1, 20 * 3, 20, "", "");

                                    if (Cooldown.isCooldown(ev.getPlayer(), spell.name)) {
                                        int segundos = (int) Math.round(Cooldown.get(ev.getPlayer(), spell.name).millisRemaining() / 1000);
                                        TitleAPI.sendActionBar(ev.getPlayer(), ChatColor.RED + "" + ChatColor.BOLD + "Aguarde: " + segundos + " segundos");
                                        ev.getPlayer().removeMetadata("elements", KoM._instance);
                                        return;
                                    }

                                    Cooldown.setMetaCooldown(ev.getPlayer(), spell.name, spell.getCooldownInSeconds() * 1000);

                                    double requiredMana = spell.getManaCost();

                                    if (!Mana.spendMana(ev.getPlayer(), (int) requiredMana)) {
                                        return;
                                    }

                                    int minSkill = spell.getMinSkill();
                                    double mySkill = ev.getPlayer().getLevel();

                                    if(tipo==TipoClasse.PRIMARIA)
                                        minSkill -= 15;
                                    if(minSkill < 1)
                                        minSkill = 1;

                                    Sucesso sucesso = Jobs.hasSuccess(minSkill, Jobs.Classe.Mago, ev.getPlayer(), 0);
                                    if (sucesso.acertou) {
                                        ev.getPlayer().removeMetadata("elements", KoM._instance);
                                        //int xp = XP.getExpPorAcao(minSkill);
                                        //XP.changeExp(ev.getPlayer(), xp, 0.2);
                                        ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 1));
                                        spell.cast(ev.getPlayer());
                                        ev.getPlayer().sendMessage(ChatColor.GREEN + "Voce conjurou a magia " + ChatColor.AQUA + spell.name);
                                        PlayEffect.play(VisualEffect.SOUND, ev.getPlayer().getLocation(), "type:WITHER_SHOOT");
                                    } else {
                                        ev.getPlayer().sendMessage(L.m(ChatColor.RED + "A magia se dissipou"));
                                        ev.getPlayer().removeMetadata("elements", KoM._instance);
                                        return;
                                    }

                                    casted = true;
                                    break;

                                }
                            }
                            if (!casted) {
                                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Você ainda não conhece essa magia !"));
                            }
                            ev.getPlayer().removeMetadata("elements", KoM._instance);
                        }
                    }
                }
            }
        }
    }

    public void onEnable() {
        SpellLoader.load();
    }

}
