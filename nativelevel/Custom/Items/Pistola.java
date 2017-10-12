/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, Gabripj, Feldmann
 Patrocionio: InstaMC

 */
package nativelevel.Custom.Items;

import java.util.List;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Classes.Thief;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.MetaShit;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.Attributes.Mana;
import nativelevel.spec.PlayerSpec;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

/**
 *
 * @author usuario
 *
 */
public class Pistola extends CustomItem {

    public Pistola() {
        super(Material.LEVER, L.m("Bonka Boom"), L.m("Uma velha engenhoca de arma"), CustomItem.INCOMUM);
    }

    @Override
    public boolean onItemInteract(Player player) {

        int job = Jobs.getJobLevel("Engenheiro", player);
        if (job != 1) {
            player.sendMessage(ChatColor.RED + L.m("Apenas bons engenheiros sabem usar isto !"));
            return false;
        }

        if (!player.hasPotionEffect(PotionEffectType.SLOW)) {

            if (!player.getInventory().contains(Material.SULPHUR)) {
                player.sendMessage(L.m("Voce precisa de polvora para atirar"));
                return false;
            }

            boolean gastaMana = true;
            ItemMeta m = player.getItemInHand().getItemMeta();
            List<String> lore = m.getLore();
            if (lore.size() > 0) {
                if (lore.get(0).contains("Bonka Versao 2")) {
                    gastaMana = false;
                }
            }

            int mana = 8;
            int random = 2;
            if (PlayerSpec.temSpec(player, PlayerSpec.Fuzileiro)) {
                mana = 15;
                random = 3;
            } else if (PlayerSpec.temSpec(player, PlayerSpec.Inventor)) {
                mana = 5;
                random = 6;
            }
            if (gastaMana && !Mana.spendMana(player, mana)) {
                return false;
            }

            if (Jobs.rnd.nextInt(random) == 1) {
                KoM.removeInventoryItems(player.getInventory(), Material.SULPHUR, 1);
            }

            if (Thief.taInvisivel(player)) {
                Thief.revela(player);
            }

            PlayEffect.play(VisualEffect.SMOKE_LARGE, player.getLocation(), "num:1");
            player.sendMessage(ChatColor.RED + "*boonka*");
            player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 10, 2);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 1));
            Vector dir = player.getLocation().getDirection().multiply(10);
            Location loc = player.getLocation();

            Location inicio = new Location(player.getWorld(), loc.getX() + (dir.getX() / 5.0), loc.getY() + player.getEyeHeight() - 0.1, loc.getZ() + (dir.getZ() / 5.0));
                //Snowball snowball = (Snowball)player.getWorld().spawnEntity(inicio, EntityType.SNOWBALL);

            Snowball snowball = (Snowball) player.launchProjectile(Snowball.class);
            
            if(!player.isSneaking()) {
                Vector destino = snowball.getVelocity();
                destino.add(new Vector(Jobs.rnd.nextInt(1) - (1 / 2) / 20, Jobs.rnd.nextInt(1) - (1 / 2) / 20, Jobs.rnd.nextInt(1) - (1 / 2) / 20));
                snowball.setVelocity(destino);
            }
            
            snowball.setFireTicks(400);
                //snowball.setVelocity(player.getLocation().getDirection().multiply(2f));

            MetaShit.setMetaString("tipoTiro", snowball, "kaboom");
            /*
            AttributeInfo info = KnightsOfMania.database.getAtributos(player);
            double ratio = 0.4 + ((Attributes.calcMagicDamage(PlayerSpec.temSpec(player, PlayerSpec.Sabio), info.attributes.get(Attr.intelligence))) + Attributes.calcArcheryDamage(info.attributes.get(Attr.dexterity)) / 2);
            if(KnightsOfMania.debugMode)
                KnightsOfMania.log.info("RATIO BONKA: "+ratio);
            */
            MetaShit.setMetaObject("modDano", snowball, 1.2);
        }
        return false;
    }

}
