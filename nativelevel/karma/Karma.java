package nativelevel.karma;


import nativelevel.sisteminhas.ClanLand;
import nativelevel.KoM;
import nativelevel.MetaShit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Button;
import org.bukkit.material.Cake;
import org.bukkit.material.Dispenser;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Karma {

    public static void main(String[] args) {

        int karmaMorreu = 500;
        int karmaMatou = 100;

        int karmaGanho = (-karmaMorreu - karmaMatou) / 100;

        int karmaFinal = karmaMatou + karmaGanho;
        System.out.println(karmaGanho);
    }

    public static int DELIMITADOR = -50;

    public static void manoloMata(Player matador, LivingEntity morreu) {

        if(matador.getWorld().getName().equalsIgnoreCase("WoE") || matador.getWorld().getName().equalsIgnoreCase("Arena"))
            return;
        
        int karmaMatou = KoM.database.getKarma(matador.getUniqueId());
        int karmaMorreu = (ClanLand.getMobLevel(morreu.getLocation()) * 5) * 320 / 2;

        boolean player = false;
        if (morreu.getType() == EntityType.PLAYER) {
            player = true;
            karmaMorreu = KoM.database.getKarma(morreu.getUniqueId());
        } else {
            if (morreu.hasMetadata("nivel")) {
                int nivelMob = (Integer) MetaShit.getMetaObject("nivel", morreu) + 1;
                karmaMorreu = karmaMorreu * nivelMob; // recupera o /5
            } else if (KoM.mm.getMobManager().isActiveMob(morreu.getUniqueId())) {
                karmaMorreu = karmaMorreu * 50000;
            }
        }

        int karmaGanho = (karmaMorreu - karmaMatou) / 100;
        if (player == true) {
            karmaGanho = (-karmaMorreu - karmaMatou) / 75;
            // se to perdendo karma por matar um noob
            if (
                    
                    ((Player) morreu).getLevel() <= 10 && karmaGanho < 0) {
                karmaGanho *= 10;
            }
        } else {
            if (matador.isOp() && KoM.debugMode) {
                matador.sendMessage("Karma do mob: " + karmaMorreu);
            }
            if (karmaGanho < 0) {
                karmaGanho = 0;
            } 
            karmaGanho /= 6;
        }
        
        // se quem morreu for criminoso, não perde karma
        if(morreu.getType()==EntityType.PLAYER && karmaGanho < 0 && Criminoso.isCriminoso((Player)morreu)) {
            return;
        }
        
        if(karmaGanho > 100)
            karmaGanho = 100;
        
        int karmaFinal = karmaMatou + karmaGanho;
        if (karmaFinal < -32000) {
            karmaFinal = - 32000;
        }
        if (karmaFinal > 32000) {
            karmaFinal = 32000;
        }

        int diferencaKarma = karmaFinal - karmaMatou;

        // se eu sou bom e matei um mau, nao perco karma
        if (karmaMatou > 0 && karmaMorreu < 0 && diferencaKarma < 0) {
            return;
        }

        if (diferencaKarma < 0 && !player) {
            diferencaKarma = 0;
            karmaFinal = karmaMatou;
        }

        if (diferencaKarma < 0) {
            diferencaKarma *= 5;
            matador.sendMessage(ChatColor.GREEN + "Karma: " + karmaMatou + " " + ChatColor.GOLD + "" + diferencaKarma);
            KoM.database.setKarma(matador.getUniqueId(), karmaMatou + diferencaKarma);
            if (!matador.hasMetadata("msgkarma") && matador.getLevel() <= 10) {
                MetaShit.setMetaObject("msgkarma", matador, true);
                matador.sendMessage("§e§l[Dica] §aVoce matou uma pessoa boa, e perdeu Karma. No KoM voce é livre, pode matar, roubar, pilhar, porém suas ações definem seu caráter.");
                matador.sendMessage("§e§l[Dica] §aPessoas um simbolo de caveira mais avermelhado são malvadas, pessoas com simbolos de carinha feliz mais azulados são boas.");
            }
        } else {
            matador.sendMessage(ChatColor.GREEN + "Karma: " + karmaMatou + " " + ChatColor.GOLD + "+" + diferencaKarma);
            KoM.database.setKarma(matador.getUniqueId(), karmaFinal);
        }

        if (karmaFinal != karmaMatou) {
            if (!ClanLand.permission.playerHas(matador, "kom.bom") && !ClanLand.permission.playerHas(matador, "kom.mau")) {
                ClanLand.permission.playerRemove(matador, "kom.bom");
            }
            if (karmaFinal >= DELIMITADOR && karmaMatou < DELIMITADOR) {
                if (ClanLand.permission.playerHas(matador, "kom.mau")) {
                    ClanLand.permission.playerRemove(matador, "kom.mau");
                }
                ClanLand.permission.playerAdd(matador, "kom.bom");
                matador.sendMessage(ChatColor.GREEN + "Você se tornou uma pessoa de bom Karma.");
            } else if (karmaFinal <= DELIMITADOR && karmaMatou > DELIMITADOR) {
                if (ClanLand.permission.playerHas(matador, "kom.bom")) {
                    ClanLand.permission.playerRemove(matador, "kom.bom");
                }
                ClanLand.permission.playerAdd(matador, "kom.mau");
                matador.sendMessage(ChatColor.GREEN + "Você se tornou uma pessoa de mau Karma.");
            }
        }

        Fama.manoloMata(matador, morreu);

    }

}
