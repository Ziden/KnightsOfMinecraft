/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farmcraft.titulos;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class Cores {

    public static String getNome(ChatColor c) {
        if (c == ChatColor.WHITE) {
            return "Branco";
        }
        if (c == ChatColor.AQUA) {
            return "Azul Claro";
        }
        if (c == ChatColor.BLACK) {
            return "Preto";
        }
        if (c == ChatColor.BLUE) {
            return "Azul";
        }
        if (c == ChatColor.DARK_AQUA) {
            return "Ciano";
        }
        if (c == ChatColor.DARK_BLUE) {
            return "Azul Escuro";
        }
        if (c == ChatColor.DARK_GRAY) {
            return "Cinza Escuro";
        }
        if (c == ChatColor.DARK_GREEN) {
            return "Verde Escuro";
        }
        if (c == ChatColor.DARK_PURPLE) {
            return "Roxo";
        }
        if (c == ChatColor.DARK_RED) {
            return "Vermelho Escuro";
        }
        if (c == ChatColor.GOLD) {
            return "Dourado";
        }
        if (c == ChatColor.GRAY) {
            return "Cinza";
        }
        if (c == ChatColor.GREEN) {
            return "Verde";
        }
        if (c == ChatColor.LIGHT_PURPLE) {
            return "Rosa";
        }
        if (c == ChatColor.RED) {
            return "Vermelho";
        }
        if (c == ChatColor.YELLOW) {
            return "Amarelo";
        }
        return "????";
    }

    public static MaterialData getData(ChatColor c) {
        Material m = Material.DIRT;
        byte data = 0;
        if (c == ChatColor.WHITE) {
            m = Material.SNOW_BLOCK;
        }
        if (c == ChatColor.AQUA) {
            m = Material.DIAMOND_BLOCK;
        }
        if (c == ChatColor.BLACK) {
            m = Material.COAL_BLOCK;
        }
        if (c == ChatColor.BLUE) {
            m = Material.STAINED_CLAY;
            data = 3;
        }
        if (c == ChatColor.DARK_AQUA) {
            m = Material.WOOL;
            data = 9;
        }
        if (c == ChatColor.DARK_BLUE) {
            m = Material.LAPIS_BLOCK;
        }
        if (c == ChatColor.DARK_GRAY) {
            m = Material.WOOL;
            data = 7;
        }
        if (c == ChatColor.DARK_GREEN) {
            m = Material.STAINED_CLAY;
            data = 13;
        }
        if (c == ChatColor.DARK_PURPLE) {
            m = Material.WOOL;
            data = 10;
        }
        if (c == ChatColor.DARK_RED) {
            m = Material.NETHERRACK;
        }
        if (c == ChatColor.GOLD) {
            m = Material.GOLD_BLOCK;
        }
        if (c == ChatColor.GRAY) {
            m = Material.STONE;
        }
        if (c == ChatColor.GREEN) {
            m = Material.EMERALD_BLOCK;
        }
        if (c == ChatColor.LIGHT_PURPLE) {
            m = Material.WOOL;
            data = 2;
        }
        if (c == ChatColor.RED) {
            m = Material.STAINED_CLAY;
            data = 14;
        }
        if (c == ChatColor.YELLOW) {
            data = 4;
            m = Material.WOOL;
        }
        return new MaterialData(m, data);
    }

}
