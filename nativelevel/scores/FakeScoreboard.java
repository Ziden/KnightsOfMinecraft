package nativelevel.scores;

/**
 *
 * @author Gabriel
 */

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

public class FakeScoreboard {
    
    private HashMap<DisplaySlot, String> displayNames = new HashMap<DisplaySlot, String>();
    private HashMap<DisplaySlot, HashMap<String, Integer>> objectives = new HashMap<DisplaySlot, HashMap<String, Integer>>();
    private HashMap<String, FakeTeam> teams = new HashMap<String, FakeTeam>();

    public FakeTeam createFakeTeam(String name) {
        FakeTeam team = new FakeTeam(name);
        teams.put(name, team);
        return team;
    }

    public FakeTeam getFakeTeam(String name) {
        if (teams.containsKey(name))
            return teams.get(name);
        return null;
    }

    public void hideScore(DisplaySlot slot, String name) {
        if (objectives.containsKey(slot)) {
            if (objectives.get(slot).containsKey(name)) {
                objectives.get(slot).remove(name);
                if (objectives.get(slot).isEmpty())
                    objectives.remove(slot);
            }
        }
    }

    public void makeScore(DisplaySlot slot, String name, int score) {
        if (!objectives.containsKey(slot))
            objectives.put(slot, new HashMap<String, Integer>());
        objectives.get(slot).put(name, score);
    }

    public void setDisplayName(DisplaySlot slot, String name) {
        displayNames.put(slot, name);
    }

    public void setupObjectives(Player player) {
        for (DisplaySlot slot : displayNames.keySet()) {
            ScoreboardManager.setDisplayName(player, slot, displayNames.get(slot));
        }
        for (DisplaySlot slot : objectives.keySet()) {
            for (String name : objectives.get(slot).keySet()) {
                int score = objectives.get(slot).get(name);
                ScoreboardManager.makeScore(player, slot, name, score);
            }
        }
        for (FakeTeam fakeTeam : teams.values()) {
            for (int x =0;x<fakeTeam.getPlayers().size();x++) {
                String p = fakeTeam.getPlayers().get(x);
                ScoreboardManager.addToTeam(player, p, fakeTeam.getTeamName(), fakeTeam.getPrefix(), fakeTeam.getSuffix(),
                        fakeTeam.canSeeInvisiblePlayers());
            }
        }
    }

    public Collection<FakeTeam> getFakeTeams() {
        return teams.values();
    }

}