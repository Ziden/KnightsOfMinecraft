/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ComandosNovos.list.KomSubs;

import java.util.HashSet;
import nativelevel.Classes.Blacksmithy.CustomCrafting;
import nativelevel.ComandosNovos.Comando.CommandType;
import nativelevel.ComandosNovos.SubCmd;
import nativelevel.Custom.CustomPotion;
import nativelevel.Classes.Mage.MageSpell;
import nativelevel.RecipeBooks.BookTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author User
 */
public class CmdRecipes extends SubCmd {
    
    public CmdRecipes() {
        super("receitas", CommandType.OP);
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        String bookTypes = "";
        for(BookTypes type : BookTypes.values())
            bookTypes += ChatColor.GREEN+"|"+ChatColor.YELLOW+type.name();
       if(args.length!=2) {
           cs.sendMessage("Use /kom receitas "+bookTypes);
       } else {
           String bookType = args[1];
           try {
               BookTypes type = BookTypes.valueOf(bookType);
               if(type==BookTypes.Alquimia) {
                   CustomPotion.showRecipes(((Player)cs));
               } else if(type==BookTypes.Magia) {
                   MageSpell.showRecipes(((Player)cs));
               } else if(type==BookTypes.Ferraria) {
                   CustomCrafting.showRecipes(((Player)cs));
               } else {
                   cs.sendMessage("Este livro ainda nao tem receitas..");
               }
           } catch(Exception e) {
               cs.sendMessage("Use /kom receitas "+bookTypes);
               e.printStackTrace();
               return;
           }
       }
    }
}
