/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯//┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, Gabripj, Feldmann < O CARALHO... SOH O FELD Q AJUDOU, mas ele eh fdp
 Patrocionio: InstaMC << MEU PAU DE OCULOS

- FEITO
- Adicionado cooldown no Grito do Lenhador
- Nerfado stat Penetração de Armadura em 25%
- Tempo de Stun Máximo Agora é 60 (mesmo se tiver mais de 60, o máximo será 60)
- Adicionado um cooldown ao stun , ele é 2x o tempo da duração do stun.
- Quando ladino atirar uma flecha invisivel, uma fumaça maior aparece pra ser mais facil ver de onde veio a flecha
- Quando ladino atira uma flecha invisivel ele tem uma chance de 20% de ser revelado.
- Nerfada XP do Alquimista
- Paladinos, Ladinos e Magos ganham mais XP ao matar mobs !
- Nerfado loots dos baús de dungeons no geral
- Evitado dano de explosões na SafeZone
- Arrumado bug da coleira do engenheiro. Nao vai mais aparecer a coleira (nao funciona mais nessa versão, preciso ver outra alternativa)
- Arrumado bug onde era possível duplicar livros de receitas usando estantes
- Arrumado bug onde o seguro com 0 cargas fazia com que o jogador dropasse tudo.



fAZER:
- arrumar items bugados sem checaMods
- 

STAFF:
 - adicionar luz da graça na loja (1 cash)
 - rever as recompensas dos boss
 - sistema de conquistas
 - colocar mais debuff em dungeon


 - rever sistema de aprimoramentos, nao deixar aprimorar 2x etc etc

 fazer a porra do log de cmdblock
 NPC DESAPARECENDO PORRA



------ NEW CRIM
 - sistema de roubar loot 
 - usabilidade pra outras madeiras
 - pensar em algo para EYE_OF_ENDER
 - restricao de usar as skills aprendidas
 - nao mostrar vida de NPCS do citizens
 - fazer funfar os comandos do /f em ingles tb (f create)
 - adicionar todos comandos do /gld no /f
 */
package nativelevel;

import nativelevel.utils.LogsKom;
import nativelevel.sisteminhas.Boat;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.sisteminhas.Deuses;
import nativelevel.Listeners.GeneralListener;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import genericos.komzin.libzinha.InstaMCLibKom;
import genericos.komzin.libzinha.comandos.ComandoChatbb;
import genericos.komzin.libzinha.comandos.ComandoL;
import genericos.komzin.libzinha.comandos.ComandoR;
import genericos.komzin.libzinha.comandos.ComandoTell;
import io.lumine.xikage.mythicmobs.MythicMobs;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import me.blackvein.quests.Quests;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.arena1x1.PvPMatchmaking.PvPMatchmaking;
import nativelevel.ArenaGuilda2x2.Arena2x2;
import nativelevel.ArenaGuilda2x2.CmdArena;
import nativelevel.ArenaGuilda2x2.Eventos;
import nativelevel.Equipment.Generator.PreSet;
import nativelevel.Auras.Aura;
import nativelevel.Classes.Blacksmithy.RecipeLoader;
import nativelevel.Comandos.Botaschem;
import nativelevel.Comandos.CmdGemas;
import nativelevel.Comandos.CmdHub;
import nativelevel.Comandos.Cmdlimpaitem;
import nativelevel.Comandos.ComandoScore;
import nativelevel.Comandos.ConsoleItem;
import nativelevel.Comandos.Doar;
import nativelevel.Comandos.Guilda;
import nativelevel.Comandos.Kom;
import nativelevel.Comandos.Msgraio;
import nativelevel.Comandos.Msgzinha;
import nativelevel.Comandos.Spawn;
import nativelevel.Comandos.Tpraio;
import nativelevel.Comandos.KomExp;
import nativelevel.Crafting.CraftConfig;
import nativelevel.ComandosNovos.Comando;
import nativelevel.ComandosNovos.CommandLoader;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.ItemLoader;
import nativelevel.Custom.Items.BombaFumaca;
import nativelevel.Custom.Items.DeedTorre;
import nativelevel.Custom.Items.LockPick;
import nativelevel.Custom.Items.LogoutTrap;
import nativelevel.Custom.Items.TeleportScroll;
import nativelevel.Custom.PotionLoader;
import nativelevel.DataBase.SQL;
import nativelevel.Harvesting.HarvestConfig;
import nativelevel.Lang.L;
import nativelevel.Listeners.BlockListener;
import nativelevel.Listeners.DamageListener;
import nativelevel.Listeners.DeathEvents;
import nativelevel.Listeners.InteractEvents;
import nativelevel.Listeners.InventoryEvents;
import nativelevel.Listeners.PlayerEvents;
import nativelevel.Menu.netMenu;
import nativelevel.Planting.PlantConfig;
import nativelevel.Tasks.CleanupTask;
import nativelevel.Attributes.Mana;
import nativelevel.Attributes.MenuAtributos;
import nativelevel.Comandos.CmdAnuncio;
import nativelevel.Comandos.CmdFalar;
import nativelevel.Comandos.CmdGerador;
import nativelevel.Comandos.DarExp;
import nativelevel.Comandos.LimpaMobs;
import nativelevel.Comandos.SetarBloco;
import nativelevel.Comandos.Teleportar;
import nativelevel.ajuda.ComandoDislike;
import nativelevel.ajuda.ComandoLike;
import nativelevel.ajuda.KomAjuda;
import nativelevel.bencoes.TipoBless;
import nativelevel.config.Config;
import nativelevel.config.ConfigKom;
import nativelevel.oreGen.Gen;
import nativelevel.oreGen.Reflector;
import nativelevel.playerboolean.StageDB;
import nativelevel.precocabeca.Principal;
import nativelevel.rankings.RankDB;
import nativelevel.sc.bugfixes.SCBugFixes;
import nativelevel.gemas.SocketListener;
import nativelevel.komquista.KomQuista;
import nativelevel.lojaagricola.ConfigLoja;
import nativelevel.spec.PlayerSpec;
import nativelevel.titulos.CmdDarTitulo;
import nativelevel.titulos.CmdSexo;
import nativelevel.titulos.CmdTitulo;
import nativelevel.titulos.EscolheSexo;
import nativelevel.titulos.EscolheTitulo;
import nativelevel.titulos.TituloDB;
import nativelevel.utils.ConfigManager;
import nativelevel.sisteminhas.IpLog;
import nativelevel.sisteminhas.Lobo;
import nativelevel.sisteminhas.SystemLoader;
import nativelevel.lojaagricola.LojaAgricola;
import nativelevel.lojas.CmdSetPreco;
import nativelevel.phatloots.PhatLoots;
import nativelevel.rankings.RankCache;
import nativelevel.skills.SkillMaster;
import nativelevel.sisteminhas.Tesouros;
import nativelevel.scores.SBCoreListener;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandMap;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

/*     
 * …………………▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
 ……………▄▄█▓▓▓▒▒▒▒▒▒▒▒▒▒▓▓▓▓█▄▄
 …………▄▀▀▓▒░░░░░░░░░░░░░░░░▒▓▓▀▄
 ………▄▀▓▒▒░░░░░░░░░░░░░░░░░░░▒▒▓▀▄
 ……..█▓█▒░░░░░░░░░░░░░░░░░░░░░▒▓▒▓█
 …..▌▓▀▒░░░░░░░░░░░░░░░░░░░░░░░░▒▀▓█
 …..█▌▓▒▒░░░░░░░░░░░░░░░░░░░░░░░░░▒▓█
 …▐█▓▒░░░░░░░░░░░░░░░░░░░░░░░░░░░▒▓█▌
 …█▓▒▒░░░░░░░░░░░░░░░░░░░░░░░░░░░░▒▓█
 ..█▐▒▒░░░░░░░░░░░░░░░░░░░░░░░░░░░▒▒█▓█
 …█▓█▒░░░░░░░░░░░░░░░░░░░░░░░░░░░▒█▌▓█
 ..█▓▓█▒░░░░▒█▄▒▒░░░░░░░░░▒▒▄█▒░░░░▒█▓▓█
 ..█▓█▒░▒▒▒▒░░▀▀█▄▄░░░░░▄▄█▀▀░░▒▒▒▒░▒█▓█
 .█▓▌▒▒▓▓▓▓▄▄▄▒▒▒▀█░░░░█▀▒▒▒▄▄▄▓▓▓▓▒▒▐▓█
 .██▌▒▓███▓█████▓▒▐▌░░▐▌▒▓████▓████▓▒▐██
 ..██▒▒▓███▓▓▓████▓▄░░░▄▓████▓▓▓███▓▒▒██
 ..█▓▒▒▓██████████▓▒░░░▒▓██████████▓▒▒▓█
 ..█▓▒░▒▓███████▓▓▄▀░░▀▄▓▓███████▓▒░▒▓█
 ….█▓▒░▒▒▓▓▓▓▄▄▄▀▒░░░░░▒▀▄▄▄▓▓▓▓▒▒░▓█
 ……█▓▒░▒▒▒▒░░░░░░▒▒▒▒░░░░░░▒▒▒▒░▒▓█
 ………█▓▓▒▒▒░░██░░▒▓██▓▒░░██░░▒▒▒▓▓█
 ………▀██▓▓▓▒░░▀░▒▓████▓▒░▀░░▒▓▓▓██▀
 ………….░▀█▓▒▒░░░▓█▓▒▒▓█▓▒░░▒▒▓█▀░
 …………█░░██▓▓▒░░▒▒▒░▒▒▒░░▒▓▓██░░█
 ………….█▄░░▀█▓▒░░░░░░░░░░▒▓█▀░░▄█
 …………..█▓█░░█▓▒▒▒░░░░░▒▒▒▓█░░█▓█
 …………….█▓█░░█▀█▓▓▓▓▓▓█▀░░█░█▓█▌
 ……………..█▓▓█░█░█░█▀▀▀█░█░▄▀░█▓█
 ……………..█▓▓█░░▀█▀█░█░█▄█▀░░█▓▓█
 …………… …█▓▒▓█░░░░▀▀▀▀░░░░░█▓▓█
 …………… …█▓▒▒▓█░░░░ ░░░░█▓▓▓▓▓█
 ………………..█▓▒▓██▄█░░░▄░▄██▓▒▓█
 ………………..█▓▒▒▓█▒█▀█▄█▀█▒█▓▒▓█
 ………………..█▓▓▒▒▓██▒▒██▒██▓▒▒▓█
 ………………….█▓▓▒▒▓▀▀███▀▀▒▒▓▓█
 ……………………▀█▓▓▓▓▒▒▒▒▓▓▓▓█▀
 ………………………..▀▀██▓▓▓▓██▀ —
 * 
 * 


 ----- TODO ---------
 - Spawnar Mini Missoes no mundo GUILDA
 */
public class KoM extends JavaPlugin {

    public static final String tag = "§7§l[§6§lK§e§lo§6§lM§7§l]";
    public static String camila = "camila"; // S2
    public static boolean serverTestes = false;
    public static boolean debugMode = false;
    private GeneralListener eventos;
    public static List<Recipe> receitasCustom = new ArrayList<Recipe>();
    public static final HashMap<String, Integer> lvlTop = new HashMap<String, Integer>();
    public static KoM _instance = null;
    public static Gen generator = new Gen();
    public static Logger log;
    public static SQL database;
    public static MythicMobs mm;
    public static PhatLoots pl;
    public static Dano dano = new Dano();
    public static WorldGuardPlugin worldGuard;
    public static WorldEditPlugin worldEdit;
    public static LogsKom komLog = new LogsKom();
    public static ClanLand t;
    public static ConfigManager config;
    public static Plugin itemAttributes;
    public static Arena2x2 arena;
    public static PvPMatchmaking pvp;
    public static Quests quests;
    public static KomAjuda ajuda;
    public static KomQuista komq;

    // items custom
    public static HashMap<Block, Material> rewind = new HashMap<Block, Material>();
    IpLog iplog = new IpLog();
    //

    public static void debug(String m) {
        if (KoM.debugMode) {
            log.info("[DEBUG] " + m);
        }
    }

    public static void act(Entity p, String msg) {
        p.sendMessage(ChatColor.AQUA + "* " + msg + " *");
        for (Entity e : p.getNearbyEntities(12, 4, 12)) {
            if (e.getType() == EntityType.PLAYER) {
                ((Player) e).sendMessage(ChatColor.AQUA + "* " + msg + " *");
            }
        }
    }

   

    public static ItemStack addGlow(ItemStack item) {
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = null;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        }
        if (tag == null) {
            tag = nmsStack.getTag();
        }
        NBTTagList ench = new NBTTagList();
        tag.set("ench", ench);
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror(nmsStack);
    }

    private void startTasks() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(_instance, new CleanupTask(), CleanupTask.DELAY * 20, CleanupTask.DELAY * 20);
        Boat.timerStart();
    }

    public static long ENABLE_TIME;

    @Override
    public void onEnable() {

        SkillMaster.load();
        ENABLE_TIME = System.currentTimeMillis() / 1000;
        _instance = this;
        log = getLogger();
        String path = new File(".").getAbsolutePath();
        if (path.contains("testes")) {
            KoM.serverTestes = true;
            KoM.log.info("======= SERVER DE TESTES KOM ========");
            KoM.log.info("======= SERVER DE TESTES KOM ========");
            KoM.log.info("======= SERVER DE TESTES KOM ========");
        }

        quests = (Quests) Bukkit.getPluginManager().getPlugin("Quests");
        L.LoadLang();
        Aura.onEnable();
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        mm = (MythicMobs) Bukkit.getServer().getPluginManager().getPlugin("MythicMobs");
        log.info(L.m("iniciando saporra de server ! :D"));
        new ConfigKom();
        try {
            config = new ConfigManager(this.getDataFolder().getAbsolutePath() + "/ConfigKom.yml");
            if (config.getConfig().getString("database.name") == null || config.getConfig().getString("database.name") == "") {
                config.getConfig().set("database.name", "kom");
            }
            if (config.getConfig().getString("database.pass") == null || config.getConfig().getString("database.pass") == "") {
                config.getConfig().set("database.pass", "123batata");
            } else {

                camila = config.getConfig().getString("database.pass");
            }
            config.SaveConfig();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        komq = new KomQuista();
        komq.onEnable();
        worldEdit = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        worldGuard = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
        TipoBless.init();
        pvp = new PvPMatchmaking();
        pvp.onEnable();

        new HarvestConfig().onEnable();
        new CraftConfig().onEnable();
        new PlantConfig().onEnable();
        // aways night in dungeon world
        Runnable r = new Runnable() {
            @Override
            public void run() {
                World dun = Bukkit.getWorld("dungeon");
                if (dun != null) {
                    dun.setTime(18500L);
                }
                World vila = Bukkit.getWorld("vila");
                if (vila != null) {
                    vila.setTime(16000L);
                }
                //vila.setTime(3000L);
            }
        };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, r, 20 * 60 * 60, 20 * 60 * 60);
        /*
         Runnable r2 = new Runnable() {
         @Override
         public void run() {
         for (Player p : Bukkit.getOnlinePlayers()) {
         int[] temMax = database.getAlmasEMax(p.getUniqueId().toString());
         if (temMax != null && temMax[0] < 5)//temMax[1])
         {
         database.setAlmas(p.getUniqueId().toString(), temMax[0] + 1);
         p.sendMessage(ChatColor.GREEN + L.m("Sua alma se fortalece, e voce agora tem % pontos de alma", "" + (temMax[0] + 1)));
         }
         }
         }
         };
         */

        Runnable r4 = new Runnable() {
            @Override
            public void run() {
                Deuses.testa();
            }
        };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, r4, 20 * 60 * 60 * 2, 20 * 60 * 60 * 2);
        
        // Bukkit.getScheduler().scheduleSyncRepeatingTask(this, r2, 20 * 60 * 30, 20 * 60 * 30);
        Runnable r3 = new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    int[] temMax = database.getAlmasEMax(p.getUniqueId().toString());
                    if (temMax != null && temMax[0] < temMax[1]) {
                        database.setAlmas(p.getUniqueId().toString(), temMax[0] + 1);
                        p.sendMessage(ChatColor.GREEN + L.m("Sua alma se fortalece, e voce agora tem % pontos de alma", (temMax[0] + 1) + ""));
                    }
                }
            }
        };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, r3, 20 * 60 * 60, 20 * 60 * 60);
        database = new SQL(this);
        //iplog.onEnable();

        // LOADERS
        ItemLoader.load();
        SystemLoader.load();
        PotionLoader.load();
        RecipeLoader.load();
        loadCommands();
        PreSet.load();
        //

        pl = new PhatLoots();
        pl.onEnable();
        pl.reloadConfig();

        Mana.startRegenTimer();
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, r3, 20 * 60);
        new Principal(); // recompensas do lko
        LojaAgricola agricola = new LojaAgricola();

        agricola.onEnable();

        eventos = new GeneralListener(this);
        new ConfigLoja();
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerEvents(), _instance);
        Bukkit.getServer().getPluginManager().registerEvents(new InteractEvents(), _instance);

        Bukkit.getServer().getPluginManager().registerEvents(new BlockListener(), _instance);
        Bukkit.getServer().getPluginManager().registerEvents(new DamageListener(), _instance);

        Bukkit.getServer().getPluginManager().registerEvents(new DeathEvents(), _instance);

        Bukkit.getServer().getPluginManager().registerEvents(new InventoryEvents(), _instance);
        Bukkit.getServer().getPluginManager().registerEvents(new Tesouros(), _instance);
        Bukkit.getServer().getPluginManager().registerEvents(new MenuAtributos(), _instance);
        Bukkit.getServer().getPluginManager().registerEvents(PlayerSpec.Assassino, _instance);
        Bukkit.getServer().getPluginManager().registerEvents(agricola, _instance);

        SBCoreListener core = new SBCoreListener();
        Bukkit.getServer().getPluginManager().registerEvents(core, _instance);

        t = new ClanLand();
        t.onEnable();
        database.inicializa();

        ajuda = new KomAjuda();
        ajuda.onEnable();

        Bukkit.getPluginCommand("l").setExecutor(new ComandoL());
        Bukkit.getPluginCommand("tell").setExecutor(new ComandoTell());
        Bukkit.getPluginCommand("r").setExecutor(new ComandoR());
        
        Bukkit.getPluginCommand("chatbb").setExecutor(new ComandoChatbb());
        Bukkit.getPluginCommand("anuncio").setExecutor(new CmdAnuncio());
        Bukkit.getPluginCommand("like").setExecutor(new ComandoLike());
        Bukkit.getPluginCommand("dislike").setExecutor(new ComandoDislike());
        Bukkit.getPluginCommand("kom").setExecutor(new Kom());
        Bukkit.getPluginCommand("limpamobs").setExecutor(new LimpaMobs());
        Bukkit.getPluginCommand("gerador").setExecutor(new CmdGerador());
        Bukkit.getPluginCommand("setarbloco").setExecutor(new SetarBloco());
        Bukkit.getPluginCommand("teleportar").setExecutor(new Teleportar());
        Bukkit.getPluginCommand("darxp").setExecutor(new DarExp());
        Bukkit.getPluginCommand("falar").setExecutor(new CmdFalar());
        Bukkit.getPluginCommand("setpreco").setExecutor(new CmdSetPreco());
        Bukkit.getPluginCommand("verexp").setExecutor(new KomExp());
        Bukkit.getPluginCommand("msgzinha").setExecutor(new Msgzinha());
        Bukkit.getPluginCommand("msgzinha").setExecutor(new Msgzinha());
        Bukkit.getPluginCommand("doar").setExecutor(new Doar());
        Bukkit.getPluginCommand("lobo").setExecutor(new Lobo());
        Bukkit.getPluginCommand("spawn").setExecutor(new Spawn());
        Bukkit.getPluginCommand("guilda").setExecutor(new Guilda());
        Bukkit.getPluginCommand("lobby").setExecutor(new CmdHub());
        Bukkit.getPluginCommand("limpaitem").setExecutor(new Cmdlimpaitem());
        Bukkit.getPluginCommand("f").setExecutor(new Guilda());
        Bukkit.getPluginCommand("kompaste").setExecutor(new Botaschem());
        Bukkit.getPluginCommand("consoleitem").setExecutor(new ConsoleItem());
        Bukkit.getPluginCommand("tpraio").setExecutor(new Tpraio());
        Bukkit.getPluginCommand("msgraio").setExecutor(new Msgraio());
        Bukkit.getPluginCommand("arena").setExecutor(new CmdArena());
        Bukkit.getPluginCommand("score").setExecutor(new ComandoScore());
        Bukkit.getPluginCommand("genero").setExecutor(new CmdSexo());
        Bukkit.getPluginCommand("titulo").setExecutor(new CmdTitulo());
        Bukkit.getPluginCommand("dartitulo").setExecutor(new CmdDarTitulo());
        Bukkit.getPluginCommand("gemas").setExecutor(new CmdGemas());
        //Bukkit.getServer().getPluginManager().registerEvents(this, new IconMenu());
        this.addReceitas();
        try {
            /// SETTING ORE GENERATION
            if (System.getSecurityManager() != null) {
                Reflector.setStaticValue("java.lang.System", "security", null);
            }
        } catch (Exception e) {
            log.info("ERROR ON ORE GEN: " + e.getMessage());
        }
        generator.onEnable();
        msgBonitinha();
        timerBrilhos();
        startTasks();
        arena = new Arena2x2();
        arena.onEnable();
        Bukkit.getServer().getPluginManager().registerEvents(new Eventos(), _instance);
        Bukkit.getServer().getPluginManager().registerEvents(new netMenu(), _instance);
        Bukkit.getServer().getPluginManager().registerEvents(new SCBugFixes(), _instance);
        Bukkit.getServer().getPluginManager().registerEvents(new EscolheTitulo(), _instance);
        Bukkit.getServer().getPluginManager().registerEvents(new EscolheSexo(), _instance);
        Bukkit.getServer().getPluginManager().registerEvents(new SocketListener(), _instance);

        new StageDB().onEnable();
        TituloDB.InitMysql();

        KoM.database.loadLOOTS();

        //----------= Só código picudo =---------//
        InstaMCLibKom lib = new InstaMCLibKom();
        lib.onEnable();
        //---------------------------------------//

        new Config();

        RankDB.InitMysql();
        RankDB.calculaRankings();
        RankCache.loadTops();

    }

    private static CommandMap cmap;

    public void loadCommands() {
        try {
            if (Bukkit.getServer() instanceof CraftServer) {
                final Field f = CraftServer.class.getDeclaredField("commandMap");
                f.setAccessible(true);
                cmap = (CommandMap) f.get(Bukkit.getServer());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommandLoader.load();
    }

    public static void addCommand(Comando cmd) {
        cmap.register(cmd.getName(), cmd);
        cmd.setExecutor(KoM._instance);
    }

    public static boolean reiniciando = false;

    public static boolean inventoryContains(Inventory inventory, ItemStack item) {
        int count = 0;
        ItemStack[] items = inventory.getContents();
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getType() == item.getType() && items[i].getDurability() == item.getDurability()) {
                count += items[i].getAmount();
            }
            if (count >= item.getAmount()) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack geraEs(int qtas) {
        ItemStack esponja = new ItemStack(Material.SPONGE, qtas);
        ItemMeta meta = esponja.getItemMeta();
        meta.setDisplayName("§5♦ §6" + L.m("Moeda Magica"));
        List<String> lore = new ArrayList<String>();
        lore.add(0, "§9- " + L.m("Uma antiga moeda magica."));
        lore.add(1, "§9" + L.m("Pode ser usada para comprar diversos items especiais."));
        lore.add(2, "§9Season 1, por PixelMC");
        meta.setLore(lore);
        esponja.setItemMeta(meta);
        esponja.addUnsafeEnchantment(Enchantment.WATER_WORKER, 1);
        return esponja;
    }

    public static void msgUnica(Player p, String msg) {
        if (!p.hasMetadata(msg)) {
            p.sendMessage(msg);
            MetaShit.setMetaString(msg, p, "");
        }
    }

    @Override
    public void onDisable() {
        reiniciando = true;

        komq.onDisable();
        
        for (UUID u : GeneralListener.loots.keySet()) {
            List<ItemStack> items = GeneralListener.loots.get(u);
            ItemStack[] ss = items.toArray(new ItemStack[items.size()]);
            KoM.database.setLoot(u, ss);
        }

        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl.getItemOnCursor() != null && pl.getItemOnCursor().getType() != Material.AIR) {
                pl.setItemOnCursor(new ItemStack(Material.AIR));
            }
        }

        for (Block b : rewind.keySet()) {
            if (!b.getChunk().isLoaded()) {
                b.getChunk().load();
            }
            b.setType(rewind.get(b));
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getVehicle() != null) {
                p.getVehicle().eject();
            }
        }

        for (World w : Bukkit.getWorlds()) {
            for (Entity e : w.getEntities()) {
                if (e.getType() == EntityType.WOLF && ((Wolf) e).getOwner() != null) {
                    e.remove();
                }
            }
        }

        pl.onDisable();
        log.info("FEXANDO AS TAKS !!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.info("FEXANDO AS TAKS !!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.info("FEXANDO AS TAKS !!!!!!!!!!!!!!!!!!!!!!!!!!");
        for (BukkitTask task : Bukkit.getScheduler().getPendingTasks()) {
            try {
                if ((task.getOwner() == this || task.getOwner().getName().equalsIgnoreCase("KoM")) && task instanceof Runnable) {
                    ((Runnable) task).run();
                    log.info("Fexando task : " + task.getTaskId());
                }
            } catch (Exception e) {
                log.info("Deu pau pra fexar uma task ae...");
                e.printStackTrace();
            }
        }
        t.onDisable();
        //AutoDispenser.finaliza();

        iplog.onDisable();
        if (arena != null) {
            arena.onDisable();
        }
        pvp.onDisable();
        TipoBless.save.Save();
        RankDB.saveAll();
        database.fechaConexao();
        ajuda.onDisable();
        AutoUpdate.CheckUpdade("KomAtualizado.jar");
        AutoUpdate.CheckUpdade("KomQuista.jar");
        AutoUpdate.CheckUpdade("ArenaKom.jar");
    }

    public static void dealTrueDamage(LivingEntity p, double qto) {
        double v = p.getHealth();
        if (v - qto <= 0) {
            p.setHealth(0);
        } else {
            p.setHealth(v - qto);
        }
        if (p.getType() == EntityType.PLAYER) {
            KoM.dano.mostraDano((Player) p, qto, Dano.TOMEI);
        }
    }

    public static void removeInventoryItems(Inventory inv, Material type, int amount) {
        for (ItemStack is : inv.getContents()) {
            if (is != null && is.getType() == type) {
                int slot = inv.first(is.getType());
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                } else {
                    inv.setItem(slot, null);
                    amount = -newamount;
                    if (amount == 0) {
                        break;
                    }
                }
            }
        }
    }

    public static boolean gastaItem(Player p, Inventory inv, Material type, byte data) {
        for (ItemStack is : inv.getContents()) {
            if (is != null && is.getType() == type && is.getData().getData() == data) {
                if (is.getAmount() > 1) {
                    is.setAmount(is.getAmount() - 1);
                } else {
                    inv.setItem(inv.first(is), null);
                }
                p.updateInventory();
                return true;
            }
        }
        return false;
    }

    public static boolean gastaItem(Player p, ItemStack ss) {
        Inventory inv = p.getInventory();
        ItemStack[] c = inv.getContents();
        for (int x = 0; x < c.length; x++) {
            ItemStack is = c[x];
            if (is != null && is.isSimilar(ss) && is.getAmount()==ss.getAmount()) {
                c[x] = null;
                p.getInventory().setContents(c);
                p.updateInventory();
                return true;
            }
        }
        return false;
    }

    public static boolean gastaCustomItem(Player p, ItemStack item) {
        if (!p.getInventory().contains(item)) {
            return false;
        }
        p.getInventory().removeItem(item);
        return false;
    }

    public static boolean temCustomItem(Player p, ItemStack item) {
        return p.getInventory().contains(item);
    }

    public static boolean tem(Inventory i, Material m) {
        for (ItemStack ss : i.getContents()) {
            if (ss == null) {
                continue;
            }
            if (ss.getType() == m) {
                return true;
            }
        }
        return false;
    }

    public static boolean gastaReagentes(Player p, Inventory inv, int amount) {
        boolean gastou = false;
        String livro = CustomItem.getCustomItem(p.getItemInHand());
        if (livro != null && livro.equalsIgnoreCase(L.m("Livro das Almas"))) {
            return true;
        }
        for (ItemStack is : inv.getContents()) {
            if (is == null) {
                continue;
            }
            if (is.getType() == Material.POTION) {
                String customItem = CustomItem.getCustomItem(is);
                if (customItem == null || !customItem.equalsIgnoreCase(L.m("Pocao de Mana"))) {
                    continue;
                }
                gastou = true;
                int slot = inv.first(is);
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    //inv.setItem(slot, is);
                    break;
                } else {
                    inv.setItem(slot, new ItemStack(Material.AIR));
                    amount = -newamount;
                    if (amount == 0) {
                        break;
                    }
                }
            }
        }
        if (gastou) {
            p.updateInventory();
        }
        return gastou;
    }
    public static boolean safeMode = false;

    public static void efeitoBlocos(Entity e, Material m) {
        e.getLocation().getWorld().playEffect(e.getLocation().add(0.0D, 1D, 0.0D), Effect.STEP_SOUND, m.getId());
    }

    public static void efeitoBlocos(Location e, Material m) {
        e.getWorld().playEffect(e, Effect.STEP_SOUND, m.getId());
    }

    public static boolean ehOriginal(Player p) {
        UUID offlineUUID
                = UUID.nameUUIDFromBytes(("OfflinePlayer:" + p.getName()).getBytes());
        if (offlineUUID == p.getUniqueId() || offlineUUID.equals(p.getUniqueId())) {
            return false;
        }
        return true;
    }

    public static void efeitoBlocos(Block e, Material m) {
        e.getLocation().getWorld().playEffect(e.getLocation().add(0.5D, 5D, 0.5D), Effect.STEP_SOUND, m.getId());
    }

    public void addReceitas() {
        // CHAINMAIL
        /*
         ShapedRecipe r1 = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1)); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
         r1.shape("A A", "BBB", "AAA");
         r1.setIngredient('A', Material.LEATHER);
         r1.setIngredient('B', Material.IRON_INGOT);
         addReceita(r1);
         ShapedRecipe r2 = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_BOOTS, 1)); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
         r2.shape("   ", "A A", "B B");
         r2.setIngredient('A', Material.LEATHER);
         r2.setIngredient('B', Material.IRON_INGOT);
         addReceita(r2);
         ShapedRecipe r3 = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_HELMET, 1)); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
         r3.shape("AAA", "B B", "   ");
         r3.setIngredient('A', Material.LEATHER);
         r3.setIngredient('B', Material.IRON_INGOT);
         addReceita(r3);
         ShapedRecipe r4 = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1)); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
         r4.shape("AAA", "B B", "B B");
         r4.setIngredient('A', Material.LEATHER);
         r4.setIngredient('B', Material.IRON_INGOT);
         addReceita(r4);
         */

        // LOCKPICK
        ShapedRecipe recipeL = new ShapedRecipe(CustomItem.getItem(LockPick.class).generateItem()); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
        recipeL.shape("BAB", "BAB", "BAB");
        recipeL.setIngredient('A', Material.IRON_INGOT);
        recipeL.setIngredient('B', new MaterialData(Material.WOOD, (byte) 2));
        addReceita(recipeL);
        ShapedRecipe recipe2 = new ShapedRecipe(CustomItem.getItem(DeedTorre.class).generateItem()); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
        recipe2.shape("BAB", "BAB", "BAB");
        ItemStack ss = new ItemStack(Material.LOG_2, 1, (short) 0, (byte) 1);
        recipe2.setIngredient('A', new MaterialData(Material.LOG_2, (byte) 0));
        recipe2.setIngredient('B', Material.IRON_INGOT);
        addReceita(recipe2);
        ShapedRecipe recipe6 = new ShapedRecipe(CustomItem.getItem(LogoutTrap.class).generateItem()); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
        recipe6.shape("AAA", "ABA", "ABA");
        recipe6.setIngredient('A', Material.WOOD);
        recipe6.setIngredient('B', Material.IRON_INGOT);
        addReceita(recipe6);
        ShapedRecipe recipe7 = new ShapedRecipe(CustomItem.getItem(BombaFumaca.class).generateItem()); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
        recipe7.shape("AAA", "ABA", "AAA");
        recipe7.setIngredient('A', Material.PAPER);
        recipe7.setIngredient('B', Material.SULPHUR);
        addReceita(recipe7);
        ShapedRecipe recipe9 = new ShapedRecipe(CustomItem.getItem(nativelevel.Custom.Items.SlimePoison.class).generateItem()); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
        recipe9.shape("ABA", "BCB", "ABA");
        recipe9.setIngredient('A', Material.SPIDER_EYE);
        recipe9.setIngredient('B', Material.GRAVEL);
        recipe9.setIngredient('C', Material.SLIME_BALL);
        addReceita(recipe9);
        ShapedRecipe recipe10 = new ShapedRecipe(CustomItem.getItem(nativelevel.Custom.Items.Ank.class).generateItem()); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
        recipe10.shape("ABA", "BCB", "ABA");
        recipe10.setIngredient('A', Material.GOLDEN_APPLE);
        recipe10.setIngredient('B', Material.GOLD_INGOT);
        recipe10.setIngredient('C', Material.GOLD_BLOCK);
        addReceita(recipe10);

        /*
         ShapedRecipe recipe12 = new ShapedRecipe(CustomItem.getItem(nativelevel.Custom.Items.VenenoNatural.class).generateItem()); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
         recipe12.shape("   ", "BAB", " B ");
         recipe12.setIngredient('A', Material.POTATO_ITEM);
         recipe12.setIngredient('B', Material.GLASS);
         addReceita(recipe12);
         */
        ShapedRecipe recipe13 = new ShapedRecipe(CustomItem.getItem(nativelevel.Custom.Items.SuperBomba.class).generateItem()); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
        recipe13.shape("AAA", "BCB", "ADA");
        recipe13.setIngredient('A', Material.TNT);
        recipe13.setIngredient('B', Material.POWERED_RAIL);
        recipe13.setIngredient('C', Material.WATCH);
        recipe13.setIngredient('D', Material.DETECTOR_RAIL);
        addReceita(recipe13);
        ShapedRecipe recipe14 = new ShapedRecipe(TeleportScroll.createTeleportScroll(CFG.spawnTree, true, 3, "Pergaminho para Rhodes")); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
        recipe14.shape("AAA", "BCB", "ADA");
        recipe14.setIngredient('A', Material.SUGAR);
        recipe14.setIngredient('B', Material.SAND);
        recipe14.setIngredient('C', Material.PAPER);
        recipe14.setIngredient('D', Material.INK_SACK);
        addReceita(recipe14);
        ShapedRecipe recipe15 = new ShapedRecipe(CustomItem.getItem(nativelevel.Custom.Items.PeDeCabra.class).generateItem()); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
        recipe15.shape("AAA", "BBA", "BBA");
        recipe15.setIngredient('A', Material.IRON_INGOT);
        recipe15.setIngredient('B', new MaterialData(Material.LOG_2, (byte) 1));
        addReceita(recipe15);
        ShapedRecipe recipe16 = new ShapedRecipe(CustomItem.getItem(nativelevel.Custom.Items.BombaFarinha.class).generateItem()); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
        recipe16.shape(" A ", " B ", " C ");
        recipe16.setIngredient('A', Material.WOOD_BUTTON);
        recipe16.setIngredient('B', Material.BONE);
        recipe16.setIngredient('C', Material.FLOWER_POT_ITEM);
        addReceita(recipe16);

        ShapedRecipe recipe19 = new ShapedRecipe(CustomItem.getItem(nativelevel.Custom.Items.Runa.class).generateItem()); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
        recipe19.shape("EBE", "BRB", "EBE");
        recipe19.setIngredient('E', Material.ENDER_PEARL);
        recipe19.setIngredient('B', Material.BOOKSHELF);
        recipe19.setIngredient('R', Material.OBSIDIAN);
        addReceita(recipe19);
        ShapedRecipe recipe20 = new ShapedRecipe(new ItemStack(Material.BLAZE_ROD, 2)); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
        recipe20.shape("RRR", "III", "RRR");
        recipe20.setIngredient('R', Material.REDSTONE);
        recipe20.setIngredient('I', Material.IRON_INGOT);
        addReceita(recipe20);
        ShapedRecipe recipe21 = new ShapedRecipe(CustomItem.getItem(nativelevel.Custom.Items.Adaga.class).generateItem()); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
        recipe21.shape(" D ", " F ", "   ");
        recipe21.setIngredient('D', Material.DIAMOND);
        recipe21.setIngredient('F', Material.IRON_SWORD);
        addReceita(recipe21);

        ShapedRecipe recipe23 = new ShapedRecipe(CustomItem.getItem(nativelevel.Custom.Items.Lock.class).generateItem()); //Note: ABC is the bottom row, CBC is the middle row, BCB is the top row
        recipe23.shape(" D ", " F ", " A ");
        recipe23.setIngredient('D', Material.IRON_BLOCK);
        recipe23.setIngredient('F', Material.REDSTONE_BLOCK);
        recipe15.setIngredient('A', new MaterialData(Material.LOG_2, (byte) 0));
        addReceita(recipe23);

        ShapelessRecipe recipe22 = new ShapelessRecipe(CustomItem.getItem(nativelevel.Custom.Items.Atadura.class).generateItem(10));
        recipe22.addIngredient(Material.WOOL);
        addReceita(recipe22);
    }

    public void addReceita(Recipe recipe) {
        Bukkit.addRecipe(recipe);
        receitasCustom.add(recipe);
    }

    public final void abrePassagem(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getClickedBlock() == null) {
            return;
        }
        Block tocha = null;
        final Block suposTocha = event.getClickedBlock().getRelative(BlockFace.DOWN, 3);
        if (suposTocha.getType().equals(Material.REDSTONE_BLOCK)) {
            tocha = suposTocha;
        } else if (suposTocha.getRelative(BlockFace.DOWN).getType() == Material.REDSTONE_BLOCK || suposTocha.getRelative(BlockFace.DOWN).getType() == Material.REDSTONE_TORCH_ON) {
            tocha = suposTocha.getRelative(BlockFace.DOWN);
        } else {
            return;
        }
        Block piston = tocha.getRelative(BlockFace.UP);
        if (piston.getType() != Material.PISTON_BASE && piston.getType() != Material.PISTON_STICKY_BASE && piston.getType() != Material.PISTON_EXTENSION) {
            return;
        }
        Block air = tocha.getRelative(BlockFace.UP, 4);
        final int ida = air.getTypeId();
        final byte datai = air.getData();
        final Block txx = tocha;
        PlayEffect.play(VisualEffect.SPELL_INSTANT, air.getLocation().add(0.5, 0, 0.5), "num:1");
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                txx.setTypeIdAndData(ida, datai, false);
            }
        },
                1
        );
        tocha.getState()
                .update();
        tocha.getRelative(
                0, 1, 0).getState().update();
        tocha.setType(air.getType());
        air.setType(Material.AIR);
        KoM.rewind.put(tocha, Material.REDSTONE_BLOCK);
        delayFechar(tocha);

        /*
        
         if (event.getClickedBlock() == null)
         {
         return;
         }
         Block tocha = null;
         final Block suposTocha = event.getClickedBlock().getRelative(BlockFace.DOWN, 3);
         if (suposTocha.getType().equals(Material.REDSTONE_TORCH_ON) || suposTocha.getType().equals(Material.REDSTONE_BLOCK))
         {
         tocha = suposTocha;
         }
         else if (suposTocha.getRelative(BlockFace.DOWN).getType().equals(Material.REDSTONE_TORCH_ON) || suposTocha.getType().equals(Material.REDSTONE_BLOCK))
         {
         tocha = suposTocha.getRelative(BlockFace.DOWN);
         }
         else
         {
         return;
         }
         Block piston = tocha.getRelative(BlockFace.UP);
         if (piston.getType() != Material.PISTON_BASE && piston.getType() != Material.PISTON_STICKY_BASE && piston.getType() != Material.PISTON_EXTENSION)
         {
         return;
         }
         Block air = tocha.getRelative(BlockFace.UP, 4);
         KnightsOfMinecraft.rewind.put(air, air.getType());
         tocha.setTypeIdAndData(air.getTypeId(), air.getData(), false);
         PlayEffect.play(VisualEffect.SPELL, air.getLocation(), "num:2");
         tocha.setType(air.getType());
         KnightsOfMinecraft.rewind.put(tocha, Material.REDSTONE_BLOCK);
       
         air.setType(Material.AIR);
         delayFechar(tocha);
         */
    }

    public class fechaPassagem implements Runnable {

        private Block tox;

        public fechaPassagem(Block t) {
            tox = t;
        }

        @Override
        public void run() {
            PlayEffect.play(VisualEffect.FIREWORKS_SPARK, tox.getLocation().getBlock().getRelative(BlockFace.UP, 4).getLocation(), "num:8");
            tox.getLocation().getWorld().playEffect(tox.getLocation().getBlock().getRelative(BlockFace.UP, 4).getLocation(), Effect.FIREWORKS_SPARK, 1);

            tox.getLocation().getBlock().getRelative(BlockFace.UP, 4).setTypeIdAndData(tox.getTypeId(), tox.getData(), false);
            tox.getLocation().getBlock().setType(Material.REDSTONE_BLOCK);

            /*
             if ((tox.getLocation().getBlock().getType() == Material.REDSTONE_TORCH_OFF) || (tox.getLocation().getBlock().getType() == Material.REDSTONE_COMPARATOR_ON))
             {
             RedstoneTorch torch = (RedstoneTorch) tox.getLocation().getBlock().getState().getData();
             //torch.setFacingDirection(BlockFace.UP);
             }
             else
             {
             if(tox.getLocation().getBlock().getType()==Material.REDSTONE_BLOCK || tox.getLocation().getBlock().getType()==Material.REDSTONE_TORCH_ON) 
             return;
             KnightsOfMinecraft.log.severe("[KoM]: Fecha Porta deu Pau! X:" + tox.getLocation().getBlockX() + " Y:" + tox.getLocation().getBlockY() + " Z:" + tox.getLocation().getBlockZ());
             try
             {
             tox.getLocation().getBlock().setType(Material.REDSTONE_BLOCK);
             RedstoneTorch torch = (RedstoneTorch) tox.getLocation().getBlock().getState().getData();
             torch.setFacingDirection(BlockFace.UP);
             }
             catch (Exception e)
             {
             KnightsOfMinecraft.log.severe("[KoM]: Fecha Porta deu Pau 2x! X:" + tox.getLocation().getBlockX() + " Y:" + tox.getLocation().getBlockY() + " Z:" + tox.getLocation().getBlockZ());
             }
             }
             */
        }
    }

    public void delayFechar(final Block tocha) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new fechaPassagem(tocha), 40l);
    }

    public static void chutaBola(PlayerPickupItemEvent ev) {
        if (ev.getItem().getItemStack().getType() == Material.PUMPKIN) {
            if (ev.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.GRASS && ev.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.WOOL) {
                return;
            }
            Vector v = ev.getPlayer().getLocation().getDirection().normalize().multiply(1.1);
            v.setY(v.getY() + 0.3D);
            if (ev.getPlayer().isSneaking()) {
                v = new Vector(0, 0.35D, 0);
            }
            if (ev.getPlayer().isSprinting()) {
                v.multiply(1.1);
            } else {
                v.multiply(0.7);
            }
            ev.getItem().setVelocity(v);
            ev.setCancelled(true);
        }
    }
    public boolean[] attacking
            = {
                false, false, false, false
            };

    public Clan getClan(String s) {
        return ClanLand.getClan(s);
    }

    public static boolean hasRelation(Clan a, Clan b) {
        if (a == null || b == null) {
            return a == b;
        }
        return a.getTag().equals(b.getTag()) || a.isAlly(b.getTag());
    }

    public static void timerBrilhos() {
        /*
         Runnable r = new Runnable() {
         @Override
         public void run() {
         for (Player p : Bukkit.getOnlinePlayers()) {
         if (p.hasPermission("kom.vip")) {
         if (Kom.aurasJogadores.containsKey(p.getUniqueId())) {
         String[] s = Kom.aurasJogadores.get(p.getUniqueId()).split("!");
         if (KnightsOfMania.debugMode) {
         KnightsOfMania.log.info("tocando |" + s[0].trim() + " " + s[1].trim() + "|");
         }
         PlayEffect.play(s[0], s[1] + " loc:" + p.getLocation().getWorld().getName() + "," + p.getLocation().getBlockX() + "," + p.getLocation().getBlockY() + "," + p.getLocation().getBlockZ());
         }
         }
         }
         }
         };
         Bukkit.getScheduler().scheduleSyncRepeatingTask(KnightsOfMania._instance, r, 5, 5);
         */
    }

    public static void TeleportarTPBG(final Player p, final String servidor) {
        Bukkit.getScheduler().runTask(KoM._instance, new Runnable() {
            @Override
            public void run() {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                try {
                    out.writeUTF("ConnectOther");
                    out.writeUTF(p.getName());
                    out.writeUTF(servidor);
                } catch (IOException e) {
                }
                new PluginMessageTaskBungee(b, p).runTaskAsynchronously(KoM._instance);
            }
        });
    }

    public void msgBonitinha() {
        KoM.log.info("╭╮╭━╮╱╱╭━╮╭━╮╭━━━╮╱╱╭╮");
        KoM.log.info("┃┃┃╭╯╱╱┃┃╰╯┃┃┃╭━╮┃╱╱┃┃");
        KoM.log.info("┃╰╯╯╭━━┫╭╮╭╮┃┃┃╱┃┣━╮┃┃╭┳━╮╭━━╮");
        KoM.log.info("┃╭╮┃┃╭╮┃┃┃┃┃┃┃┃╱┃┃╭╮┫┃┣┫╭╮┫┃━┫");
        KoM.log.info("┃┃┃╰┫╰╯┃┃┃┃┃┃┃╰━╯┃┃┃┃╰┫┃┃┃┃┃━┫");
        KoM.log.info("╰╯╰━┻━━┻╯╰╯╰╯╰━━━┻╯╰┻━┻┻╯╰┻━━╯");
    }
}
