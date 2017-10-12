  package nativelevel.arena1x1.Listeners;
  
  import java.util.ArrayList;
  import nativelevel.arena1x1.PvPMatchmaking.PvPMatchmaking;
import nativelevel.KoM;
  import org.bukkit.Bukkit;
  import org.bukkit.configuration.file.FileConfiguration;
  import org.bukkit.entity.Player;
  import org.bukkit.event.EventHandler;
  import org.bukkit.event.entity.PlayerDeathEvent;
  import org.bukkit.event.player.PlayerRespawnEvent;
  
  
  public class DeathListener
    implements org.bukkit.event.Listener
  {
    public static KoM plugin;
    
    public DeathListener(PvPMatchmaking instance) { plugin = KoM._instance; }
    
    boolean stuff = false;
    boolean stuff1 = false;
    boolean stuff2 = false;
    boolean stuff3 = false;
    boolean stuff4 = false;
    boolean stuff5 = false;
    boolean stuff6 = false;
    boolean stuff7 = false;
    boolean stuff8 = false;
    boolean stuff9 = false;
    boolean stuff10 = false;
    boolean stuff11 = false;
    boolean stuff12 = false;
    boolean stuff13 = false;
    boolean stuff14 = false;
    boolean stuff15 = false;
    boolean stuff16 = false;
    boolean stuff17 = false;
    boolean stuff18 = false;
    boolean stuff19 = false;
    boolean stuff20 = false;
    boolean stuff21 = false;
    boolean stuff22 = false;
    boolean stuff23 = false;
    
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
      Player player = event.getEntity();
      String playerName = player.getName();
      if (!(event.getEntity().getKiller() instanceof Player)) {
        return;
      }
      Player killer = event.getEntity().getKiller();
      String killerName = killer.getName();
      if (PvPMatchmaking.joinedPlayers.contains(player)) { Player[] arrayOfPlayer;
        int j;
        int i; if ((PvPMatchmaking.player1.contains(player)) || (PvPMatchmaking.player2.contains(player))) {
          event.setDeathMessage(PvPMatchmaking.green + killerName + PvPMatchmaking.blue + " triunfou sobre " + PvPMatchmaking.green + playerName + PvPMatchmaking.blue + "!");
          for(Player players: Bukkit.getOnlinePlayers()) {
            if (PvPMatchmaking.joinedPlayers.contains(players)) {
              PvPMatchmaking.spectating.remove(players);
              players.sendMessage(PvPMatchmaking.blue + "Proximo round em 5 segundos !");
              if (!this.stuff) {
                PvPMatchmaking.teleportToArena("Start", 2);
                this.stuff = true;
                if (PvPMatchmaking.player1.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team1", killerName);
                  PvPMatchmaking.wplayer1.add(killer);
                  plugin.saveConfig();
                } else if (PvPMatchmaking.player2.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team1", killerName);
                  PvPMatchmaking.wplayer1.add(killer);
                  plugin.saveConfig();
                }
              }
            }
          }
        } else if ((PvPMatchmaking.player3.contains(player)) || (PvPMatchmaking.player4.contains(player))) {
          event.setDeathMessage(PvPMatchmaking.green + killerName + PvPMatchmaking.blue + " triunfou sobre " + PvPMatchmaking.green + playerName + PvPMatchmaking.blue + "!");
          for(Player players: Bukkit.getOnlinePlayers()) {
            if (PvPMatchmaking.joinedPlayers.contains(players)) {
              PvPMatchmaking.spectating.remove(players);
              players.sendMessage(PvPMatchmaking.blue + "Proximo round em 5 segundos !");
              if (!this.stuff1) {
                PvPMatchmaking.teleportToArena("Start", 3);
                this.stuff1 = true;
                if (PvPMatchmaking.player3.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team2", killerName);
                  PvPMatchmaking.wplayer2.add(killer);
                  PvPMatchmaking.Quarter2.add(killer);
                  plugin.saveConfig();
                } else if (PvPMatchmaking.player4.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team2", killerName);
                  PvPMatchmaking.wplayer2.add(killer);
                  PvPMatchmaking.Quarter2.add(killer);
                  plugin.saveConfig();
                }
              }
            }
          }
        } else if ((PvPMatchmaking.player5.contains(player)) || (PvPMatchmaking.player6.contains(player))) {
          event.setDeathMessage(PvPMatchmaking.green + killerName + PvPMatchmaking.blue + " triunfou sobre " + PvPMatchmaking.green + playerName + PvPMatchmaking.blue + "!");
          for(Player players: Bukkit.getOnlinePlayers()) {
            if (PvPMatchmaking.joinedPlayers.contains(players)) {
              PvPMatchmaking.spectating.remove(players);
              players.sendMessage(PvPMatchmaking.blue + "Proximo round em 5 segundos !");
              if (!this.stuff2) {
                PvPMatchmaking.teleportToArena("Start", 4);
                this.stuff2 = true;
                if (PvPMatchmaking.player5.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team3", playerName);
                  PvPMatchmaking.wplayer3.add(killer);
                  plugin.saveConfig();
                } else if (PvPMatchmaking.player6.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team3", playerName);
                  PvPMatchmaking.wplayer3.add(killer);
                  plugin.saveConfig();
                }
              }
            }
          }
        } else if ((PvPMatchmaking.player7.contains(player)) || (PvPMatchmaking.player8.contains(player))) {
          event.setDeathMessage(PvPMatchmaking.green + killerName + PvPMatchmaking.blue + " triunfou sobre " + PvPMatchmaking.green + playerName + PvPMatchmaking.blue + "!");
          for(Player players: Bukkit.getOnlinePlayers()) {
            if (PvPMatchmaking.joinedPlayers.contains(players)) {
              PvPMatchmaking.spectating.remove(players);
              players.sendMessage(PvPMatchmaking.blue + "Proximo round em 5 segundos !");
              if (!this.stuff3) {
                PvPMatchmaking.teleportToArena("Start", 5);
                this.stuff3 = true;
                if (PvPMatchmaking.player7.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team4", killerName);
                  PvPMatchmaking.wplayer4.add(killer);
                  plugin.saveConfig();
                } else if (PvPMatchmaking.player8.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team4", killerName);
                  PvPMatchmaking.wplayer4.add(killer);
                  plugin.saveConfig();
                }
              }
            }
          }
        } else if ((PvPMatchmaking.player9.contains(player)) || (PvPMatchmaking.player10.contains(player))) {
          event.setDeathMessage(PvPMatchmaking.green + killerName + PvPMatchmaking.blue + " triunfou sobre " + PvPMatchmaking.green + playerName + PvPMatchmaking.blue + "!");
          for(Player players: Bukkit.getOnlinePlayers()) {
            if (PvPMatchmaking.joinedPlayers.contains(players)) {
              PvPMatchmaking.spectating.remove(players);
              players.sendMessage(PvPMatchmaking.blue + "Proximo round em 5 segundos !");
              if (!this.stuff4) {
                PvPMatchmaking.teleportToArena("Start", 6);
                this.stuff4 = true;
                if (PvPMatchmaking.player9.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team5", killerName);
                  PvPMatchmaking.wplayer5.add(killer);
                  plugin.saveConfig();
                } else if (PvPMatchmaking.player10.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team5", killerName);
                  PvPMatchmaking.wplayer5.add(killer);
                  plugin.saveConfig();
                }
              }
            }
          }
        } else if ((PvPMatchmaking.player11.contains(player)) || (PvPMatchmaking.player12.contains(player))) {
          event.setDeathMessage(PvPMatchmaking.green + killerName + PvPMatchmaking.blue + " triunfou sobre " + PvPMatchmaking.green + playerName + PvPMatchmaking.blue + "!");
          for(Player players: Bukkit.getOnlinePlayers()) {
            if (PvPMatchmaking.joinedPlayers.contains(players)) {
              PvPMatchmaking.spectating.remove(players);
              players.sendMessage(PvPMatchmaking.blue + "Proximo round em 5 segundos !");
              if (!this.stuff5) {
                PvPMatchmaking.teleportToArena("Start", 7);
                this.stuff5 = true;
                if (PvPMatchmaking.player11.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team6", killerName);
                  PvPMatchmaking.wplayer6.add(killer);
                  plugin.saveConfig();
                } else if (PvPMatchmaking.player12.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team6", killerName);
                  PvPMatchmaking.wplayer6.add(killer);
                  plugin.saveConfig();
                }
              }
            }
          }
        } else if ((PvPMatchmaking.player13.contains(player)) || (PvPMatchmaking.player14.contains(player))) {
          event.setDeathMessage(PvPMatchmaking.green + killerName + PvPMatchmaking.blue + " triunfou sobre " + PvPMatchmaking.green + playerName + PvPMatchmaking.blue + "!");
          for(Player players: Bukkit.getOnlinePlayers()) {
            if (PvPMatchmaking.joinedPlayers.contains(players)) {
              PvPMatchmaking.spectating.remove(players);
              players.sendMessage(PvPMatchmaking.blue + "Proximo round em 5 segundos !");
              if (!this.stuff6) {
                PvPMatchmaking.teleportToArena("Start", 8);
                this.stuff6 = true;
                if (PvPMatchmaking.player13.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team7", killerName);
                  PvPMatchmaking.wplayer7.add(killer);
                  plugin.saveConfig();
                } else if (PvPMatchmaking.player14.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team7", killerName);
                  PvPMatchmaking.wplayer7.add(killer);
                  plugin.saveConfig();
                }
              }
            }
          }
        } else if ((PvPMatchmaking.player15.contains(player)) || (PvPMatchmaking.player16.contains(player))) {
          event.setDeathMessage(PvPMatchmaking.green + killerName + PvPMatchmaking.blue + " triunfou sobre " + PvPMatchmaking.green + playerName + PvPMatchmaking.blue + "!");
          for(Player players: Bukkit.getOnlinePlayers()) {
            if (PvPMatchmaking.joinedPlayers.contains(players)) {
              PvPMatchmaking.spectating.remove(players);
              players.sendMessage(PvPMatchmaking.blue + "Proximo round em 5 segundos !");
              if (!this.stuff7) {
                PvPMatchmaking.teleportToArena("Quarter", 1);
                this.stuff7 = true;
                if (PvPMatchmaking.player15.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team8", killerName);
                  PvPMatchmaking.wplayer8.add(killer);
                  plugin.saveConfig();
                } else if (PvPMatchmaking.player16.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Round1.Team8", killerName);
                  PvPMatchmaking.wplayer8.add(killer);
                  plugin.saveConfig();
                }
              }
            }
          }
        } else if ((PvPMatchmaking.wplayer1.contains(player)) || (PvPMatchmaking.wplayer2.contains(player))) {
          event.setDeathMessage(PvPMatchmaking.green + killerName + PvPMatchmaking.blue + " triunfou sobre " + PvPMatchmaking.green + playerName + PvPMatchmaking.blue + "!");
          for(Player players: Bukkit.getOnlinePlayers()) {
            if (PvPMatchmaking.joinedPlayers.contains(players)) {
              PvPMatchmaking.spectating.remove(players);
              players.sendMessage(PvPMatchmaking.blue + "Proximo round em 5 segundos !");
              if (!this.stuff8) {
                PvPMatchmaking.teleportToArena("Quarter", 2);
                this.stuff8 = true;
                if (PvPMatchmaking.wplayer1.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Quarter.Team1", killerName);
                  PvPMatchmaking.Quarter1.add(killer);
                  plugin.saveConfig();
                } else if (PvPMatchmaking.wplayer2.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Quarter.Team1", killerName);
                  PvPMatchmaking.Quarter1.add(killer);
                  plugin.saveConfig();
                }
              }
            }
          }
        } else if ((PvPMatchmaking.wplayer3.contains(player)) || (PvPMatchmaking.wplayer4.contains(player))) {
          event.setDeathMessage(PvPMatchmaking.green + killerName + PvPMatchmaking.blue + " triunfou sobre " + PvPMatchmaking.green + playerName + PvPMatchmaking.blue + "!");
          for(Player players: Bukkit.getOnlinePlayers()) {
            if (PvPMatchmaking.joinedPlayers.contains(players)) {
              PvPMatchmaking.spectating.remove(players);
              players.sendMessage(PvPMatchmaking.blue + "Proximo round em 5 segundos !");
              if (!this.stuff9) {
                PvPMatchmaking.teleportToArena("Quarter", 3);
                this.stuff9 = true;
                if (PvPMatchmaking.wplayer1.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Quarter.Team2", killerName);
                  PvPMatchmaking.Quarter2.add(killer);
                  plugin.saveConfig();
                } else if (PvPMatchmaking.wplayer2.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Quarter.Team2", killerName);
                  PvPMatchmaking.Quarter2.add(killer);
                  plugin.saveConfig();
                }
              }
            }
          }
        } else if ((PvPMatchmaking.wplayer5.contains(player)) || (PvPMatchmaking.wplayer6.contains(player))) {
          event.setDeathMessage(PvPMatchmaking.green + killerName + PvPMatchmaking.blue + " triunfou sobre " + PvPMatchmaking.green + playerName + PvPMatchmaking.blue + "!");
          for(Player players: Bukkit.getOnlinePlayers()) {
            if (PvPMatchmaking.joinedPlayers.contains(players)) {
              PvPMatchmaking.spectating.remove(players);
              players.sendMessage(PvPMatchmaking.blue + "Proximo round em 5 segundos !");
              if (!this.stuff10) {
                PvPMatchmaking.teleportToArena("Quarter", 4);
                this.stuff10 = true;
                if (PvPMatchmaking.wplayer1.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Quarter.Team3", killerName);
                  PvPMatchmaking.Quarter3.add(killer);
                  plugin.saveConfig();
                } else if (PvPMatchmaking.wplayer2.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Quarter.Team3", killerName);
                  PvPMatchmaking.Quarter3.add(killer);
                  plugin.saveConfig();
                }
              }
            }
          }
        } else if ((PvPMatchmaking.wplayer7.contains(player)) || (PvPMatchmaking.wplayer8.contains(player))) {
          event.setDeathMessage(PvPMatchmaking.green + killerName + PvPMatchmaking.blue + " triunfou sobre " + PvPMatchmaking.green + playerName + PvPMatchmaking.blue + "!");
          for(Player players: Bukkit.getOnlinePlayers()) {
            if (PvPMatchmaking.joinedPlayers.contains(players)) {
              PvPMatchmaking.spectating.remove(players);
              players.sendMessage(PvPMatchmaking.blue + "Proximo round em 5 segundos !");
              if (!this.stuff11) {
                PvPMatchmaking.teleportToArena("Semi", 1);
                this.stuff11 = true;
                if (PvPMatchmaking.wplayer1.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Quarter.Team4", killerName);
                  PvPMatchmaking.Quarter4.add(killer);
                  plugin.saveConfig();
                } else if (PvPMatchmaking.wplayer2.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Quarter.Team4", killerName);
                  PvPMatchmaking.Quarter4.add(killer);
                  plugin.saveConfig();
                }
              }
            }
          }
        } else if ((PvPMatchmaking.Quarter1.contains(player)) || (PvPMatchmaking.Quarter2.contains(player))) {
          event.setDeathMessage(PvPMatchmaking.green + killerName + PvPMatchmaking.blue + " triunfou sobre " + PvPMatchmaking.green + playerName + PvPMatchmaking.blue + "!");
          for(Player players: Bukkit.getOnlinePlayers()) {
            if (PvPMatchmaking.joinedPlayers.contains(players)) {
              PvPMatchmaking.spectating.remove(players);
              players.sendMessage(PvPMatchmaking.blue + "Proximo round em 5 segundos !");
              if (!this.stuff12) {
                PvPMatchmaking.teleportToArena("Semi", 2);
                this.stuff12 = true;
                if (PvPMatchmaking.Quarter1.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.SemiFinal.Team1", killerName);
                  PvPMatchmaking.Semi1.add(killer);
                  plugin.saveConfig();
                } else if (PvPMatchmaking.Quarter2.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.SemiFinal.Team1", killerName);
                  PvPMatchmaking.Semi1.add(killer);
                  plugin.saveConfig();
                }
              }
            }
          }
        } else if ((PvPMatchmaking.Quarter3.contains(player)) || (PvPMatchmaking.Quarter4.contains(player))) {
          event.setDeathMessage(PvPMatchmaking.green + killerName + PvPMatchmaking.blue + " triunfou sobre " + PvPMatchmaking.green + playerName + PvPMatchmaking.blue + "!");
          for(Player players: Bukkit.getOnlinePlayers()) {
            if (PvPMatchmaking.joinedPlayers.contains(players)) {
              PvPMatchmaking.spectating.remove(players);
              players.sendMessage(PvPMatchmaking.blue + "Proximo round em 5 segundos !");
              if (!this.stuff13) {
                PvPMatchmaking.teleportToArena("Final", 1);
                this.stuff13 = true;
                if (PvPMatchmaking.Quarter3.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.SemiFinal.Team1", killerName);
                  PvPMatchmaking.Semi2.add(killer);
                  plugin.saveConfig();
                } else if (PvPMatchmaking.Quarter4.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.SemiFinal.Team2", killerName);
                  PvPMatchmaking.Semi2.add(killer);
                  plugin.saveConfig();
                }
              }
            }
          }
        } else if ((PvPMatchmaking.Semi1.contains(player)) || (PvPMatchmaking.Semi2.contains(player))) {
          event.setDeathMessage(PvPMatchmaking.green + killerName + PvPMatchmaking.blue + " triunfou sobre " + PvPMatchmaking.green + playerName + PvPMatchmaking.blue + "!");
          player.getServer().broadcastMessage(PvPMatchmaking.Pre + " " + PvPMatchmaking.yellow + killerName + PvPMatchmaking.blue + " foi vitorioso!");
          for(Player players: Bukkit.getOnlinePlayers()) {
            if (PvPMatchmaking.joinedPlayers.contains(players)) {
              PvPMatchmaking.spectating.remove(players);
              players.sendMessage(PvPMatchmaking.blue + "Proximo round em 5 segundos !");
              if (!this.stuff14) {
                this.stuff14 = true;
                if (PvPMatchmaking.wplayer1.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Final.Team1", killerName);
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Final.Winner", killerName);
                  PvPMatchmaking.Final.add(killer);
                  plugin.saveConfig();
                } else if (PvPMatchmaking.wplayer2.contains(player)) {
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Final.Team1", killerName);
                  PvPMatchmaking.cfg.set("PvPMatchmaking.Winners.Final.Winner", killerName);
                  PvPMatchmaking.Final.add(killer);
                  plugin.saveConfig();
                }
                PvPMatchmaking.ResetGame();
              }
            }
          }
        }
      }
    }
    
    @EventHandler
    public static void respawner(PlayerRespawnEvent event) {
      Player player = event.getPlayer();
      org.bukkit.World world = Bukkit.getWorld(plugin.getConfig().getString("PvPMatchmaking.Locations.World"));
      org.bukkit.Location spec = new org.bukkit.Location(world, plugin.getConfig().getInt("PvPMatchmaking.Locations.Spectator.Spawn.block.x"), plugin.getConfig().getInt("PvPMatchmaking.Locations.Spectator.Spawn.block.y"), plugin.getConfig().getInt("PvPMatchmaking.Locations.Spectator.Spawn.block.z"));
      
      if ((PvPMatchmaking.joinedPlayers.contains(player)) || (PvPMatchmaking.spectating.contains(player))) {
        player.teleport(spec);
      }
    }
  }


/* Location:              C:\Users\User\Downloads\PvPMatchmaking_v1.2.jar!\me\Doubts\Listeners\DeathListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */