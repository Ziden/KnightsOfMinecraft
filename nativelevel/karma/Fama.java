package nativelevel.karma;

import nativelevel.phatloots.events.MobDropLootEvent;
import nativelevel.phatloots.events.PlayerLootEvent;
import nativelevel.phatloots.events.PreMobDropLootEvent;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.lumine.xikage.mythicmobs.MythicMobs;
import nativelevel.utils.TitleAPI;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import me.asofold.bpl.simplyvanish.SimplyVanish;
import me.asofold.bpl.simplyvanish.config.VanishConfig;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.ArenaGuilda2x2.Elo;
import nativelevel.CFG;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Classes.Alchemy.Alchemist;
import nativelevel.Classes.Blacksmithy.Blacksmith;
import nativelevel.Classes.Engineer;
import nativelevel.Classes.Farmer;
import nativelevel.Classes.Lumberjack;
import nativelevel.Classes.Minerador;
import nativelevel.Classes.Paladin;
import nativelevel.Classes.Thief;
import nativelevel.Classes.Mage.Wizard;
import nativelevel.Comandos.Terreno;
import nativelevel.Custom.Buildings.Portal;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.Armadilha;
import nativelevel.Custom.Items.CapaInvisvel;
import nativelevel.Custom.Items.Ponte;
import nativelevel.Custom.Items.Detonador;
import nativelevel.Custom.Items.Encaixe;
import nativelevel.Custom.Items.LogoutTrap;
import nativelevel.Custom.Items.Runa;
import nativelevel.Custom.Items.SeguroDeItems;
import nativelevel.Custom.Items.SuperBomba;
import nativelevel.Dano;
import nativelevel.sisteminhas.Deuses;
import nativelevel.sisteminhas.Dungeon;
import static nativelevel.sisteminhas.Dungeon.LUZ_DO_ESCURO;
import nativelevel.ExecutaSkill;
import nativelevel.sisteminhas.Mobs;
import nativelevel.sisteminhas.Mobs.EfeitoMobs;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Language.LNG;
import nativelevel.Language.MSG;
import nativelevel.Language.TRL;
import nativelevel.Menu.Menu;
import nativelevel.Menu.netMenu;
import nativelevel.MetaShit;
import nativelevel.Listeners.Signs;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.Attributes.Mana;
import nativelevel.Attributes.Stamina;
import nativelevel.bencoes.TipoBless;
import nativelevel.integration.BungeeCordKom;
import nativelevel.integration.SimpleClanKom;
import nativelevel.integration.WorldGuardKom;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.spec.PlayerSpec;
import nativelevel.sisteminhas.BookPortal;
import nativelevel.utils.BookUtil;
import nativelevel.utils.BungLocation;
import nativelevel.sisteminhas.IronGolem;
import nativelevel.sisteminhas.Lobo;
import nativelevel.lojaagricola.LojaAgricola;
import nativelevel.skills.Skill;
import nativelevel.skills.SkillMaster;
import nativelevel.sisteminhas.XP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Pig;
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
//import org.bukkit.event.player.PlayerBeaconEvent;
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

public class Fama {

    public static void manoloMata(Player matador, LivingEntity morreu) {

        if(matador.getWorld().getName().equalsIgnoreCase("WoE") || matador.getWorld().getName().equalsIgnoreCase("Arena"))
            return;
        
        int famaMatou = KoM.database.getFama(matador.getUniqueId());
        int famaMorreu =  (ClanLand.getMobLevel(morreu.getLocation()) * 5) * 320 / 2;

        if (morreu.getType() == EntityType.PLAYER) {
            famaMorreu = KoM.database.getFama(morreu.getUniqueId());
        } else {
            if (morreu.hasMetadata("nivel")) {
                int nivelMob = (Integer) MetaShit.getMetaObject("nivel", morreu)+1 * 3;
                famaMorreu = famaMorreu * nivelMob;
            } else if (KoM.mm.getMobManager().isActiveMob(morreu.getUniqueId())) {
                famaMorreu = 500000;
            }
        }

        
        int famaGanho = (famaMorreu - famaMatou) / 100 / 2;
        if(famaGanho == 0)
            famaGanho = 1;
        
        if(famaGanho > 100)
            famaGanho = 100;
        int famaFinal = famaMatou + famaGanho;
        
        if(matador.isOp() && KoM.debugMode) {
            matador.sendMessage("Fama Mob: "+famaMorreu);
        }
        
        
        
        if (famaFinal < 0) {
            famaFinal = 0;
        }
        if (famaFinal > 32000) {
            famaFinal = 32000;
        }

        int diferencaFama = famaFinal - famaMatou;

        if (diferencaFama > 0) {
            matador.sendMessage(ChatColor.GREEN+"Fama: "+famaMatou+" "+ChatColor.GOLD+"+"+diferencaFama);
            KoM.database.setFama(matador.getUniqueId(), famaMatou + diferencaFama);
        }
    }

}
