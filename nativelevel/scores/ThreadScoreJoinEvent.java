package nativelevel.scores;

import nativelevel.mercadinho.Utils;
import nativelevel.titulos.ChatUtils;
import nativelevel.titulos.Titulos;
import static nativelevel.titulos.Titulos.trabalhaTitulo;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 *
 * @author gabripj
 */
public class ThreadScoreJoinEvent implements Runnable {

    Player p;

    public ThreadScoreJoinEvent(Player p)
    {
        this.p = p;
    }

    @Override
    public void run()
    {
        /*
        PermissionUser u = PermissionsEx.getPermissionManager().getUser(p);
        String prefixo = u.getPrefix();
        String sufixo = u.getSuffix();
        if ((sufixo != null && !sufixo.equals("")))
        {
            sufixo += " ";
        }
        prefixo = ChatUtils.translateColorCodes(prefixo);
        sufixo = ChatUtils.translateColorCodes(sufixo);
        final String prefixob = prefixo;
        final String sufixob = sufixo;
        ScoreboardManager.addToTeam(p.getName(), ChatColor.stripColor(prefixo), prefixob + "§f", sufixob, false);
                */
        
       
    }
}
