package priv.clemens.logcraft.mine.scoreBoard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import priv.clemens.logcraft.mine.Values;

/**
 * @author ClemensMine
 */
public class ScoreBoard {

    public static void createScoreBoard(Player player){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("mined","dummy", Values.title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for(String name : Values.scores.keySet()){
            Score score = objective.getScore(name);
            score.setScore(Values.scores.get(name));
        }
        player.setScoreboard(scoreboard);
    }

    public static void refreshScoreBoardForAllOnlinePlayers(){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(Values.hidePlayers.contains(player.getName())){
                continue;
            }
            Scoreboard scoreboard = player.getScoreboard();
            Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
            for(String name : Values.players){
                assert false;
                Score score = objective.getScore(name);
                score.setScore(Values.scores.get(name));
            }
        }
    }

    public static void removeScoreBoard(Player player){
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        scoreboard.clearSlot(DisplaySlot.SIDEBAR);
        assert objective != null;
        objective.unregister();
        player.setScoreboard(scoreboard);
    }
}
