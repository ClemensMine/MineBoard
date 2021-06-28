package priv.clemens.logcraft.mine.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import priv.clemens.logcraft.mine.Mine;
import priv.clemens.logcraft.mine.Values;
import priv.clemens.logcraft.mine.scoreBoard.ScoreBoard;

import java.io.File;
import java.io.IOException;

/**
 * @author ClemensMine
 */
public class PlayerChat implements Listener {
    @EventHandler
    private void playerChatEvent(AsyncPlayerChatEvent event){
        if(event.isCancelled()){
            return;
        }

        File file = new File(Mine.instance.getDataFolder(), "/data/players.yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        // 隐藏挖掘榜
        if(event.getMessage().equals("!!hide")){
            event.setCancelled(true);
            // 安全验证
            if(Values.hidePlayers.contains(event.getPlayer().getName())){
                return;
            }
            Values.hidePlayers.add(event.getPlayer().getName());
            // 保存文件
            configuration.set("hide", Values.hidePlayers);
            try {
                configuration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 删除侧边栏
            ScoreBoard.removeScoreBoard(event.getPlayer());
        }else if(event.getMessage().equals("!!show")){
            event.setCancelled(true);
            // 安全验证
            Values.hidePlayers.remove(event.getPlayer().getName());
            // 保存文件
            configuration.set("hide", Values.hidePlayers);
            try {
                configuration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.getPlayer().sendMessage("§c请重新登录以启用挖掘榜");
        }
    }
}
