/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.sisteminhas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import nativelevel.Menu.Menu;
import nativelevel.KoM;
import nativelevel.utils.jnbt.ByteTag;
import nativelevel.utils.jnbt.CompoundTag;
import nativelevel.utils.jnbt.IntTag;
import nativelevel.utils.jnbt.ListTag;
import nativelevel.utils.jnbt.NBTInputStream;
import nativelevel.utils.jnbt.ShortTag;
import nativelevel.utils.jnbt.Tag;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.permissions.Permission;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 *
 * @author USER
 * 
 */

public class Html {

    /*  String h ="<html>";
     h+="<table border=\"1\" style=\"width:100%\">";
     h+="<tr>";
                
     h+= "<td>Nome</td>";
     h+= "<td>Tag</td>";
     h+= "<td>Poder</td>";
                
     h+="<td>Numero de membros</td>";
     h+="<td>Lideres</td>";
     h+="</tr>";
     */
    public static void geraPlayerRank() {

        String h = "<html>";
        FileWriter fstream = null;
        try {

            fstream = new FileWriter("plugins" + File.separator + "kom" + File.separator + "site" + File.separator + "PlayerRank.html");
            BufferedWriter out = new BufferedWriter(fstream);

            h += "<head>";
            h += "<meta charset=\"utf-8\">";
            h += "<title> Rank de players</title>";
            h += "<link rel=\"stylesheet\" href=\"./styler.css\">";
            h += "<link href=\"./favicon.ico\" rel=\"shortcut icon\" type=\"image/x-icon\" />";
            h += "</head>";
            h += "<body>";
            h += "<img src=\"http://www.knightsofminecraft.com.br/img/company-logo.png\" class=\"logo\" alt=\"Logo KoM\"></h1>";
            h += "<h2>Top Levels</h2>";
            h += "<table border=1 class=\"topl\">";
            ArrayList<KomPlayer> toplevel = new ArrayList();
            ArrayList<KomPlayer> topeme = new ArrayList();
            ArrayList<KomPlayer> topkdr = new ArrayList();
            HashMap<String,String> negada =  KoM.database.getUuidsAndNames();
            for (String s : negada.keySet()) {
                String uuid = s;
                String name = negada.get(s);
                if(!new File("vila" + File.separator + "playerdata" + File.separator + uuid + ".dat").exists()){
                    break;
                }
                KomPlayer km = new KomPlayer(uuid, name);
                toplevel.add(km);
                topeme.add(km);
                topkdr.add(km);
            }
            Collections.sort(toplevel, new Comparator<KomPlayer>() {
                public int compare(KomPlayer one, KomPlayer other) {
                    return other.getLevel() - one.getLevel();
                }
            });
            Collections.sort(topkdr, new Comparator<KomPlayer>() {
                public int compare(KomPlayer one, KomPlayer other) {
                    if (one.getKdR() < other.getKdR()) {
                        return 1;
                    } else if (one.getKdR() == other.getKdR()) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
            Collections.sort(topeme, new Comparator<KomPlayer>() {
                public int compare(KomPlayer one, KomPlayer other) {
                    return other.getEmeralds() - one.getEmeralds();
                }
            });
            h += "<tr>";
            h += "<td class =\"cabe\">Lugar</td>";
            h += "<td class =\"cabe\">Nome do jogador</td>";
            h += "<td class =\"cabe\">Level do jogador</td>";
            h += "</tr>";

            for (int y = 0; y < toplevel.size(); y++) {
                if (y == 50) {
                    break;

                }
                KomPlayer kp = toplevel.get(y);
                h += "<tr>";
                h += "<td>" + (y + 1) + "</td>";
                h += "<td>" + "<a href=\"./jogadores/" + kp.getName() + ".html\"\">" + kp.getName() + "</a>" + "</td>";
                h += "<td>" + kp.getLevel() + "</td>";
                h += "</tr>";
            }
            h += "</table><br>";

            h += "<h2>Top Money</h2>";

            h += "<table border=1 class=\"topl\">";
            h += "<tr>";
            h += "<td class =\"cabe\">Lugar</td>";
            h += "<td class =\"cabe\">Nome do jogador</td>";
            h += "<td class =\"cabe\">Dinheiro no Banco</td>";
            h += "</tr>";

            for (int y = 0; y < topeme.size(); y++) {
                if (y == 50) {
                    break;

                }
                KomPlayer kp = topeme.get(y);
                h += "<tr>";
                h += "<td>" + (y + 1) + "</td>";
                h += "<td>" + "<a href=\"./jogadores/" + kp.getName() + ".html\"\">" + kp.getName() + "</a>" + "</td>";
                h += "<td>" + kp.getEmeralds() + "</td>";
                h += "</tr>";
            }
            h += "</table><br>";
            h += "<h2>Top KdR</h2>";

            h += "<table border=1 class=\"topl\">";
            h += "<tr>";
            h += "<td class =\"cabe\">Lugar</td>";
            h += "<td class =\"cabe\">Nome do jogador</td>";
            h += "<td class =\"cabe\">KdR</td>";
            h += "</tr>";

            for (int y = 0; y < topkdr.size(); y++) {
                if (y == 50) {
                    break;

                }
                KomPlayer kp = topkdr.get(y);
                h += "<tr>";
                h += "<td>" + (y + 1) + "</td>";
                h += "<td>" + "<a href=\"./jogadores/" + kp.getName() + ".html\"\">" + kp.getName() + "</a>" + "</td>";
                h += "<td>" + kp.getKdR() + "</td>";
                h += "</tr>";
            }
            h += "</table>";

            h += "</body>";
            h += "</html>";
            out.write(h);
            out.close();
            fstream.close();
            h = "<html>";

        } catch (IOException ex) {
            ex.printStackTrace();
            KoM.log.info(ex.getMessage());
        } finally {
           
        }

    }

    public static int getLevel(String uuid) {
        try {
            FileInputStream fis = new FileInputStream("vila" + File.separator + "playerdata" + File.separator + uuid + ".dat");
            NBTInputStream input = new NBTInputStream(fis);

            CompoundTag compound = (CompoundTag) input.readTag();
            Map<String, Tag> tags = compound.getValue();

            int lvl = ((IntTag) tags.get("XpLevel")).getValue();
            fis.close();
            input.close();
            return lvl;

        } catch (IOException ex) {
            ex.printStackTrace();
            KoM.log.info(ex.getMessage());
        }
        return 0;
    }

    public static void geraPlayer() {

        HashMap<String, String> negada = KoM.database.getUuidsAndNames();
        for (String s : negada.keySet()) {
            
            //OfflinePlayer of = Bukkit.getOfflinePlayer(UUID.fromString(s));
            String name = negada.get(s);
            String uuid = s;
            KoM.log.info("processando player "+name+" "+s);
            String h = "<html>";
            FileWriter fstream = null;
            ClanPlayer cp = ClanLand.manager.getClanPlayer(UUID.fromString(uuid));
            try {
                if(!new File("vila" + File.separator + "playerdata" + File.separator + uuid + ".dat").exists()){
                    break;
                }
                File playerfile = new File("plugins" + File.separator + "kom" + File.separator + "site" + File.separator + "jogadores" + File.separator + name + ".html");
               if(!playerfile.exists()){
                   playerfile.createNewFile();
               }   
                fstream = new FileWriter("plugins" + File.separator + "kom" + File.separator + "site" + File.separator + "jogadores" + File.separator + name + ".html");
                KoM.log.info("Arquivo criado");
                BufferedWriter out = new BufferedWriter(fstream);
                
                String clan = "Nao tem";
                String tag = "";
                String kdr = "0.0";
                String ex = "Nao tem";
                if (cp != null) {
                    if (cp.getClan() != null) {
                        clan = cp.getClan().getName();
                        tag = cp.getClan().getTag();
                        ex = "<a href=\"../guildas/" + tag + ".html\"\">" + clan + "</a>" + "</td>";
                    }
                    kdr = String.valueOf(cp.getKDR());

                }
                KoM.log.info("obtive clan");
                h += "<head>";
                h += "<meta charset=\"utf-8\">";
                h += "<title>Jogador " + name+ "</title>";
                h += "<link rel=\"stylesheet\" href=\"../stylep.css\">";
                h += "<link href=\"../favicon.ico\" rel=\"shortcut icon\" type=\"image/x-icon\" />";
                h += "</head>";
                h += "<body>";
                h += "<img src=\"http://www.knightsofminecraft.com.br/img/company-logo.png\" class=\"logo\" alt=\"Logo KoM\"></h1>";

                h += "<h2>" + name+ "</h2>";
                h += "<img src=\"https://minotar.net/helm/" + name + "/150.png\"\" class=\"logo\" alt=\"Cara de " + name + "\"></h1><br>";

                h += "<table class =\"info\" border=\"4px\">";
                KoM.log.info("Escrevi headers");
                h += "<tr>";
                h += "<td class=\"cabe\"> Dinheiro no Banco</td>";
                h += "<td>" + Calcula(uuid) + "</td>";
                h += "</tr>";
                KoM.log.info("Calculei grana no banco");
                if (cp != null) {
                    h += "<tr>";
                    h += "<td class=\"cabe\"> Guildas que passou</td>";
                    h += "<td>" + cp.getPackedPastClans() + "</td>";
                    h += "</tr>";
                }
                h += "<tr>";
                h += "<td class=\"cabe\">Clan atual</td>";
                h += "<td>" + ex + "</td>";
                h += "</tr>";

                h += "<tr>";
                h += "<td class=\"cabe\">Classes</td>";
                if (KoM.database.hasRegisteredClass(uuid)) {
                    h += "<td>" + TraduzClasses(KoM.database.getSkills(uuid)) + "</td>";
                } else {
                    h += "<td>Nao escolheu</td>";
                }
                KoM.log.info("Traduzi classes");
                h += "</tr>";
                h += "<tr>";
                h += "<td class=\"cabe\">KdR</td>";
                h += "<td>" + kdr + "</td>";
                h += "</tr>";
                h += "</tr>";
                h += "<tr>";
                //h += "<td class=\"cabe\">Vip</td>";
               // PermissionUser pu = PermissionsEx.getUser(name);
               // String g = pu.has("kom.vip") ? "Sim" : "Nao";
               // h += "<td>" + g + "</td>";
                h += "<td class=\"cabe\">Deus Favorito</td><td>Jabu</td>";
                h += "</tr>";
                h += "<tr>";
                h += "<td class=\"cabe\">Level</td>";
                h += "<td>" + getLevel(uuid) + "</td>";
                h += "</tr>";
                h += "</table>";
                h += "</body>";
                h += "</html>";
                out.write(h);
                out.close();
                fstream.close();
                KoM.log.info("Finalizei");
            } catch (IOException ex) {
                ex.printStackTrace();
                KoM.log.info(ex.getMessage());
            } finally {
             
            }
        }
    }

    public static int Calcula(String uuid) {
        int r = 0;
        try {
            FileInputStream fis = new FileInputStream("vila" + File.separator + "playerdata" + File.separator + uuid + ".dat");
            NBTInputStream input = new NBTInputStream(fis);

            CompoundTag compound = (CompoundTag) input.readTag();
            Map<String, Tag> tags = compound.getValue();
            ListTag bukkit = (ListTag) tags.get("EnderItems");

            for (Tag g : bukkit.getValue()) {

                short id = ((ShortTag) ((Map) g.getValue()).get("id")).getValue();
                byte cont = ((ByteTag) ((Map) g.getValue()).get("Count")).getValue();
                if (id == 388) {
                    r += cont;

                } else if (id == 133) {
                    r += (cont) * 9;

                }

            }
            fis.close();
            input.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            KoM.log.info(ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            KoM.log.info(ex.getMessage());
        }

        return r;
    }

    public static String TraduzClasses(int[] s) {
        String a = "";
        for (int x = 0; x < 10; x++) {
            int level = s[x];
            if (level == 2) {
                if (level == 2) {
                    level = 1;
                } else if (level == 1) {
                    level = 2;
                }
                a += level + "-" + Menu.getNome(x) + "<br>";
            }
        }
        return a;
    }

    public static void gera() {

        Thread t = new Thread() {
            public void run() {
                long co = System.currentTimeMillis();
                try {
                    File glds = new File("plugins" + File.separator + "kom" + File.separator + "site" + File.separator + "guildas");
                    if (!glds.exists()) {
                        glds.mkdirs();
                    } else {
                        glds.delete();
                    }
                    glds.mkdirs();
                    File pasta = new File("plugins" + File.separator + "kom" + File.separator + "site" + File.separator + "jogadores");
                    if (!pasta.exists()) {
                        pasta.mkdirs();
                    } else {
                        pasta.delete();
                    }
                    pasta.mkdirs();
                    geraPlayer();
                    geraPlayerRank();
                    geraRankGuildas();
                    for (Clan c : ClanLand.manager.getClans()) {
                        KoM.log.info("[Html] Processando guilda "+c.getTag());
                        String h = "<html>";
                        FileWriter fstream = null;
                        try {

                            fstream = new FileWriter("plugins" + File.separator + "kom" + File.separator + "site" + File.separator + "guildas" + File.separator + c.getTag() + ".html");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            KoM.log.info(ex.getMessage());
                        }
                        BufferedWriter out = new BufferedWriter(fstream);
                        h += "<head>";
                        h += "<meta charset=\"utf-8\">";
                        h += "<title>Guilda " + c.getName() + "</title>";
                        h += "<link rel=\"stylesheet\" href=\"../style.css\">";
                        h += "<link href=\"../favicon.ico\" rel=\"shortcut icon\" type=\"image/x-icon\" />";
                        h += "</head>";
                        h += "<body>";
                        h += "<img src=\"http://www.knightsofminecraft.com.br/img/company-logo.png\" class=\"logo\" alt=\"Logo KoM\"></h1>";

                        h += "<p id=\"guilda\">" + c.getName().toUpperCase() + "<br>" + c.getTag().toUpperCase() + "</p>";
                        h += "<table class=\"info\" border=\"1\">";
                        h += "</tr>";
                        h += "<tr>";
                        h += "<td class=\"cabe\">Terrenos</td>";
                        h += "<td style=\"width:250px\">" + ClanLand.getQtdTerrenos(c.getTag()) + "</td>";

                        h += "</tr>";

                        h += "<tr>";
                        h += "<td class=\"cabe\">Poder</td>";
                        h += "<td style=\"width:250px\">" + ClanLand.getPoder(c.getTag()) + "</td>";
                        h += "</tr>";
                        h += "<tr>";
                        h += "<td class=\"cabe\">KDR Da Guilda</td>";
                        h += "<td style=\"width:250px\">" + c.getTotalKDR() + "</td>";
                        h += "</tr>";

                        h += "<tr>";
                        h += "<td class=\"cabe\">Numero de Membros</td>";
                        h += "<td style=\"width:250px\">" + c.getMembers().size() + "</td>";

                        h += "</table>";
                        h += "<h2> Membros</h2>";
                        h += "<table class=\"members\" border=\"1\" style=\"width:80%\">";
                        h += "<tr class=\"cabe\">";
                        h += "<th>Membro</th>";
                        h += "<th>Cargo</th>";
                        h += "<th>KDR</th>";

                        h += "</tr>";
                        for (ClanPlayer cp : c.getAllMembers()) {
                            h += "<tr>";
                            h += "<td>" + "<a href=\"../jogadores/" + cp.getName() + ".html\"\">" + cp.getName() + "</a>" + "</td>";
                            h += "<td>" + getCargo(cp) + "</td>";
                            h += "<td>" + cp.getKDR() + "</td>";
                            h += "</tr>";
                        }
                        h += "</table>";
                        h += "<h2>Rivais</h2>";
                        h += "<table class=\"members\" border=\"1\" style=\"width:80%\">";
                        h += "<tr >";
                        h += "<th>Guilda</th>";
                        h += "<th style=\"width:30px\">PPs</th>";
                        h += "</tr>";
                        for (Clan cs : ClanLand.manager.getClans()) {
                            if (cs != c) {
                                if (cs.isRival(c.getTag())) {
                                    h += "<td> <a href=\"../guildas/" + cs.getTag() + ".html\"\">" + cs.getName() + " - " + cs.getTag() + "</a></td>";
                                    h += "<td style=\"width:30px\">" + ClanLand.getPtosPilagem(c.getTag(), cs.getTag()) + "</td>";
                                }

                            }
                        }
                        h += "</table>";
                        h += "<h2>Aliados</h2>";
                        h += "<table class=\"members\" border=\"1\" style=\"width:60%\">";
                        h += "<tr >";
                        h += "<th>Guilda</th>";

                        h += "</tr>";
                        for (Clan cs : ClanLand.manager.getClans()) {
                            if (cs != c) {
                                if (cs.isAlly(c.getTag())) {
                                    h += "<td> <a href=\"../guildas/" + cs.getTag() + ".html\"\">" + cs.getName() + " - " + cs.getTag() + "</a></td>";

                                }

                            }
                        }
                        h += "</table>";
                        h += "</body>";
                        out.write(h + "</html>");
                        h = "<html>";
                        out.close();
                        fstream.close();
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                    KoM.log.info(ex.getMessage());
                   
                }
                KoM.log.info("O Update do site levou " + String.valueOf((System.currentTimeMillis() - co)) + " milisegundos!");

            }
        };
        t.start();
    }

    public static String getCargo(ClanPlayer cp) {
        if (cp.isLeader()) {
            return "Lider";
        } else if (cp.isTrusted()) {
            return "Membro de Confianca";
        } else {
            return "Membro";
        }
    }

    public static void geraRankGuildas() {
        String h = "<html>";
        FileWriter fstream = null;
        KoM.log.info("Gerando rank de guildas");
        try {
           
            fstream = new FileWriter("plugins" + File.separator + "kom" + File.separator + "site" + File.separator + "ClanRank.html");
            BufferedWriter out = new BufferedWriter(fstream);
 KoM.log.info("Arquivo aberto");
            h += "<head>";
            h += "<meta charset=\"utf-8\">";
            h += "<title> Rank de Guildas</title>";
            h += "<link rel=\"stylesheet\" href=\"./styler.css\">";
            h += "<link href=\"./favicon.ico\" rel=\"shortcut icon\" type=\"image/x-icon\" />";
            h += "</head>";
            h += "<body>";
            h += "<img src=\"http://www.knightsofminecraft.com.br/img/company-logo.png\" class=\"logo\" alt=\"Logo KoM\"></h1>";
            h += "<h2>Top Poder</h2>";
            h += "<table border=1 class=\"topg\">";
             KoM.log.info("foi header");
            ArrayList<KomPlayer> top = new ArrayList();
            ArrayList<Clan> toppoder = new ArrayList();
            ArrayList<Clan> topkdr = new ArrayList();
            for (Clan c : ClanLand.manager.getClans()) {

                //toplevel.add(c);
                toppoder.add(c);
                topkdr.add(c);
            }
             KoM.log.info("vi os clans");
            Collections.sort(toppoder, new Comparator<Clan>() {
                public int compare(Clan one, Clan other) {
                    return ClanLand.getPoder(other.getTag()) - ClanLand.getPoder(one.getTag());
                }
            });
             KoM.log.info("fiz o sort KDR");
            Collections.sort(topkdr, new Comparator<Clan>() {
                public int compare(Clan one, Clan other) {
                    if (one.getTotalKDR() < other.getTotalKDR()) {
                        return 1;
                    } else if (one.getTotalKDR() == other.getTotalKDR()) {
                        return 0;
                    } else {
                        return -1;
                    }
                }

            });
             KoM.log.info("fiz o sort PODER");

            h += "<tr>";
            h += "<td class =\"cabeg\">Lugar</td>";
            h += "<td class =\"cabeg\">Nome da Guilda</td>";
            h += "<td class =\"cabeg\">Poder</td>";
            h += "</tr>";

            for (int y = 0; y < toppoder.size(); y++) {
                if (y == 50) {
                    break;
                }
                Clan kp = toppoder.get(y);
                h += "<tr>";
                h += "<td>" + (y + 1) + "</td>";
                h += "<td>" + "<a href=\"./guildas/" + kp.getTag() + ".html\"\">" + kp.getName() + " - " + kp.getTag() + "</a>" + "</td>";
                h += "<td>" + ClanLand.getPoder(kp.getTag()) + "</td>";
                h += "</tr>";
            }
            h += "</table><br>";

            h += "<h2>Top KdR</h2>";

            h += "<table border=1 class=\"topg\">";
            h += "<tr>";
            h += "<td class =\"cabeg\">Lugar</td>";
            h += "<td class =\"cabeg\">Guilda</td>";
            h += "<td class =\"cabeg\">KdR</td>";
            h += "</tr>";

            for (int y = 0; y < topkdr.size(); y++) {
                if (y == 50) {
                    break;

                }
                Clan kp = topkdr.get(y);
                h += "<tr>";
                h += "<td>" + (y + 1) + "</td>";
                h += "<td>" + "<a href=\"./guildas/" + kp.getTag() + ".html\"\">" + kp.getTag() + " - " + kp.getName() + "</a>" + "</td>";
                h += "<td>" + kp.getTotalKDR() + "</td>";
                h += "</tr>";
            }
             KoM.log.info("criei as tabelas");
            h += "</table><br>";

            h += "</body>";
            h += "</html>";
            out.write(h);
            out.close();
            fstream.close();
            h = "<html>";

        } catch (IOException ex) {
            ex.printStackTrace();
            KoM.log.info(ex.getMessage());
        } finally {
       
        }
    }
}
