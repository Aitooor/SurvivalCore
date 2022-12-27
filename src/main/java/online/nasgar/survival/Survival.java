package online.nasgar.survival;

import me.yushust.message.MessageHandler;
import me.yushust.message.MessageProvider;
import me.yushust.message.bukkit.BukkitMessageAdapt;
import me.yushust.message.source.MessageSourceDecorator;
import net.cosmogrp.storage.dist.CachedRemoteModelService;
import net.cosmogrp.storage.dist.LocalModelService;
import net.cosmogrp.storage.mongo.MongoModelService;
import net.cosmogrp.storage.redis.connection.GsonRedis;
import net.cosmogrp.storage.redis.connection.JedisBuilder;
import net.cosmogrp.storage.redis.connection.JedisInstance;
import net.cosmogrp.storage.redis.connection.Redis;
import net.milkbowl.vault.economy.Economy;
import online.nasgar.survival.services.chat.ChatService;
import online.nasgar.survival.managers.command.CommandManager;
import online.nasgar.survival.managers.config.ConfigFile;
import online.nasgar.survival.services.database.Authentication;
import online.nasgar.survival.services.database.mongodb.MongoManager;
import online.nasgar.survival.listeners.ChatListener;
import online.nasgar.survival.listeners.PlayerListener;
import online.nasgar.survival.listeners.SpawnersListener;
import online.nasgar.survival.managers.menus.MenuManager;
import online.nasgar.survival.managers.playerdata.PlayerData;
import online.nasgar.survival.managers.playerdata.parser.PlayerMongoModelParser;
import online.nasgar.survival.services.playerdata.PlayerService;
import online.nasgar.survival.listeners.providers.ScoreboardListener;
import online.nasgar.survival.listeners.providers.TablistListener;
import online.nasgar.survival.services.redis.ChatChannelListener;
import online.nasgar.survival.services.redis.data.MessageData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Survival extends JavaPlugin {

    private static Survival instance;

    private String serverId;
    private ConfigFile configFile;
    private MessageHandler messageHandler;
    private Executor executor;
    private ChatService chatService;

    private Redis redis;

    private MongoManager mongoManager;
    private PlayerService playerService;
    private CachedRemoteModelService<PlayerData> playerModelService;
    private static Economy econ = null;

    @Override
    public void onEnable() {
        instance = this;

        executor = Executors.newCachedThreadPool();

        this.configFile = new ConfigFile(this, "config.yml");

        this.serverId = this.configFile.getString("id");

        this.setupNMessage();
        chatService = new ChatService(messageHandler);

        this.setupRedis();
        this.setupDatabases();
        this.setupServices();
        this.setupManagers();

        new MenuManager(this);

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        if (!setupEconomy()) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getPluginManager().registerEvents(new PlayerListener(playerService), this);
            Bukkit.getPluginManager().registerEvents(new ChatListener(redis, chatService, messageHandler, playerModelService), this);
            Bukkit.getPluginManager().registerEvents(new SpawnersListener(), this);
            Bukkit.getPluginManager().registerEvents(new TablistListener(), this);
            Bukkit.getPluginManager().registerEvents(new ScoreboardListener(playerModelService), this);
            // Register economy
        } else {
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        this.mongoManager.close();
        instance = null;
    }


    private void setupNMessage() {
        File langFolder = new File(this.getDataFolder(), "lang");
        try {
            langFolder.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MessageProvider messageProvider = MessageProvider
                .create(
                        MessageSourceDecorator
                                .decorate(BukkitMessageAdapt.newYamlSource(this, "lang/lang_%lang%.yml"))
                                .addFallbackLanguage("en")
                                .get(),
                        config -> {
                            config.specify(Player.class)
                                    .setLinguist(player -> player.getLocale().split("_")[0])
                                    .setMessageSender((sender, mode, message) -> sender.sendMessage(message));
                            config.specify(CommandSender.class)
                                    .setLinguist(commandSender -> "en")
                                    .setMessageSender((sender, mode, message) -> sender.sendMessage(message));
                            config.addInterceptor(s -> ChatColor.translateAlternateColorCodes('&', s));
                        }
                );
        messageHandler = MessageHandler.of(messageProvider);
    }

    private void setupRedis() {
        JedisInstance jedisInstance = JedisBuilder.builder()
                .setHost(configFile.getString("redis.address"))
                .setPassword(configFile.getString("redis.password"))
                .setPort(configFile.getInt("redis.port"))
                .setTimeout(2000)
                .build();

        redis = GsonRedis.builder(executor)
                .setJedis(jedisInstance)
                .setServerId(getServerId())
                .setParentChannel(configFile.getString("redis.channel.chat"))
                .build();

        redis.getMessenger().getChannel(ChatChannelListener.CHANNEL_NAME, MessageData.class)
                .addListener(new ChatChannelListener(chatService));

    }

    private void setupServices() {
        this.playerModelService = (CachedRemoteModelService<PlayerData>)
                MongoModelService.builder(PlayerData.class)
                        .modelParser(new PlayerMongoModelParser())
                        .cachedService(LocalModelService.create())
                        .database(mongoManager.getMongoDatabase())
                        .collection(configFile.getString("mongodb.collection"))
                        .executor(executor)
                        .build();

        this.playerService = new PlayerService(playerModelService);
    }

    private void setupManagers() { new CommandManager(playerModelService, messageHandler); }

    private void setupDatabases() {
        this.mongoManager = new MongoManager(new Authentication(
                this.configFile.getString("mongodb.address"),
                this.configFile.getInt("mongodb.port"),
                this.configFile.getString("mongodb.database"),
                this.configFile.getString("mongodb.authentication.username"),
                this.configFile.getString("mongodb.authentication.password"),
                this.configFile.getString("mongodb.authentication.uri")
        ));
    }

    private boolean setupEconomy() {
        if (!getServer().getPluginManager().isPluginEnabled("Vault")) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to Vault is disabled!", getDescription().getName()));
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to Rsp is null!", getDescription().getName()));
            return false;
        }

        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Survival getInstance() {
        return instance;
    }

    public String getServerId() {
        return serverId;
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public Executor getExecutor() {
        return executor;
    }

    public ChatService getChatService() {
        return chatService;
    }

    public Redis getRedis() {
        return redis;
    }

    public MongoManager getMongoManager() {
        return mongoManager;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public CachedRemoteModelService<PlayerData> getPlayerModelService() {
        return playerModelService;
    }

    public static Economy getEcon() {
        return econ;
    }
}