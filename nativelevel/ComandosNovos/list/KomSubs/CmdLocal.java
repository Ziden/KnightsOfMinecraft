/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ComandosNovos.list.KomSubs;

import java.util.ArrayList;
import java.util.List;
import nativelevel.ComandosNovos.Comando.CommandType;
import nativelevel.ComandosNovos.SubCmd;
import nativelevel.ComandosNovos.list.KomSubs.HarvestSubs.CmdHarvestAdd;
import nativelevel.ComandosNovos.list.KomSubs.HarvestSubs.CmdHarvestCheck;
import nativelevel.ComandosNovos.list.KomSubs.PlantSubs.CmdPlantAdd;
import nativelevel.ComandosNovos.list.KomSubs.PlantSubs.CmdPlantCheck;
import nativelevel.Custom.Items.BussolaMagica;
import nativelevel.Custom.Items.BussolaMagica.LocalBussola;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.utils.LocUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author vntgasl
 * 
 */

public class CmdLocal extends SubCmd {

    public CmdLocal() {
        super("locais", CommandType.OP);
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length == 2 && args[1].equalsIgnoreCase("listar")) {

            List<LocalBussola> locais = KoM.database.getLocais();

            for (LocalBussola loc : locais) {
                cs.sendMessage(loc.nome + " lvl " + loc.nivel);
            }
            
        } else if (args.length == 3) {

            if (args[1].equalsIgnoreCase("remove")) {
                String nome = args[2];
                LocalBussola local = BussolaMagica.getLocal(nome);
                KoM.database.removeLocal(local);
                cs.sendMessage("Local removido");
            } else if (args[1].equalsIgnoreCase("tp")) {
                String nome = args[2];
                LocalBussola local = BussolaMagica.getLocal(nome);
                Player p = (Player) cs;
                p.teleport(LocUtils.str2loc(local.local));
            }

        } else if (args.length == 4) {

            Player p = (Player) cs;

            String nome = args[2];
            int nivel = Integer.valueOf(args[3]);

            LocalBussola local = new LocalBussola();
            local.local = LocUtils.loc2str(p.getLocation());
            local.nivel = nivel;
            local.nome = nome;
            KoM.database.addLocal(local);

        } else {
            
            cs.sendMessage("___ locais no mundo de guildas ___");
            cs.sendMessage("/kom2 locais listar");
            cs.sendMessage("/kom2 locais add <nome> <nivel>");
            cs.sendMessage("/kom2 locais remove <nome>");
            cs.sendMessage("/kom2 locais tp <nome>");
            
        }
    }

}
