package online.nasgar.survival;

import lombok.Getter;
import online.nasgar.survival.auctions.AuctionsManager;
import online.nasgar.survival.backpack.BackPackMenu;
import online.nasgar.survival.command.management.CommandManager;
import online.nasgar.survival.config.ConfigFile;
import online.nasgar.survival.database.Authentication;
import online.nasgar.survival.database.mongodb.MongoManager;
import online.nasgar.survival.graves.GravesManager;
import online.nasgar.survival.listeners.PlayerListener;
import online.nasgar.survival.listeners.SpawnersListener;
import online.nasgar.survival.menu.MenuManager;
import online.nasgar.survival.playerdata.PlayerDataManager;
import online.nasgar.survival.providers.BoardListener;
import online.nasgar.survival.providers.TablistListener;
import online.nasgar.survival.randomtp.RandomTPManager;
import online.nasgar.survival.rankup.RankManager;
import online.nasgar.survival.redis.CoreRedisDatabase;
import online.nasgar.survival.shop.ShopItemManager;
import online.nasgar.survival.warp.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Survival extends JavaPlugin {

    @Getter
    private static Survival instance;

    private ConfigFile configFile, warpsFile;

    private MongoManager mongoManager;

    private PlayerDataManager playerDataManager;
    private RankManager rankManager;
    private ShopItemManager shopItemManager;

    @Override
    public void onEnable() {
        instance = this;

        this.configFile = new ConfigFile(this, "config.yml");
        this.warpsFile = new ConfigFile(this, "warps.yml");

        this.setupDatabases();
        this.setupManagers();

        new CoreRedisDatabase();

        new MenuManager(this);
        new WarpManager();
        new GravesManager();
        new AuctionsManager();

        new RandomTPManager();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnersListener(), this);
        Bukkit.getPluginManager().registerEvents(new TablistListener(), this);
        Bukkit.getPluginManager().registerEvents(new BoardListener(), this);
        Bukkit.getPluginManager().registerEvents(new BackPackMenu(), this);
    }

    @Override
    public void onDisable() {
        WarpManager.getInstance().save();
        AuctionsManager.getInstance().save();
        this.mongoManager.close();
        instance = null;
    }

    private void setupManagers() {
        new CommandManager(this);

        this.rankManager = new RankManager(this);
        this.playerDataManager = new PlayerDataManager(this);
        this.shopItemManager = new ShopItemManager();
    }

    private void setupDatabases() {

        this.mongoManager = new MongoManager(new Authentication(

                this.configFile.getString("mongodb.address"),
                this.configFile.getInt("mongodb.port"),
                this.configFile.getString("mongodb.database"),
                this.configFile.getBoolean("mongodb.authentication.enabled"),
                this.configFile.getString("mongodb.authentication.username"),
                this.configFile.getString("mongodb.authentication.password"))

        );
    }
}