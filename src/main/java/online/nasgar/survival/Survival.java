package online.nasgar.survival;

import lombok.Getter;
import online.nasgar.survival.command.management.CommandManager;
import online.nasgar.survival.config.ConfigFile;
import online.nasgar.survival.listeners.PlayerListener;
import online.nasgar.survival.mongodb.MongoAuth;
import online.nasgar.survival.mongodb.MongoManager;
import online.nasgar.survival.playerdata.PlayerDataManager;
import online.nasgar.survival.scoreboard.NautilusManager;
import online.nasgar.survival.scoreboard.NautilusScoreboardAdapter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Survival extends JavaPlugin {

    @Getter private static Survival instance;

    private ConfigFile configFile;
    private MongoManager mongoManager;
    private PlayerDataManager playerDataManager;

    @Override public void onEnable() {
        instance = this;

        new CommandManager();
        new NautilusManager(this, new NautilusScoreboardAdapter(), 20L);

        this.configFile = new ConfigFile(this, "config.yml");

        this.mongoManager = new MongoManager(new MongoAuth(

                this.configFile.getString("mongodb.address"),
                this.configFile.getInt("mongodb.port"),
                this.configFile.getString("mongodb.database"),
                this.configFile.getBoolean("mongodb.authentication.enabled"),
                this.configFile.getString("mongodb.authentication.username"),
                this.configFile.getString("mongodb.authentication.password"))

        );

        this.playerDataManager = new PlayerDataManager(this.mongoManager);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override public void onDisable() {
        instance = null;
    }

}
