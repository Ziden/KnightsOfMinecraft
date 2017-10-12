package nativelevel.ajuda;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import nativelevel.ajuda.database.*;
import nativelevel.sisteminhas.ClanLand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Cmds
        implements CommandExecutor {

    KomAjuda plugin;
    DBDefault db = new DBDefault();

    public Cmds(KomAjuda instance) {
        this.plugin = instance;
    }

    public void removerAjudante(String nome) {
        Player p = Bukkit.getPlayer(nome);
        if (p != null) {
            ClanLand.permission.playerAdd(p, "kom.ajd");
        }
    }

    public void adicionarAjudante(String nome) {
        Player p = Bukkit.getPlayer(nome);
        if (p != null) {
            ClanLand.permission.playerRemove(p, "kom.ajd");
        }

    }

    public static boolean ehInteiro(String arg) {
        try {
            Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public static HashMap<UUID, UUID> possiveisLikes = new HashMap<UUID, UUID>();
    public static HashMap<UUID, UUID> jaDeramLike = new HashMap<UUID, UUID>();

    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
        Player p;
        String myString;
        int id;
        if ((cs instanceof Player)) {
            p = (Player) cs;
            String comando = cmd.getLabel();
            PermissionUser user = PermissionsEx.getUser(p);
            String grupoUser = "default";
            for (String grupo : user.getGroupNames()) {
                grupoUser = grupo;
            }
            if (comando.equalsIgnoreCase("ajudantes")) {
                if (!user.has("kom.staff")) {
                    return true;
                }
                if (args.length < 1) {
                    p.sendMessage(KomAjuda.f("&cDigite /ajudantes <adicionar/remover> <jogador> &eou &c/ajudantes lista"));
                    return true;
                }
                if ((args.length < 2) && (args[0].equalsIgnoreCase("lista"))) {
               
           
                    return true;
                }
                if ((!args[0].equalsIgnoreCase("adicionar")) && (!args[0].equalsIgnoreCase("remover")) && (!args[0].equalsIgnoreCase("lista"))) {
                    p.sendMessage(KomAjuda.f("&cDigite /ajudantes <adicionar/remover> <jogador> &eou &c/ajudantes lista"));
                    return true;
                }
                if (args[0].equalsIgnoreCase("adicionar")) {
                    try {
                        p.sendMessage(KomAjuda.f("&aAjudante &e" + args[1] + " &aadicionado com sucesso!"));
                        adicionarAjudante(args[1]);
                    } catch (NullPointerException e) {
                        p.sendMessage(KomAjuda.f("&cAlgo deu errado.."));
                    }
                } else if (args[0].equalsIgnoreCase("remover")) {
                    try {
                        removerAjudante(args[1]);
                        p.sendMessage(KomAjuda.f("&cAjudante &e" + args[1] + " &cremovido com sucesso!"));
                    } catch (NullPointerException e) {
                        p.sendMessage(KomAjuda.f("&cAcho que este ajudante nao esta cadastrado.. ou algo deu errado!"));
                    }
                }
            }
            Object nome;
            if (comando.equalsIgnoreCase("responder")) {
          
                if (p.hasPermission("kom.ajd")) {
                    if (args.length < 1) {
                        p.sendMessage(KomAjuda.f("&cDigite /responder <id> <resposta>"));
                        return true;
                    }
                    if (!ehInteiro(args[0])) {
                        p.sendMessage(KomAjuda.f("&cDigite /responder <id> <resposta>"));
                        return true;
                    }
                    id = Integer.parseInt(args[0]);
                    if (MetodosGet.getInfoPorId_DatabasePerguntas(id, "pergunta") == null) {
                        p.sendMessage(KomAjuda.f("&cPergunta não encontrada! Digite /perguntas para ve-las!"));
                        return true;
                    }
                    nome = MetodosGet.getInfoPorId_DatabasePerguntas(id, "nome");

                    myString = "";
                    for (int i = 1; i < args.length; i++) {
                        String arg = args[i] + " ";
                        myString = myString + arg;
                    }

                    String servidor = MetodosGet.getInfoPorId_DatabasePerguntas(id, "servidor");
                    if (Bukkit.getPlayer((String) nome) == null) {
                        p.sendMessage(KomAjuda.f("&cO jogador esta offline. Sua resposta foi registrada!"));

                        try {
                            MetodosAdição.inserirResposta(p.getUniqueId(), p.getName(), id, myString, servidor);
                            MetodosRemover.removerPergunta(id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        p.sendMessage(KomAjuda.f("&cO jogador esta &aOnline&c. Sua resposta foi enviada!"));
                        String pergunta = MetodosGet.getInfoPorId_DatabasePerguntas(id, "pergunta");
                        Player jogador = Bukkit.getPlayer((String) nome);
                        jogador.sendMessage("");
                        jogador.sendMessage("&a------> Dùvida Respondida <-----");
                        jogador.sendMessage("");
                        jogador.sendMessage(KomAjuda.f("&aSua pergunta: &e" + pergunta));
                        jogador.sendMessage("");
                        jogador.sendMessage(KomAjuda.f("&bResposta: &f" + myString));
                        jogador.sendMessage("");
                        MetodosRemover.removerPergunta(id);
                        MetodosRemover.removerResposta(id);
                        jogador.sendMessage(KomAjuda.f("&6Se sua dúvida foi respondida certinho, digite &a/like " + ChatColor.YELLOW + " :)"));
                        Cmds.possiveisLikes.put(jogador.getUniqueId(), p.getUniqueId());
                        for(Player pl: Bukkit.getOnlinePlayers()) {
                            if(pl.hasPermission("kom.verresposta")) {
                                pl.sendMessage(ChatColor.AQUA+p.getName()+" respondeu a pergunta "+pergunta);
                                pl.sendMessage(ChatColor.AQUA+myString);
                            }
                        }
                        jogador.sendMessage("");
                    }
                }
            }
            if (comando.equalsIgnoreCase("perguntas")) {
                if (args.length < 1) {
                    p.sendMessage(KomAjuda.f("&cDigite &e/perguntas servidor &cou &e/perguntas todas"));
                    return true;
                }
                if (args[0].equalsIgnoreCase("servidor")) {
               
                    if (p.hasPermission("kom.ajd")) {
                        try {
                            ResultSet rs = this.db.getConnection().createStatement().executeQuery("SELECT * FROM Pixel_Perguntas");
                            while (rs.next()) {
                                if (rs.getString("servidor").equalsIgnoreCase(p.getServer().getServerName())) {
                                    p.sendMessage(KomAjuda.f("&7Pergunta ID: &e" + rs.getInt("id")));
                                    p.sendMessage(KomAjuda.f("&a" + rs.getString("pergunta")));
                                    p.sendMessage(KomAjuda.f("&7Jogador: &e" + rs.getString("nome") + " &7Servidor: &b" + rs.getString("servidor")));
                                    p.sendMessage(" ");
                                }
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(Cmds.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("todas")) {
                
                    if (p.hasPermission("kom.ajd")) {
                        try {
                            ResultSet rs = this.db.getConnection().createStatement().executeQuery("SELECT * FROM Pixel_Perguntas");
                            while (rs.next()) {
                                p.sendMessage(KomAjuda.f("&7Pergunta ID: &e" + rs.getInt("id")));
                                p.sendMessage(KomAjuda.f("&a" + rs.getString("pergunta")));
                                p.sendMessage(KomAjuda.f("&7Jogador: &e" + rs.getString("nome") + " &7Servidor: &b" + rs.getString("servidor")));
                                p.sendMessage(" ");
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(Cmds.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    p.sendMessage(KomAjuda.f("&cDigite &e/perguntas servidor &cou &e/perguntas todas"));
                    return true;
                }
            }
            if (comando.equalsIgnoreCase("ajuda")) {
               
                List<String> online = new ArrayList<String>();
                for(Player pll : Bukkit.getOnlinePlayers()) {
                    if(pll.hasPermission("kom.ajd")) {
                        online.add(pll.getName());
                    }
                }
                
                if (args.length == 0) {
                    p.sendMessage(KomAjuda.f("&7Digite &a/ajuda <mensagem> &7para solicitar ajuda de nossos ajudantes!"));
                    return true;
                }
                myString = "";
                for (int i = 0; i < args.length; i++) {
                    String arg = args[i] + " ";
                    myString = myString + arg;
                }
                try {
                    MetodosAdição.inserirPergunta(p.getUniqueId(), p.getName(), myString, p.getServer().getServerName());
                } catch (SQLException ex) {
                    Logger.getLogger(Cmds.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (online.isEmpty()) {
                    p.sendMessage(KomAjuda.f("&7Não existem ajudantes online! Sua pergunta foi registrada e será respondida em breve!"));
                    return true;
                }
                p.sendMessage(KomAjuda.f("&7Pergunta enviada! &eAguarde a resposta de um de nossos ajudantes!"));

                id = 0;
                try {
                    ResultSet rs = this.db.getConnection().createStatement().executeQuery("SELECT * FROM Pixel_Perguntas");
                    while (rs.next()) {
                        id = rs.getInt("id");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Cmds.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Object pl1 : online) {
                    Player pl = Bukkit.getPlayer((String) pl1);
                    pl.sendMessage(" ");
                    pl.sendMessage(KomAjuda.f("&a&lNOVA PERGUNTA! &7[&dID: &e" + id + "&7]"));
                    pl.sendMessage(KomAjuda.f("&f" + myString));
                    pl.sendMessage(KomAjuda.f("&7Jogador: &a" + p.getName() + " &cUtilize &e/responder " + id + " <resposta>"));
                    pl.sendMessage(" ");
                }
            }
        }
        return true;
    }
}
