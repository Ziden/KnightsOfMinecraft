package nativelevel.ComandosNovos.list.KomSubs;

import java.util.ArrayList;
import java.util.List;
import nativelevel.KoM;
import nativelevel.MetaShit;
import nativelevel.ComandosNovos.Comando.CommandType;
import nativelevel.ComandosNovos.SubCmd;
import nativelevel.ComandosNovos.list.KomSubs.PlantSubs.CmdPlantAdd;
import nativelevel.ComandosNovos.list.KomSubs.PlantSubs.CmdPlantCheck;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author vntgasl
 */
public class CmdMostraChance extends SubCmd {

    public CmdMostraChance() {
        super("mostrachances", CommandType.PLAYER);
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if(cs instanceof Player) {
            Player p = (Player)cs;
           
        }
    }

}
