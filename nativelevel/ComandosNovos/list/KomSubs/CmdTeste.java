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
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;

/**
 *
 * @author vntgasl
 */
public class CmdTeste extends SubCmd {

    public CmdTeste() {
        super("teste", CommandType.PLAYER);
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            Entity pig = p.getWorld().spawnEntity(p.getLocation(), EntityType.fromName(args[1]));
            ((LivingEntity)pig).setLeashHolder(p);
            p.addPassenger(pig);
            p.sendMessage("Teste Feito");
        }
    }

}
