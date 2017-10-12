package nativelevel.rankings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import nativelevel.KoM;
import org.bukkit.entity.Player;

/**
 *
 * @author vntgasl
 */
public class RankDB {

    static String url = "jdbc:mysql://localhost:3306/kom?autoReconnect=true";

    public static void InitMysql() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection connection = KoM.database.pegaConexao();
            for (Estatistica stat : Estatistica.values()) {
                PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Estatistica_" + stat.name() + " (uuid varchar(100) PRIMARY KEY, nome varchar(100), pontos int, posicaoRank int)");
                statement.executeUpdate();
            }
            if (!connection.isValid(100)) {
                throw new SQLException();
            }

            // connection.close();
        } catch (Exception e) {

            ErroMysql(e);
        }
    }

    public static void calculaRankings() {
        Connection connection = KoM.database.pegaConexao();
        try {
            for (Estatistica stat : Estatistica.values()) {
                String nomeTabelaLeitura = "Estatistica_" + stat.name();
                Statement statement = connection.createStatement();
                // mais otimizado q isso só chamando a porra do STEVE JOBS DA TUMBA PORRA
                String sql = "UPDATE   " + nomeTabelaLeitura + " P2 "
                        + "JOIN     (SELECT    p.uuid,"
                        + "                    @curRank := @curRank + 1 AS rank"
                        + "          FROM      " + nomeTabelaLeitura + " p"
                        + "          JOIN      (SELECT @curRank := 0) r"
                        + "          ORDER BY  p.pontos DESC"
                        + "         ) ranks ON (ranks.uuid = P2.uuid) "
                        + "SET      P2.posicaoRank = IF(ranks.rank = 0, 666, ranks.rank) ";
                statement = connection.createStatement();
                statement.executeUpdate(sql);
            }
        } catch (Exception e) {
            ErroMysql(e);
        }
    }

    private static HashMap<UUID, HashMap<Estatistica, RankedPlayer>> ranks = new HashMap<UUID, HashMap<Estatistica, RankedPlayer>>();

    public static HashMap<Estatistica, RankedPlayer> getRankings(Player p) {
        HashMap<Estatistica, RankedPlayer> map = new HashMap<Estatistica, RankedPlayer>();
        if (ranks.containsKey(p.getUniqueId())) {
            return ranks.get(p.getUniqueId());
        }
        for (Estatistica a : Estatistica.values()) {
            RankedPlayer rp = getPlayer(a, p);
            map.put(a, rp);
        }
        ranks.put(p.getUniqueId(), map);
        return map;
    }

    public static RankedPlayer getPlayer(Estatistica stat, Player p) {
        Connection connection = KoM.database.pegaConexao();
        RankedPlayer pl = new RankedPlayer();
        try {
            String nomeTabela = "Estatistica_" + stat.name();
            Statement statement = connection.createStatement();
            String sql = "select uuid, nome, pontos, posicaoRank from " + nomeTabela + " where uuid = '" + p.getUniqueId().toString() + "'";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                pl.id = UUID.fromString(rs.getString("uuid"));
                pl.player = rs.getString("nome");
                pl.posicao = rs.getInt("posicaoRank");
                pl.pontos = rs.getInt("pontos");
                pl.stat = stat;
                return pl;
            }
        } catch (SQLException ex) {
            ErroMysql(ex);
        }
        return pl;
    }

    public static List<RankedPlayer> getTopPlayers(Estatistica stat, int limite) {
        List<RankedPlayer> lista = new ArrayList<RankedPlayer>(limite);
        try {
            String nomeTabela = "Estatistica_" + stat.name();
            Connection connection = KoM.database.pegaConexao();
            Statement statement = connection.createStatement();
            String sql = "select uuid, nome, pontos, posicaoRank from " + nomeTabela + " order by posicaoRank limit " + limite;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                RankedPlayer pl = new RankedPlayer();
                pl.id = UUID.fromString(rs.getString("uuid"));
                pl.player = rs.getString("nome");
                pl.posicao = rs.getInt("posicaoRank");
                pl.pontos = rs.getInt("pontos");
                pl.stat = stat;
                lista.add(0, pl);
            }
        } catch (SQLException ex) {
            ErroMysql(ex);
        }
        return lista;
    }

    public static void addPontoCache(Player p, Estatistica stat, int pontos) {
        KoM.debug("GANHANDO RANKING " + p.getName() + " " + stat.name() + " -> " + pontos);
        if (!RankCache.players.containsKey(p.getUniqueId())) {
            RankCache.players.put(p.getUniqueId(), new RankCache(p.getName()));
        }
        RankCache ranks = RankCache.players.get(p.getUniqueId());
        if (!ranks.ganhos.containsKey(stat)) {
            ranks.ganhos.put(stat, 0);
        }
        int pontosG = ranks.ganhos.get(stat);
        ranks.ganhos.put(stat, (pontos + pontosG));
    }

    public static void saveAll() {
        KoM.log.info("Salvando estatisticas");
        for (UUID u : RankCache.players.keySet()) {
            RankCache c = RankCache.players.get(u);
            for (Estatistica s : c.ganhos.keySet()) {
                KoM.debug("addando " + c.ganhos.get(s) + " pontos para " + c.nome);
                addPontos(c.nome, u, s, c.ganhos.get(s));

            }
        }
        KoM.log.info("Estatisticas salvas");
    }

    private static void addPontos(String nome, UUID uuid, final Estatistica stat, final int pontos) {
        Connection connection = KoM.database.pegaConexao();
        final UUID uid = uuid;
        final String name = nome;
        // new Thread() {
        //     public void run()
        //    {
        try {
            String nomeTabela = "Estatistica_" + stat.name();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO " + nomeTabela + " (uuid, nome, pontos, posicaoRank) "
                    + "VALUES ('" + uid.toString() + "', '" + name + "', " + pontos + ",0) "
                    + "ON DUPLICATE KEY UPDATE "
                    + "  pontos=pontos+" + pontos;
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException ex) {
            ErroMysql(ex);
        }
        //      }
        //  }.start();
    }

    public static void deletePlayer(final Player p) {

        final UUID uid = p.getUniqueId();
        new Thread() {
            public void run() {
                Connection connection = KoM.database.pegaConexao();
                for (Estatistica stat : Estatistica.values()) {
                    try {
                        String nomeTabela = "Estatistica_" + stat.name();
                        Statement statement = connection.createStatement();
                        String sql = "delete from " + nomeTabela + " where uuid = '" + p.getUniqueId() + "'";
                        statement.executeUpdate(sql);
                        connection.commit();
                    } catch (SQLException ex) {
                        ErroMysql(ex);
                    }
                }

            }
        }.start();
    }

    public static void ErroMysql(Exception e) {
        //Fazer oq acontece quando da erro no mysql
        e.printStackTrace();
    }
}
