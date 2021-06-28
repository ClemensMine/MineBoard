package priv.clemens.logcraft.mine.file;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import priv.clemens.logcraft.mine.Mine;
import priv.clemens.logcraft.mine.Values;

import java.io.File;
import java.io.IOException;

/**
 * @author ClemensMine
 */
public class LoadConfig {
    private static String fileLink;
    private static String fileName;

    public LoadConfig(String link, String file){
        // 需要加 "/"
        fileLink = Mine.instance.getDataFolder() + link;
        // 需要加后缀
        fileName = file;
    }

    public static void loadDefaultConfig(){
        Values.title = Mine.instance.getConfig().getString("title");
        Values.ifEnable = Mine.instance.getConfig().getBoolean("enable");
    }

    public static void loadDataConfig(){
        LoadConfig loadConfig = new LoadConfig("/data","players.yml");
        if(!loadConfig.exists()){
            return;
        }
        File file = new File(Mine.instance.getDataFolder(), "/data/players.yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        Values.players = configuration.getStringList("players");
        Values.hidePlayers = configuration.getStringList("hide");
        loadConfig = new LoadConfig("/data","scores.yml");
        for (String name : Values.players){
            Values.scores.put(name, (Integer) loadConfig.get(name));
        }
    }

    public Object get(String msg){
        File file = new File(fileLink+ "/" + fileName);
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        return configuration.get(msg);
    }

    public void set(String msg, Object value) throws IOException {
        File file = new File(fileLink+ "/" + fileName);
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set(msg, value);
        configuration.save(file);
    }

    public Boolean exists(){
        File file = new File(fileLink + "/" + fileName);
        return file.exists();
    }

}
