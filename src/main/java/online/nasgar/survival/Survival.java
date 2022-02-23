package online.nasgar.survival;

import lombok.Getter;
import net.cosmogrp.storage.ModelService;
import net.cosmogrp.storage.mongo.MongoModelService;
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
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.playerdata.service.PlayerCacheModelService;
import online.nasgar.survival.playerdata.service.PlayerMongoModelService;
import online.nasgar.survival.playerdata.service.PlayerService;
import online.nasgar.survival.providers.BoardListener;
import online.nasgar.survival.providers.TablistListener;
import online.nasgar.survival.randomtp.RandomTPManager;
import online.nasgar.survival.redis.CoreRedisDatabase;
import online.nasgar.survival.shop.ShopItemManager;
import online.nasgar.survival.warp.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Getter
public class Survival extends JavaPlugin {

    @Getter
    private static Survival instance;

    private String serverId;

    private ConfigFile configFile, warpsFile;

    private MongoManager mongoManager;

    private Executor executor;

    private PlayerService playerService;
    private ModelService<PlayerData> playerCacheModelService;
    private MongoModelService<PlayerData> playerDataMongoModelService;
    private ShopItemManager shopItemManager;

    @Override
    public void onEnable() {
        instance = this;

        executor = Executors.newCachedThreadPool();

        this.configFile = new ConfigFile(this, "config.yml");
        this.warpsFile = new ConfigFile(this, "warps.yml");

        this.serverId = this.configFile.getString("id");

        this.setupDatabases();
        this.setupServices();
        this.setupManagers();

        new CoreRedisDatabase();

        new MenuManager(this);
        new WarpManager();
        new GravesManager();
        new AuctionsManager();

        new RandomTPManager();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(playerService, playerCacheModelService), this);
        Bukkit.getPluginManager().registerEvents(new SpawnersListener(), this);
        Bukkit.getPluginManager().registerEvents(new TablistListener(), this);
        Bukkit.getPluginManager().registerEvents(new BoardListener(), this);
        Bukkit.getPluginManager().registerEvents(new BackPackMenu(playerCacheModelService), this);

        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {
        WarpManager.getInstance().save();
        AuctionsManager.getInstance().save();

        this.mongoManager.close();
        instance = null;
    }

    private void setupServices() {
        this.playerCacheModelService = new PlayerCacheModelService();
        this.playerDataMongoModelService = new PlayerMongoModelService(null, mongoManager.getMongoDatabase(), playerCacheModelService);

        this.playerService = new PlayerService(playerCacheModelService, playerDataMongoModelService);
    }

    private void setupManagers() {
        new CommandManager(playerCacheModelService);

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