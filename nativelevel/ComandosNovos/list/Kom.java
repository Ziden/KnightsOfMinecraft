/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ComandosNovos.list;

import nativelevel.ComandosNovos.Comando;
import nativelevel.ComandosNovos.SubCmd;
import nativelevel.ComandosNovos.list.KomSubs.*;
import org.bukkit.command.CommandSender;

/**
 *
 * @author vntgasl
 *
 */
public class Kom extends Comando {

    public Kom() {
        super("kom2", CommandType.TODOS);
        subs.add(new CmdTeste());
        subs.add(new CmdHarvest());
        subs.add(new CmdCrafts());
        subs.add(new CmdPlants());
        subs.add(new CmdRecipes());
        subs.add(new CmdPotions());
        subs.add(new CmdCreateBook());
        subs.add(new CmdMercado());
        subs.add(new CmdOE());
        subs.add(new CmdItem());
        subs.add(new CmdLocal());
        subs.add(new CmdCraftings());
    }

    @Override
    public void usouComando(CommandSender cs, String[] args) {
        if(!cs.isOp())
            return;
        if (args.length > 0) {
            for (SubCmd cmd : subs) {
                if (cmd.cmd.equalsIgnoreCase(args[0])) {
                    cmd.execute(cs, args);
                }
            }
        } else {
            showSubCommands(cs);
        }

    }
}
