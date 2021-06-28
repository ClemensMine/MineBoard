package priv.clemens.logcraft.mine.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import priv.clemens.logcraft.mine.Mine;
import priv.clemens.logcraft.mine.Values;
import priv.clemens.logcraft.mine.file.LoadConfig;
import priv.clemens.logcraft.mine.scoreBoard.ScoreBoard;

import java.io.File;
import java.io.IOException;

/**
 * @author ClemensMine
 */
public class PlayerJoin implements Listener {
    @EventHandler
    private void playerJoinEvent(PlayerJoinEvent event) throws IOException{
        // 创建挖掘榜
        ScoreBoard.createScoreBoard(event.getPlayer());
        // 安全验证
        if(Values.players.contains(event.getPlayer().getName())) {
            return;
        }

        // 保存玩家列表
        Values.players.add(event.getPlayer().getName());
        LoadConfig loadConfig = new LoadConfig("/data","players.yml");
        loadConfig.set("players", Values.players);

        // 保存挖掘榜
        File file = new File(Mine.instance.getDataFolder(),"/data/scores.yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        Values.scores.put(event.getPlayer().getName(), 0);
        for(String name : Values.players){
            configuration.set(name, Values.scores.get(name));
        }
        configuration.save(file);
    }
}
