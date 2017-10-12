/*
 * The MIT License
 *
 * Copyright 2013 Goblom.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package nativelevel.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardBaseAPI {
    
    private final String boardName;
    private final String mainObjectiveName;
    
    private final Scoreboard scoreBoard;
    private Objective mainObjective;
    
    private final List<String> players = new ArrayList<String>();
    private DisplaySlot slot;
    
    /**
     * Create a Scoreboard with the given name in the desired slot and its blank by default
     * 
     * @param scoreBoardName Name of the scoreboard
     * @param mainObjectiveName Name of the main Objective
     * @param slot Slot where scoreboard should be displayed.... SIDEBAR, BELOW_NAME, PLAYER_LIST
     */
    public ScoreboardBaseAPI(String scoreBoardName, String mainObjectiveName, DisplaySlot slot) {
        this(scoreBoardName, mainObjectiveName, slot, null);
    }
    
    /**
     * Create a scoreboard with the given name, in the desired slot and not have it be blank by default
     * 
     * @param scoreBoardName Name of the scoreboard
     * @param mainObjectiveName Name of the main objective
     * @param slot Slot where the scoreboard should be displayed.... SIDEBAR, BELOW_NAME, PLAYER_LIST
     * @param lines The data to be shown on the scoreboard. 
     */
    public ScoreboardBaseAPI(String scoreBoardName, String mainObjectiveName, DisplaySlot slot, String[] lines) { //I like use String[]. If you do not replace the [] with ...
        this.boardName = scoreBoardName;
        this.mainObjectiveName = mainObjectiveName;
        this.slot = slot;
        this.scoreBoard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.mainObjective = scoreBoard.registerNewObjective("main", ObjectiveCriteria.DUMMY.getName());
        
        mainObjective.setDisplayName(mainObjectiveName);
        mainObjective.setDisplaySlot(slot);
        
        if (lines != null) {
            for (int i = 0; i < lines.length; i++) {
                addObjectiveScore(lines[i], i);
            }
        }
    }
    
    /**
     * ScoreBoard name
     * 
     * @return scoreBoard name
     */
    public String getBoardName() {
        return boardName;
    }
    
    /**
     * Main objective name
     * @return main objective name
     */
    public String getMainObjectiveName() {
        return mainObjectiveName;
    }
    
    public void setScoreboardName(String name) {
        mainObjective.setDisplayName(name);
        updatePlayersOnScoreboardNameChange();
    }
    
    /**
     * Change the Criteria of the main Objective. WARNING will reset all data in Main Objective
     * 
     * @param criteria Criteria to change to
     */
    public void changeCriteria(ObjectiveCriteria criteria) {
        this.mainObjective.unregister();
        this.mainObjective = null;
        this.mainObjective = scoreBoard.registerNewObjective("main", criteria.getName());
        mainObjective.setDisplayName(mainObjectiveName);
        mainObjective.setDisplaySlot(slot);
    }
    
    /**
     * Slot that the scoreboard is displayed
     * 
     * @return DisplaySlot
     */
    public DisplaySlot getDisplaySlot() {
        return slot;
    }
    
    /**
     * Change where the scoreboard is displayed
     * 
     * @param slot DisplaySlot to change to
     */
    public void setDisplaySlot(DisplaySlot slot) {
        this.slot = slot;
    }
    
    private void updatePlayersOnScoreboardNameChange() {
        for (String playerName : players) {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                player.setScoreboard(scoreBoard);
            }
        }
    }
    /**
     * Simple method to check all players in the list to make sure they still
     * have this scoreboard. If they do not then remove them from the list
     */
    public void checkPlayers() {
        for (String playerName : players) {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                if (!player.getScoreboard().equals(scoreBoard)) { // if (player.getScoreboard() != scoreBoard) {
                    players.remove(playerName);
                }
            } else players.remove(playerName);
        }
    }
    
    /**
     * Get all players that have this scoreboard
     * @return a list of player names
     */
    public List<String> getPlayers() {
        checkPlayers();
        return players;
    }
    
    /**
     * Gets all teams associated with this scoreboard
     * 
     * @return A team list
     */
    public Set<Team> getTeams() {
        return scoreBoard.getTeams();
    }
    
    /**
     * Gets a team associated with this scoreboard. Used to add players to a
     * team, etc.
     *
     * @param teamName Team to get
     * @return Team from scoreboard
     */
    public Team getTeam(String teamName) {
        return scoreBoard.getTeam(teamName);
    }
    
    /**
     * Register a new team with the scoreboard
     * 
     * @param teamName Team to register
     */
    public void registerTeam(String teamName) {
        scoreBoard.registerNewTeam(teamName);
    }
    
    /**
     * Get all objectivers registered on this scoreboard
     * 
     * @return A list of objectives
     */
    public Set<Objective> getObjectives() {
        return scoreBoard.getObjectives();
    }
    
    /**
     * Gets an objective from this scoreboard
     * 
     * @param objectiveName Objective to get
     * @return Objective found
     */
    public Objective getObjective(String objectiveName) {
        return scoreBoard.getObjective(objectiveName);
    }
    
    /**
     * Register a new objective with this scoreboard
     * 
     * @param objective Objective Name
     * @param criteria Criteria that the objective listens to
     */
    public void registerObjective(String objective, ObjectiveCriteria criteria) {
        scoreBoard.registerNewObjective(objective, criteria.getName());
    }
    
    /**
     * Gets the Objective in a slot
     * 
     * @param slot DisplaySlot to check
     * @return Objective in slot
     */
    public Objective getObjectiveInSlot(DisplaySlot slot) {
        return scoreBoard.getObjective(slot);
    }
    
    /**
     * Set the scoreboard of a player to this
     * 
     * @param player Player to set the scoreboard
     */
    public void setScoreboard(Player player) {
        player.setScoreboard(scoreBoard);
        players.add(player.getName());
    }
    
    /**
     * Sets the scoreboard of a player to a scoreboard other then this one
     * 
     * @param player Player to set the otherScoreboard
     * @param otherScoreboard Other Scoreboard to set
     */
    public void setScoreboard(Player player, Scoreboard otherScoreboard) {
        player.setScoreboard(otherScoreboard);
        players.remove(player.getName());
    }
    
    /**
     * Remove the scoreboard of a player
     * 
     * @param player Player to remove the scoreboard
     * @param blank Should it be a blank scoreboard or the main servers scoreboard
     */
    public void removeScoreboard(Player player, boolean blank) {
        if (blank) player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        else player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }
    
    /**
     * Add a score to the Main Objective. Main objective is first created when board is created.
     * 
     * @param scoreName Name of Score to add
     * @param score What should the amount displayed be
     */
    public void addObjectiveScore(String scoreName, int score) {
        mainObjective.getScore(Bukkit.getOfflinePlayer(scoreName)).setScore(score);
    }
    
    /**
     * Add a score an objective other then the Main objective.
     * 
     * @param objective Objective to add the score to
     * @param scoreName Name of the Score to add
     * @param score What should the amount displayed be
     */
    public void addObjectiveScore(Objective objective, String scoreName, int score) {
        objective.getScore(Bukkit.getOfflinePlayer(scoreName)).setScore(score);
    }
    
    /**
     * Get a Score from the Main Objective
     * 
     * @param score Score to get
     * @return Score gotten
     */
    public Score getObjectiveScore(String score) {
        return mainObjective.getScore(Bukkit.getOfflinePlayer(score));
    }
    
    /**
     * Get a score from an objective other then the Main Objective
     * 
     * @param objective Objective to get the score from
     * @param score Score to get
     * @return Score gotten
     */
    public Score getObjectiveScore(Objective objective, String score) {
        return objective.getScore(Bukkit.getOfflinePlayer(score));
    }
    
    /**
     * Sets the score of a score on the Main objective
     * 
     * @param scoreName Name of score to set
     * @param setScore Amount to display
     */
    public void setObjectiveScore(String scoreName, int setScore) {
        getObjectiveScore(scoreName).setScore(setScore);
    }
    
    /**
     * Sets the score of a score on an objective other then the Main Objective
     * 
     * @param objective Objective to set the score on
     * @param scoreName Name of score to set
     * @param setScore Amount to display
     */
    public void setObjectiveScore(Objective objective, String scoreName, int setScore) {
        getObjectiveScore(objective, scoreName).setScore(setScore);
    }
    
    /**
     * Get all scores on this scoreboard with the given name
     * 
     * @param score Scores to search for
     * @return A list of all the scores found
     */
    public Set<Score> getScores(String score) {
        return scoreBoard.getScores(Bukkit.getOfflinePlayer(score));
    }
    
    /**
     * What should the Objective listen to.
     * 
     * Bukkit ONLY supports: DUMMY, DEATH_COUND, HEALTH, PLAYER_KILL_COUNT, TOTAL_KILL_COUNT.
     * Everything else requires a 1.7 Bukkit Server with 1.7 features
     */
    public static enum ObjectiveCriteria {
        //pre 1.7
        DUMMY("dummy"), DEATH_COUNT("deathCount"), HEALTH("health"),
        PLAYER_KILL_COUNT("playerKillCount"), TOTAL_KILL_COUNT("totalKillCount"),
        
        //post 1.7
        achievement_MAKE_BREAD("achievement.makeBread"), achievement_BAKE_CAKE("achievement.bakeCake"),  achievement_DIAMONDS_TO_YOU("achievement.diamondsToYou"), 
        achievement_KILL_COW("achievement.killCow"), achievement_PORTAL("achievement.portal"), achievement_BUILD_FURNACE("achievement.buildFurnace"), 
        achievement_BUILD_SWORD("achievement.buildSword"), achievement_COOK_FISH("achievement.cookFish"), achievement_ENCHANTMENTS("achievement.enchantments"), 
        achievement_MINE_WOOD("achievement.mineWood"), achievement_OPEN_INVENTORY("achievement.openInventory"), achievement_EXPLORE_ALL_BIOMES("achievement.exploreAllBiomes"), 
        achievement_BUILD_WORKBENCH("achievement.buildWorkBench"), achievement_THE_END("achievement.theEnd"), achievement_BLAZE_ROD("achievement.blazeRod"), 
        achievement_SPAWN_WITHER("achievement.spawnWither"), achievement_BUILD_BETTER_PICKAXE("achievement.buildBetterPickaxe"), achievement_ACQUIRE_IRON("achievement.acquireIron"), 
        achievement_THE_END2("achievement.theEnd2"), achievement_BOOKCASE("achievement.bookcase"), achievement_FLYING_PIG("achievement.flyPig"), 
        achievement_GHAST("achievement.ghast"), achievement_SNIPE_SKELETON("achievement.snipeSkeleton"), achievement_DIAMONDS("achievement.diamonds"), 
        achievement_KILL_WITHER("achievement.killWither"), achievement_FULL_BEACON("achievement.fullBeacon"), achievement_BUILD_HOE("achievement.buildHoe"), 
        achievement_BREED_COW("achievement.breedCow"), achievement_ON_A_RAIL("achievement.onARail"), achievement_OVERKILL("achievement.overkill"), 
        achievement_KILL_ENEMY("achievement.killEnemy"), achievement_POTION("achievement.potion"), achievement_BUILD_PICKAXE("achievement.buildPickaxe"),
        
        stat_DAMAGE_DEALT("stat.damageDealt"), stat_DAMAGE_TAKE("stat.damageTaken"), stat_LEAVE_GAME("stat.leaveGame"), 
        stat_MINECART_ONE_CM("stat.minecartOneCm"), stat_SWIM_ONE_CM("stat.swimOneCm"), stat_WALK_ONE_CM("stat.walkOneCm"), 
        stat_HORSE_ONE_CM("stat.horseOneCm"), stat_PIG_ONE_CM("stat.pigOneCm"), stat_FLY_ONE_CM("stat.flyOneCm"), 
        stat_BOAT_ONE_CM("stat.boatOneCm"), stat_FALL_ONE_CM("stat.fallOneCm"), stat_CLIMB_ONE_CM("stat.climbOneCm"), 
        stat_DIVE_ONE_CM("stat.diveOneCm"), stat_FISH_CAUGHT("stat.fishCaught"), stat_JUNK_FISHED("stat.junkFished"), 
        stat_TREASURE_FISHED("stat.treasureFished"), stat_PLAY_ONE_MINUE("stat.playOneMinute"), stat_PLAYER_KILLS("stat.playerKills"), 
        stat_MOB_KILLS("stat.mobKills"), stat_ANIMALS_BRED("stat.animalsBred"), stat_JUMP("stat.jump"), 
        stat_DROP("stat.drop"), stat_DEATHS("stat.deaths"),
        
        stat_killEntity_SILVERFISH("stat.killEntity.Silverfish"), stat_killEntity_ZOMBIE("stat.killEntity.Zombie"), stat_killEntity_BLAZE("stat.killEntity.Blaze"), 
        stat_killEntity_PIG("stat.killEntity.Pig"), stat_killEntity_CREEPER("stat.killEntity.Creeper"), stat_killEntity_COW("stat.killEntity.Cow"), 
        stat_killEntity_GHAS("stat.killEntity.Ghast"), stat_killEntity_WHICH("stat.killEntity.Witch"), stat_killEntity_SQUID("stat.killEntity.Squid"), 
        stat_killEntity_SPIDER("stat.killEntity.Spider"), stat_killEntity_VILLATER("stat.killEntity.Villager"), stat_killEntity_ENDERMAN("stat.killEntity.Enderman"), 
        stat_killEntity_LAVA_SLIME("stat.killEntity.LavaSlime"), stat_killEntity_PIG_ZOMBIE("stat.killEntity.PigZombie"), stat_killEntity_WOLF("stat.killEntity.Wolf"), 
        stat_killEntity_SHEEP("stat.killEntity.Sheep"), stat_killEntity_CHIKEN("stat.killEntity.Chicken"), stat_killEntity_SLIME("stat.killEntity.Slime"), 
        stat_killEntity_SLELETON("stat.killEntity.Skeleton"), stat_killEntity_BAT("stat.killEntity.Bat"), stat_killEntity_MUSHROOM_COW("stat.killEntity.MushroomCow"), 
        stat_killEntity_CAVE_SPIDER("stat.killEntity.CaveSpider"), stat_killEntity_HORSE("stat.killEntity.EntityHorse"), // Horse ??? Wiki might be mispelled
        stat_killEntity_OCELOT("stat.killEntity.Ozelot"), //Ocelot ??? Wiki might be mispelled
        
        stat_entityKilledBy_WOLF("stat.entityKilledBy.Wolf"), stat_entityKilledBy_ENDERMAN("stat.entityKilledBy.Enderman"), stat_entityKilledBy_SLIME("stat.entityKilledBy.Slime"), 
        stat_entityKilledBy_LAVA_SLIME("stat.entityKilledBy.LavaSlime"), stat_entityKilledBy_SPIDER("stat.entityKilledBy.Spider"), stat_entityKilledBy_CREEPER("stat.entityKilledBy.Creeper"), 
        stat_entityKilledBy_BAT("stat.entityKilledBy.Bat"), stat_entityKilledBy_SQUID("stat.entityKilledBy.Squid"), stat_entityKilledBy_PIG_ZOMBIE("stat.entityKilledBy.PigZombie"), 
        stat_entityKilledBy_SILVERHIST("stat.entityKilledBy.Silverfish"), stat_entityKilledBy_SKELETON("stat.entityKilledBy.Skeleton"), stat_entityKilledBy_WHICH("stat.entityKilledBy.Witch"), 
        stat_entityKilledBy_PIG("stat.entityKilledBy.Pig"), stat_entityKilledBy_BLAZE("stat.entityKilledBy.Blaze"), stat_entityKilledBy_SHEEP("stat.entityKilledBy.Sheep"), 
        stat_entityKilledBy_MUSHROOM_COW("stat.entityKilledBy.MushroomCow"), stat_entityKilledBy_CAVE_SPIDER("stat.entityKilledBy.CaveSpider"), stat_entityKilledBy_VILLAGER("stat.entityKilledBy.Villager"), 
        stat_entityKilledBy_ZOMBIE("stat.entityKilledBy.Zombie"), stat_entityKilledBy_CHICKEN("stat.entityKilledBy.Chicken"), stat_entityKilledBy_COW("stat.entityKilledBy.Cow"), 
        stat_entityKilledBy_GHAST("stat.entityKilledBy.Ghast"), stat_entityKilledBy_HORSE("stat.entityKilledBy.EntityHorse"), // Horse ??? Wiki might be mispelled
        stat_entityKilledBy_OCELOT("stat.entityKilledBy.Ozelot") //Ocelot ??? Wiki might be mispelled
        
        /**
         * Missing... http://minecraft.gamepedia.com/Scoreboard#Objectives
         * 
         * - stat.craftItem
         * - stat.useItem
         * - stat.breakItem
         * - stat.mineBlock
         */
        ;
        private final String name;
        private ObjectiveCriteria(String name) { this.name = name; }
        public String getName() { return name; }
        public static String getCriteriaWithID(ObjectiveCriteria crit, int id) { return crit.getName() + "." + id; }
    }
    
    public enum Stat {
            CRAFT_ITEM("stat.craftItem"), USE_ITEM("stat.useItem"), BREAK_ITEM("stat.breakItem"), MINE_BLOCK("stat.mineBlock");
            private final String name;
            private Stat(String name) { this.name = name; }
            public String getName() { return name; }
            public static String getStatWithID(Stat stat, int id) { return stat.getName() + "." + id; }
        }
}