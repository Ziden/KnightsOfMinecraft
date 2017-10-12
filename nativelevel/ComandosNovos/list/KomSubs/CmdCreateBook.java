/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ComandosNovos.list.KomSubs;


import java.util.HashSet;
import nativelevel.Custom.Items.RecipeBook;
import nativelevel.ComandosNovos.Comando.CommandType;
import nativelevel.ComandosNovos.SubCmd;
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
public class CmdCreateBook extends SubCmd {
    
    public CmdCreateBook() {
        super("criarlivro", CommandType.OP);
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        String bookTypes = "";
        for(BookTypes type : BookTypes.values())
            bookTypes += ChatColor.GREEN+"|"+ChatColor.YELLOW+type.name();
       if(args.length!=2) {
           cs.sendMessage("Use /kom criarlivro "+bookTypes);
       } else {
           String bookType = args[1];
           try {
               BookTypes type = BookTypes.valueOf(bookType);
               ItemStack book = RecipeBook.createBook(type);
               ((Player)cs).getInventory().addItem(book);
               cs.sendMessage("Criado !");
           } catch(Exception e) {
               cs.sendMessage("Use /kom criarlivro "+bookTypes);
               e.printStackTrace();
               return;
           }
       }
    }
}
