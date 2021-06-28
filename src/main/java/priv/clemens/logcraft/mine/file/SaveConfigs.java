package priv.clemens.logcraft.mine.file;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import priv.clemens.logcraft.mine.Mine;
import priv.clemens.logcraft.mine.Values;

import java.io.File;
import java.io.IOException;

/**
 * @author ClemensMine
 */
public class SaveConfigs {
    private static File file;
    private static FileConfiguration configuration;

    public static void save() throws IOException {
        // 保存scores.yml
        file = new File(Mine.instance.getDataFolder(), "/data/scores.yml");
        configuration = YamlConfiguration.loadConfiguration(file);
        for(String name : Values.scores.keySet()){
            configuration.set(name, Values.scores.get(name));
        }
        configuration.save(file);

        // 保存players.yml
        file = new File(Mine.instance.getDataFolder(), "/data/players.yml");
        configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("players", Values.players);
        configuration.set("hide", Values.hidePlayers);
        configuration.save(file);
    }

    public static void autoSave(){
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Bukkit.getLogger().info("挖掘榜已保存。");
                    save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskTimerAsynchronously(Mine.instance,20L,300 * 20);
    }
}
