/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.NewLoots;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import nativelevel.KoM;

/**
 *
 * @author User
 */
public class BauDB {
    
    
    public void inicializa() {
        Connection conn;
        Statement st;
        try {
            conn = KoM.database.pegaConexao();
            if (conn == null) {
                KoM.log.log(Level.SEVERE,
                        "[KoM] CONXEAUMMM VEIO NUUUUL");
                KoM._instance.getServer().shutdown();
                return;
            }
            
            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Baus (x INTEGER, y INTEGER, z INTEGER, loot VARCHAR(100), chance INTEGER);");


            conn.commit();
        } catch (SQLException e) {
            KoM.log.log(Level.SEVERE,
                    "[KoM] Conexao com Banco Fechada");
            KoM.log.log(Level.SEVERE, "[KoM] {0}", e);
            KoM.safeMode = true;
        }
    }
    
}
