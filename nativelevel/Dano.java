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
package nativelevel;

import java.text.DecimalFormat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Dano {

    public static final int TOMEI = 1;
    public static final int BATI = 2;
    public static final int CUREI = 3;

    short i1 = 60;
    short i2 = 1;
    public static DecimalFormat df = new DecimalFormat("#.0");

    public void mostraDano(Player p, double dano, int tipoDano) {
        if (dano == 0) {
            return;
        }
        if (tipoDano == Dano.BATI) {
            if (KoM.debugMode) {
                KoM.log.info(p.getName() + " deu " + dano + " de dano");
            }
            if (p.hasMetadata("mostradano")) {
                p.sendMessage(ChatColor.YELLOW + "" + df.format(dano));
            }
            // final org.getspout.spoutapi.gui.Widget exp = new GenericLabel("" + dano).setTextColor(new Color(1.0F, 1.0F, 0.0F, 1.0F)).setHeight(10).setWidth(20).setAnchor(WidgetAnchor.CENTER_CENTER).shiftXPos(rand).shiftYPos(-10);
            // p.getMainScreen().attachWidget(NativeLevel.instanciaDoPlugin, exp);
            // exp.animate(org.getspout.spoutapi.gui.WidgetAnim.POS_Y, -1.5F,i1, i2, false, false).animateStart();
            // Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(NativeLevel.instanciaDoPlugin, new Limpador(p, exp), 20L);
        } else if (tipoDano == Dano.TOMEI) {
            if (KoM.debugMode) {
                KoM.log.info(p.getName() + " tomou " + dano + " de dano");
            }
            if (p.hasMetadata("mostradano")) {
                p.sendMessage(ChatColor.RED + "" + df.format(dano));
            }
            // org.getspout.spoutapi.gui.Widget exp = new GenericLabel("" + dano).setTextColor(new Color(1.0F, 0.0F, 0.0F, 1.0F)).setHeight(10).setWidth(20).setAnchor(WidgetAnchor.CENTER_CENTER).shiftXPos(rand).shiftYPos(20).animate(org.getspout.spoutapi.gui.WidgetAnim.POS_Y, 1F, i1, i2, false, false).animateStart();
            // p.getMainScreen().attachWidget(NativeLevel.instanciaDoPlugin, exp);
            // Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(NativeLevel.instanciaDoPlugin, new Limpador(p, exp), 20L);
        }
    }
}
