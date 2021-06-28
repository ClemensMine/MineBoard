package priv.clemens.logcraft.mine;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import priv.clemens.logcraft.mine.events.PlayerBreakBlock;
import priv.clemens.logcraft.mine.events.PlayerChat;
import priv.clemens.logcraft.mine.events.PlayerJoin;
import priv.clemens.logcraft.mine.file.LoadConfig;
import priv.clemens.logcraft.mine.file.SaveConfigs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author ClemensMine
 */
public final class Mine extends JavaPlugin {

    public static Mine instance;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();
        LoadConfig.loadDefaultConfig();
        try {
            createFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoadConfig.loadDataConfig();

        getServer().getPluginManager().registerEvents(new PlayerChat(),this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(),this);
        getServer().getPluginManager().registerEvents(new PlayerBreakBlock(),this);

        SaveConfigs.autoSave();

        getServer().getLogger().info("挖掘榜已启动");
    }

    @Override
    public void onDisable() {
        try {
            SaveConfigs.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getServer().getLogger().info("挖掘榜已卸载");
    }

    private void createFiles() throws IOException {
        File file = new File(getDataFolder(),"/data");
        if(!file.exists()){
            file.mkdirs();
        }else {
            return;
        }
        file = new File(getDataFolder(),"/data/players.yml");
        if(!file.exists()){
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            configuration.set("players", new ArrayList<>());
            configuration.set("hide", new ArrayList<>());
            configuration.save(file);
            file.createNewFile();
        }
        file = new File(getDataFolder(),"/data/scores.yml");
        if(!file.exists()){
            file.createNewFile();
        }
    }
}
