package nativelevel.blococomando;

import java.util.List;
import nativelevel.KoM;
import nativelevel.sisteminhas.KomSystem;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author Ziden
 *
 */
public class BlocoComandoListener extends KomSystem {

    @Override
    public void onEnable() {

        Runnable r = new Runnable() {
            public void run() {

                KoM.log.info("!!!!!!!!  CARREGANDO BLOCOS DE COMANDO !!!!!");

                List<BlocoComando> comandos = KoM.database.getBlocosComando();

                KoM.log.info("Carreguei " + comandos.size() + " blocos de comando");

            }
        };
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, r, 20);

    }

    @EventHandler
    public void interage(PlayerInteractEvent ev) {

    }

}
