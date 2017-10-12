package nativelevel.playerboolean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import nativelevel.KoM;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 *
 * @author vntgasl
 *
 */
public class StageDB {

    private static Cache<Stage> cache = new Cache<Stage>();

    public void onEnable() {
        Connection conn;
        Statement st;
        try {
            conn = KoM.database.pegaConexao();
            if (conn == null) {
                return;
            }
            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS STAGES (uuid varchar(50), stage varchar(100))");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public static Stage getPlayerStage(Player player) {
        Stage data = new Stage();
        if (cache.hasCache(player.getUniqueId())) {

            return cache.getCached(player.getUniqueId());
        }
        try {
            Connection conn = KoM.database.pegaConexao();
            Statement est = conn.createStatement();
            ResultSet rs = est.executeQuery("select stage from STAGES where uuid = '" + player.getUniqueId().toString() + "'");
            while (rs.next()) {
                data.stagesCompleted.add(rs.getString("stage"));
            }
            cache.set(player.getUniqueId(), data);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public static void clean(UUID u) {
        try {
            Connection conn = KoM.database.pegaConexao();
            Statement est = conn.createStatement();
            String sql = "delete from STAGES where uuid = '" + u.toString() + "'";
            est.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void addCompletedStage(Player player, String stage) {
        try {
            Connection conn = KoM.database.pegaConexao();
            Statement est = conn.createStatement();
            String sql = "insert into STAGES (uuid, stage)"
                    + " values "
                    + "('" + player.getUniqueId().toString() + "', '" + stage + "')";

            est.executeUpdate(sql);
            Stage actualStages = getPlayerStage(player);
            actualStages.stagesCompleted.add(stage);
            cache.set(player.getUniqueId(), actualStages);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
