package nativelevel.ajuda;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nativelevel.KoM;
import nativelevel.ajuda.database.DBDefault;
import nativelevel.ajuda.database.MetodosRemover;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class KomAjuda {

    public static KoM pl;
    public static DBDefault db = new DBDefault();

    public static String f(String message) {
        String messageFormated = message.replace("&", "§");
        return "§a[Ajd] "+messageFormated;
    }

    public void onEnable() {
        pl = KoM._instance;
        new BukkitRunnable() {
            public void run() {
                KomAjuda.this.db.iniciarBanco();
            }
        }
        .runTaskLater(KoM._instance, 100L);

        Cmds comando = new Cmds(this);
        KoM._instance.getCommand("ajudantes").setExecutor(comando);
        KoM._instance.getCommand("ajuda").setExecutor(comando);
        KoM._instance.getCommand("responder").setExecutor(comando);
        KoM._instance.getCommand("perguntas").setExecutor(comando);
        KoM._instance.getServer().getPluginManager().registerEvents(new Listeners(), KoM._instance);
      
    }

    public void onDisable() {
    }

    public static void avisaPlayerDuvida(Player p) {
        try {
            ResultSet rs = KomAjuda.db.getConnection().createStatement().executeQuery("SELECT * FROM Pixel_Respostas WHERE playerOffline='" + p.getUniqueId() + "'");
            while (rs.next()) {
                p.sendMessage(" ");
                p.sendMessage(KomAjuda.f("&a&lChegou uma resposta!"));
                try {
                    p.sendMessage(KomAjuda.f("&7Pergunta: &6" + rs.getString("pergunta")));
                    p.sendMessage(" ");
                    p.sendMessage(KomAjuda.f("&aResposta: &e" + rs.getString("resposta")));
                    MetodosRemover.removerResposta(rs.getInt("id"));
                } catch (SQLException ex) {
                    Logger.getLogger(Listeners.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
