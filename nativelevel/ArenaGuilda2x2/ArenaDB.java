package nativelevel.ArenaGuilda2x2;

import nativelevel.utils.TitleAPI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.logging.Level;
import nativelevel.ArenaGuilda2x2.Arena2x2;
import static nativelevel.ArenaGuilda2x2.Elo.matou;
import static nativelevel.ArenaGuilda2x2.Elo.morreu;
import nativelevel.KoM;
import nativelevel.utils.LocUtils;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ArenaDB {

    private Statement sst;
    private Statement est;

    public Plugin plug;

    public ArenaDB() {
        this.plug = Arena2x2.instancia;
    }

    public void inicializa() {
        Connection conn;
        Statement st;
        try {
            conn = KoM.database.pegaConexao();
            if (conn == null) {
                Arena2x2.log.log(Level.SEVERE,
                        "[KoM] CONXEAUMMM VEIO NUUUUL");
                plug.getServer().shutdown();
                return;
            }
            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Arena_Matcher (tag VARCHAR(200) PRIMARY KEY, hora DATETIME,ranked INTEGER);");

            // KnightsOfMania.database.pegaConexao().commit();
            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Arena_Jogadores (jogador VARCHAR(200) primary key, gameId INTEGER, tag varchar(200), morto INTEGER);");

            // KnightsOfMania.database.pegaConexao().commit();
            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Arena_Elo (tag VARCHAR(200) primary key, elo INTEGER);");

            // KnightsOfMania.database.pegaConexao().commit();
            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Arena_Jogos (gameId INTEGER PRIMARY KEY AUTO_INCREMENT, tag1 varchar(50), tag2 varchar(50), ranked INTEGER, arenaId INTEGER);");

            // KnightsOfMania.database.pegaConexao().commit();
            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Arena_Lista (arenaId INTEGER PRIMARY KEY AUTO_INCREMENT, x INTEGER, y INTEGER, z INTEGER, nome varchar(100), spawn1 varchar(100), spawn2 varchar(100));");

            // KnightsOfMania.database.pegaConexao().commit();
            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Arena_Stats (jogador varchar(200) primary key, vitorias INTEGER, derrotas INTEGER, matou INTEGER, morreu INTEGER);");

            // KnightsOfMania.database.pegaConexao().commit();
            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Arena_GStats (tag varchar(200) primary key, vitorias INTEGER, derrotas INTEGER);");

            // KnightsOfMania.database.pegaConexao().commit();
            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Arena_Historico (tagWin varchar(100), tagLoose varchar(100),hora DATETIME, jogadoresWin varchar(200), jogadoresLoose varchar(200));");

        } catch (SQLException e) {
            Arena2x2.log.log(Level.SEVERE, "[KoM] Conexao com Banco Fechada");
            Arena2x2.log.log(Level.SEVERE, "[KoM] {0}", e);
        }
    }

    public void setElo(String tag, int elo) {
        try {
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("update Arena_Elo set elo = " + elo + " where tag = '" + tag + "'");

            ////  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public int getElo(String tag) {
        int elo = 0;
        try {
            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select elo from Arena_Elo where tag = '" + tag + "'");

            if (rs.next()) {
                return rs.getInt("elo");
            } else {
                est = KoM.database.pegaConexao().createStatement();
                est.executeQuery("insert into Arena_Elo (tag, elo) values ('" + tag + "', 1000)");
                return 1000;
            }

            ////  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
        return elo;
    }

    // inicio do server
    public void limpaTudo() {
        try {
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("DELETE from Arena_Jogos");

            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("delete from Arena_Jogadores");

            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("delete from Arena_Matcher");

            ////  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void insereHistorico(String tagWinner, String tagLooser, List<UUID> winners, List<UUID> loosers) {
        try {

            String win = "";
            String loose = "";

            for (UUID w : winners) {
                OfflinePlayer p = Bukkit.getPlayer(w);
                if (p == null) {
                    p = Bukkit.getOfflinePlayer(w);
                }

                win += p.getName() + " ";
            }
            for (UUID l : loosers) {
                OfflinePlayer p = Bukkit.getPlayer(l);
                if (p == null) {
                    p = Bukkit.getOfflinePlayer(l);
                }

                loose += p.getName() + " ";
            }

            // retirando jogadores
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("insert into Arena_Historico (tagWin, tagLoose, hora, jogadoresWin, jogadoresLoose) values ('" + tagWinner + "', '" + tagLooser + "',CURRENT_DATE, '" + win + "','" + loose + "')");

            //  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void setSpawn1(String arena, Location spawn) {
      try {
                String loc = LocUtils.loc2str(spawn);
                est = KoM.database.pegaConexao().createStatement();
                est.executeUpdate("update Arena_Lista set spawn1 = '"+loc+"' where nome = '"+arena+"'");
            //  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void setSpawn2(String arena, Location spawn) {
      try {
                String loc = LocUtils.loc2str(spawn);
                est = KoM.database.pegaConexao().createStatement();
                est.executeUpdate("update Arena_Lista set spawn2 = '"+loc+"' where nome = '"+arena+"'");
            //  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void atualizaStats(UUID p, int modMatou, int modMorreu, int modVitorias, int modDerrotas) {
        try {

            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select 1 from Arena_Stats where jogador = '" + p.toString() + "'");
            if (!rs.next()) {
                est = KoM.database.pegaConexao().createStatement();
                est.executeUpdate("insert into Arena_Stats (matou,morreu,vitorias, derrotas,jogador) values (" + modMatou + "," + modMorreu + "," + modVitorias + "," + modDerrotas + ",'" + p.toString() + "')");
            } else {
                est = KoM.database.pegaConexao().createStatement();
                est.executeUpdate("update Arena_Stats set vitorias = vitorias + " + modVitorias + ", derrotas=derrotas+" + modDerrotas + ", matou = matou + " + modMatou + ",morreu=morreu+" + modMorreu + " where jogador = '" + p.toString() + "'");
            }
            //  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void atualizaGStats(String tag, int modVitorias, int modDerrotas) {
        try {

            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select 1 from Arena_GStats where tag = '" + tag + "'");
            if (!rs.next()) {
                est = KoM.database.pegaConexao().createStatement();
                est.executeUpdate("insert into Arena_GStats (vitorias, derrotas,tag) values (" + modVitorias + "," + modDerrotas + ",'" + tag + "')");
            } else {
                est = KoM.database.pegaConexao().createStatement();
                est.executeUpdate("update Arena_GStats set vitorias = vitorias + " + modVitorias + ", derrotas=derrotas+" + modDerrotas + " where tag = '" + tag + "'");
            }

            //  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
     public void resetaGuilda(String tag) {
        try {

            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("delete from Arena_GStats where tag = '" + tag + "'");
            //  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void apagaPartida(int gameId) {
        try {
            // retirando jogadores
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("DELETE P.* FROM Arena_Jogadores P INNER JOIN Arena_Jogos J ON P.gameId = J.gameId where P.gameId = " + gameId);

            // removendo o jogo
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("DELETE from Arena_Jogos where gameId = " + gameId);

            //  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public boolean taEmJogo(Player p) {
        try {
            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select count(*) as ct from Arena_Jogadores where jogador = '" + p.getUniqueId().toString() + "'");
            rs.next();
            return rs.getInt("ct") > 0;
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean taEmJogo(String tag) {
        try {
            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select 1 from Arena_Jogadores where tag = '" + tag + "'");
            return rs.next();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public void saiDaFila(UUID u) {
        try {
            est = KoM.database.pegaConexao().createStatement();
            String tag = null;
            ResultSet rs = est.executeQuery("select J.jogador , J.tag from Arena_Jogadores J inner join Arena_Matcher M on M.tag = J.tag where J.tag = (select distinct tag from Arena_Jogadores where jogador = '" + u.toString() + "')");
            while (rs.next()) {
                UUID uid = UUID.fromString(rs.getString("jogador"));
                tag = rs.getString("tag");
                Player p = Bukkit.getPlayer(uid);
                if (p != null) {
                    p.sendMessage(ChatColor.RED + "Voce foi retirado da fila de espera pois um jogador da sua guilda saiu da fila");
                }
            }
            if (tag != null) {
                est = KoM.database.pegaConexao().createStatement();
                est.executeUpdate("delete from Arena_Jogadores where tag = '" + tag + "'");

                est = KoM.database.pegaConexao().createStatement();
                est.executeUpdate("delete from Arena_Matcher where tag = '" + tag + "'");
            } else {
                Player ativou = Bukkit.getPlayer(u);
                if (ativou != null) {
                    ativou.sendMessage(ChatColor.RED + " Voce não está na fila de uma partida !");
                }
            }

        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void cancelaJogo(String arena) {
        
    }
    
    public List<String> arenasEmJogo() {
        List<String> arenas = new ArrayList<String>();
        try {
            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select nome from Arena_Lista L inner join Arena_Jogos J on J.arenaId = L.arenaId");
            while(rs.next()) {
                arenas.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
        return arenas;
    }

    public boolean taVivoEmJogo(Player p) {
        try {
            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select 1 from Arena_Jogadores where gameId <> -1 and morto = 0 and jogador = '" + p.getUniqueId().toString() + "'");
            return rs.next();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public void viewStats(Player vendo, ClanPlayer cp) {
        try {

            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select * from Arena_Stats where jogador = '" + cp.getUniqueId().toString() + "'");

            if (rs.next()) {

                vendo.sendMessage(ChatColor.GOLD + "______Arena de " + cp.getName() + "____");
                vendo.sendMessage(ChatColor.GOLD + "Matou " + ChatColor.GREEN + rs.getInt("matou") + ChatColor.GOLD + " Morreu " + ChatColor.RED + rs.getInt("morreu"));
                vendo.sendMessage(ChatColor.GOLD + "Vitorias " + ChatColor.GREEN + rs.getInt("vitorias") + ChatColor.GOLD + " Derrotas " + ChatColor.RED + rs.getInt("derrotas"));
            }

            est = KoM.database.pegaConexao().createStatement();
            rs = est.executeQuery("select * from Arena_GStats where tag = '" + cp.getClan().getTag() + "'");
            if (rs.next()) {
                vendo.sendMessage(ChatColor.GOLD + "______Arena de " + cp.getClan().getTag() + "____");
                vendo.sendMessage(ChatColor.GOLD + "Vitorias " + ChatColor.GREEN + rs.getInt("vitorias") + ChatColor.GOLD + " Derrotas " + ChatColor.RED + rs.getInt("derrotas"));
                vendo.sendMessage(ChatColor.GOLD + "Estas estatísticas são invisiveis e não são usadas para o rank");
            }

        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void jogadorMorre(Player p) {
        try {
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("update Arena_Jogadores set morto = 1 where jogador = '" + p.getUniqueId().toString() + "'");

            // conta +1 morte
            atualizaStats(p.getUniqueId(), 0, 1, 0, 0);

            // verificando se tem algum vivo ainda com a mesma tag q esse
            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select P.jogador, P.morto, P.gameId, P.tag, J.ranked from Arena_Jogadores P inner join Arena_Jogos J on P.gameId = J.gameId and P.gameId <> -1 where P.tag = (select tag from Arena_Jogadores where jogador = '" + p.getUniqueId().toString() + "')");
            List<UUID> loosers = new ArrayList<UUID>(MatchMaking.tamanhoGrupo);
            int gameId = -1;
            String tagLooser = null;
            boolean ranked = false;
            while (rs.next()) {
                ranked = rs.getInt("ranked") == 1;
                String jogador = rs.getString("jogador");
                int morto = rs.getInt("morto");

                tagLooser = rs.getString("tag");
                gameId = rs.getInt("gameId");
                if (morto == 0) {
                    return; // ainda tem nego vivo
                } else {
                    loosers.add(UUID.fromString(jogador));
                }
            }

            if (tagLooser == null) {
                return;
            }

            // nenhum jogador com a tag tava vivo
            List<Player> tp = new ArrayList<Player>();
            // se ta aqui é pq ja morreu geral e acabou a partida
            est = KoM.database.pegaConexao().createStatement();
            String tagWinner = null;
            List<UUID> winners = new ArrayList<UUID>();
            rs = est.executeQuery("select jogador, tag from Arena_Jogadores where gameId = " + gameId + " and tag not in ('" + tagLooser + "')");
            while (rs.next()) {
                UUID uid = UUID.fromString(rs.getString("jogador"));
                Player pl = Bukkit.getPlayer(uid);
                tagWinner = rs.getString("tag");
                winners.add(uid);
                atualizaStats(uid, 0, 0, 1, 0);// +1 vitoria 
                if (pl != null) {
                    pl.sendMessage(ChatColor.GREEN + "Voce ganhou a arena !");

                    // se ele ta vivo no jogo vai pra arena
                    if (taEmJogo(pl)) {
                        tp.add(pl);
                    }
                }
            }

            // calculando ELO
            if (ranked) {
                int vitoria = Arena2x2.sql.getElo(tagWinner);
                int derrota = Arena2x2.sql.getElo(tagLooser);

                int eloGanhador = matou(vitoria, derrota);
                int eloPerdedor = morreu(vitoria, derrota);

                for (Player pl : tp) {
                    pl.sendMessage(ChatColor.AQUA + "A guilda " + tagWinner + " ganhou " + (eloGanhador - vitoria) + " ELO, ficando com ELO: " + eloGanhador);
                }

                for (UUID u : loosers) {

                    Player pl = Bukkit.getPlayer(u);
                    atualizaStats(u, 0, 0, 0, 1);// +1 derrota 
                    if (pl != null) {
                        pl.sendMessage(ChatColor.RED + "Voce perdeu na Arena !");
                        pl.sendMessage(ChatColor.AQUA + "A guilda " + tagLooser + " perdeu " + (derrota - eloPerdedor) + " ELO, ficando com ELO: " + eloGanhador);
                    }
                }

                Arena2x2.sql.setElo(tagWinner, eloGanhador);
                Arena2x2.sql.setElo(tagLooser, eloPerdedor);
            } else {
                for (UUID u : loosers) {
                    Player pl = Bukkit.getPlayer(u);
                    atualizaStats(u, 0, 0, 0, 1);// +1 derrota 
                    if (pl != null) {
                        pl.sendMessage(ChatColor.RED + "Voce perdeu na Arena !");
                    }
                }
            }

            String msg = ChatColor.BLUE + "[Arena]" + ChatColor.AQUA + "A guilda " + tagWinner + ChatColor.AQUA + " derrotou a guilda " + tagLooser + ChatColor.AQUA + " na " + ChatColor.YELLOW + "/arena" + ChatColor.AQUA + " de guildas!";

            for (Player p2 : Bukkit.getOnlinePlayers()) {
                p2.sendMessage(msg);
            }

            // atualizando stats de guildas
            atualizaGStats(tagWinner, 1, 0); // +1 vitoria
            atualizaGStats(tagLooser, 0, 1); // +1 derrota

            // ter histórico é chique
            insereHistorico(tagWinner, tagLooser, winners, loosers);

            // remove todos dados da partida
            apagaPartida(gameId);

            for (Player tper : tp) {
                if (tper != null) {
                    tper.teleport(Bukkit.getWorld("vila").getSpawnLocation());
                }
            }

            //  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void comecaJogo(final int idArena, final List<String> tags, final boolean ranked) {
        try {
            est = KoM.database.pegaConexao().createStatement();
            ResultSet rsa = est.executeQuery("select x,y,z, spawn1, spawn2 from Arena_Lista where arenaId = " + idArena + ";");
            rsa.next();
            Location localArena = new Location(Bukkit.getWorld(Arena2x2.nomeMundoArena), rsa.getInt("x"), rsa.getInt("y"), rsa.getInt("z"), 0, 0);
            Location time1 = new Location(Bukkit.getWorld(Arena2x2.nomeMundoArena), rsa.getInt("x") + Arena2x2.tamanhoArena, rsa.getInt("y"), rsa.getInt("z"), 0, 0);
            Location time2 = new Location(Bukkit.getWorld(Arena2x2.nomeMundoArena), rsa.getInt("x") - Arena2x2.tamanhoArena, rsa.getInt("y"), rsa.getInt("z"), 0, 0);
            time1.setX(time1.getX() + 0.5);
            time1.setY(time1.getY() + 1);
            time1.setZ(time1.getZ() + 0.5);
            time2.setX(time2.getX() + 0.5);
            time2.setY(time2.getY() + 1);
            time2.setZ(time2.getZ() + 0.5);
            
            String sspawn1 = rsa.getString("spawn1");
            String sspawn2 = rsa.getString("spawn2");
            
            if(sspawn1 != null) {
                time1 = LocUtils.str2loc(sspawn1);
            }
            if(sspawn2 != null) {
                time2 = LocUtils.str2loc(sspawn2);
            }
            
            
            for (Entity e : Bukkit.getWorld("arena").getEntities()) {
                if (e.getType() != EntityType.PLAYER) {
                    if (e.getLocation().distance(localArena) <= 20) {
                        e.remove();
                    }
                }
            }

            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select jogador, tag from Arena_Jogadores where tag in ('" + tags.get(0) + "','" + tags.get(1) + "');");
            HashMap<Player, String> participantes = new HashMap<Player, String>();
            String tagQNegoSaiu = null;
            while (rs.next()) {

                String jogador = rs.getString("jogador");
                String t = rs.getString("tag");

                Player p = Bukkit.getPlayer(UUID.fromString(jogador));
                if (p == null) {
                    tagQNegoSaiu = t;
                } else {
                    Arena2x2.log.info(p.getName() + "TA NA LISTA DE NEGOS PRA TELEPORTAR PARA A ARENA " + idArena);
                    participantes.put(p, t);
                }
            }

            // se um nego saiu, não começa e remove a guilda da lista de espera
            if (tagQNegoSaiu != null) {
                for (Player p : participantes.keySet()) {
                    if (participantes.get(p).equalsIgnoreCase(tagQNegoSaiu)) {
                        p.sendMessage(ChatColor.RED + "Um jogador não estava online quando ia começar a arena !");
                        p.sendMessage(ChatColor.RED + "Sua guilda foi retirada da espera !");
                    } else {
                        p.sendMessage(ChatColor.RED + "Um participante de outra guilda saiu da arena, voltando a fila na frente !");
                    }
                }
                est = KoM.database.pegaConexao().createStatement();
                est.executeUpdate("delete from Arena_Matcher where tag ='" + tagQNegoSaiu + "'");
                return;
            }

            // teleportando a moçada
            for (Player p : participantes.keySet()) {
                Location sl = (tags.get(0).equalsIgnoreCase(participantes.get(p)) ? time1 : time2);
                Arena2x2.teleportSincrono(p, sl);
                p.sendMessage(ChatColor.GREEN + "Voce entrou na arena ! Lute !");
            }

            // criando o jogo de fato
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("insert into Arena_Jogos (tag1, tag2, ranked, arenaId) values ('" + tags.get(0) + "','" + tags.get(1) + "'," + (ranked ? 1 : 0) + "," + idArena + ")");
                    //  KnightsOfMania.database.pegaConexao().commit();

            // setando q os jogadores tão no jogo
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("update Arena_Jogadores set gameId = LAST_INSERT_ID() where tag = '" + tags.get(0) + "' or tag = '" + tags.get(1) + "'");

            // removendo os nego da lista de espera
            for (String tag : tags) {
                est = KoM.database.pegaConexao().createStatement();
                est.executeUpdate("delete from Arena_Matcher where tag ='" + tag + "'");
            }

            //  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public List<String> obtemGuildasEsperando(boolean ranked) {
        List<String> tags = new ArrayList<String>();
        try {
            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select tag from Arena_Matcher where ranked = " + (ranked ? 1 : 0) + " order by hora limit 2  ");
            while (rs.next()) {
                tags.add(rs.getString("tag"));
            }
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
        return tags;
    }

    // retorna -1 se não tiver livre
    public int getArenaLivre() {
        int arena = -1;
        try {
            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select L.arenaId from Arena_Lista L left join Arena_Jogos J on J.arenaID = L.arenaID where J.arenaId is null order by RAND() limit 1 ");
            if (rs.next()) {
                return rs.getInt("arenaId");
            }
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
        return arena;
    }

    public void deletaArena(String nome) {
        try {
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("delete from Arena_Lista where nome ='" + nome + "'");
            //  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public Location getArena(String nome) {
        try {
            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select x,y,z from Arena_Lista where nome = '" + nome + "'");
            if (rs.next()) {
                return new Location(Bukkit.getWorld(Arena2x2.nomeMundoArena), rs.getInt("x"), rs.getInt("y"), rs.getInt("z"), 0, 0);
            }
            //  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public void criaArena(int x, int y, int z, String nome) {
        try {
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("insert into Arena_Lista (x,y,z,nome) values (" + x + "," + y + "," + z + ",'" + nome + "')");
            //  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public List<String> listaArenas() {
        List<String> arenas = new ArrayList<String>();
        try {
            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select nome from Arena_Lista order by nome");
            while (rs.next()) {
                arenas.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
        return arenas;
    }

    public boolean jaTaNoMatcher(String tag) {
        try {
            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select * from Arena_Matcher where tag='" + tag + "'");
            return rs.next();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
        return true;
    }

    public void botaMatchMaking(ClanPlayer lider, List<Player> grupo, boolean ranked) {
        try {
            String tag = lider.getClan().getTag();
            for (Player p : grupo) {
                est = KoM.database.pegaConexao().createStatement();
                est.executeUpdate("insert into Arena_Jogadores (jogador,gameId,tag,morto) values ('" + p.getUniqueId().toString() + "', -1, '" + tag + "',0)");
                p.sendMessage(ChatColor.GREEN + "Sua guilda foi colocada na busca de competidores na Arena " + (ranked ? ChatColor.YELLOW + "Ranquiada" : "De Treino") + ChatColor.GREEN + " !!");
                p.sendMessage(ChatColor.GREEN + "Aguarde para ser chamado !");
            }
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("insert into Arena_Matcher (tag,ranked) values ('" + tag + "'," + (ranked ? 1 : 0) + ");");
            String msg = ChatColor.AQUA + tag + "" + ChatColor.AQUA + " entrou na lista da " + ChatColor.GREEN + "/arena";
            if(ranked)
                msg+=ChatColor.AQUA +" Ranked ! ";
            //if (!ranked) {
            //    msg = ChatColor.AQUA + "A guilda " + tag + "" + ChatColor.AQUA + " entrou na lista de espera da " + ChatColor.GREEN + "/arena";
            //} else {
            //    msg = ChatColor.AQUA + "A guilda " + tag + "" + ChatColor.AQUA + " entrou na lista de espera da " + ChatColor.GREEN + "/arena " + ChatColor.RED + "! RANQUEADA !";
            //}
            for (Player p : Bukkit.getOnlinePlayers()) {
                TitleAPI.sendActionBar(p, msg);
                //TitleAPI.sendFullTitle(p, Integer.SIZE, Integer.SIZE, Integer.SIZE, tag, tag);
                p.sendMessage(msg);
            }

            //  KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            Arena2x2.log.info("ZUOU ARENA:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
