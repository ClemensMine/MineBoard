package priv.clemens.logcraft.mine.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import priv.clemens.logcraft.mine.Values;
import priv.clemens.logcraft.mine.scoreBoard.ScoreBoard;

/**
 * @author ClemensMine
 */
public class PlayerBreakBlock implements Listener {
    @EventHandler
    private void playerBreak(BlockBreakEvent event){
        if(event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        // 增加挖掘数量
        Values.scores.put(player.getName(), Values.scores.get(player.getName()) + 1);

        if(Values.hidePlayers.contains(player.getName())){
            return;
        }
        // 刷新挖掘榜
        ScoreBoard.refreshScoreBoardForAllOnlinePlayers();
    }
}
