package nativelevel.titulos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import nativelevel.Jobs;
import nativelevel.karma.KarmaFameTables;
import nativelevel.mercadinho.Utils;
import nativelevel.rankings.RankCache;
import nativelevel.titulos.TituloDB.PData;
import nativelevel.scores.SBCoreListener;
import nativelevel.scores.ScoreboardManager;
import nativelevel.sisteminhas.ClanLand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 *
 * @author Carlos André Feldmann Júnior
 */
public class Titulos {

    public static void setTitulo(Player p, String titulo, ChatColor cor) {
        PData pd = TituloDB.getPlayerData(p);
        pd.setTitulo(titulo, cor);
        update(p, titulo, cor);
    }

    public static TreeMap<String, List<ChatColor>> getTitulos(Player p) {

        TreeMap<String, List<ChatColor>> titulos = new TreeMap(TituloDB.getTitulos(p.getUniqueId()));

        if (RankCache.souTopEm.containsKey(p.getUniqueId())) {
            List<ChatColor> cor = new ArrayList<ChatColor>();
            cor.add(ChatColor.AQUA);
            titulos.put(RankCache.souTopEm.get(p.getUniqueId()).titulo, cor);
        }

        if (KarmaFameTables.cacheTitulos.containsKey(p.getUniqueId())) {
            String titulo = KarmaFameTables.cacheTitulos.get(p.getUniqueId());
            if (!titulo.equalsIgnoreCase("")) {
                List<ChatColor> cor = new ArrayList<ChatColor>();
                cor.add(ChatColor.WHITE);
                titulos.put(titulo, cor);
            }
        }
        return titulos;
    }

    public static String trabalhaTitulo(String titulo, Player p, ChatColor cor) {
        if (titulo.equals("clan")) {
            // Clan c = ClanDB.getPlayerWithCache(p.getUniqueId()).getClan();
            // if (c != null) {
            //     return c.getTag();
            // } else {
            Titulos.setTitulo(p, "", ChatColor.WHITE);
            return null;

            // }
        }
        Sexo s = TituloDB.getPlayerData(p).getSexo();
        titulo = cor + s.getPrefix() + " " + s.feminiza(titulo);
        return titulo;

    }

    public static void update(Player p, String title, ChatColor c) {
        PermissionUser u = PermissionsEx.getPermissionManager().getUser(p);
        String prefixo = u.getPrefix();
        prefixo = ChatUtils.translateColorCodes(prefixo);
        String name = p.getName();
        if (title != null && !title.equals("")) {
            title = trabalhaTitulo(title, p, c);
        }
        if (name.length() > 12) {
            name = name.substring(0, 12);
        }
        name += Utils.randInt(1, 99);
        String bonus = "";
        if (p.hasPermission("kom.staff")) {
            bonus = "a";
        } else if (p.hasPermission("kom.vip")) {
            bonus = "b";
        } else {
            bonus = "c";
        }
        name = bonus + name;
        String r = "";
        if (!(prefixo == null || ChatColor.stripColor(prefixo).equals(""))) {
            r = "§f";
        }
        String sufix = (title != null && !title.equals("")) ? " " + title : "";
        ScoreboardManager.addToTeam(p.getName(), name, prefixo + r, sufix, false);
    } 

    static HashMap<UUID, ScoreCache> cache = new HashMap();

    public static class ScoreCache {

        public UUID uid;
        public String sufix;
        public String nomeequipe;
        public String prefix;

        public ScoreCache(UUID uid, String sufix, String nomeequipe, String prefix) {
            this.uid = uid;
            this.sufix = sufix;
            this.nomeequipe = nomeequipe;
            this.prefix = prefix;
        }

    }
}
