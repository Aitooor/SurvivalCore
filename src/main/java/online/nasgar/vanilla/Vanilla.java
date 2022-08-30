package online.nasgar.vanilla;

import lombok.Getter;
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
import online.nasgar.vanilla.auctions.AuctionsManager;
import online.nasgar.vanilla.backpack.BackPackMenu;
import online.nasgar.vanilla.services.chat.ChatService;
import online.nasgar.vanilla.managers.command.CommandManager;
import online.nasgar.vanilla.managers.config.ConfigFile;
import online.nasgar.vanilla.services.database.Authentication;
import online.nasgar.vanilla.services.database.mongodb.MongoManager;
import online.nasgar.vanilla.listeners.ChatListener;
import online.nasgar.vanilla.listeners.PlayerListener;
import online.nasgar.vanilla.listeners.SpawnersListener;
import online.nasgar.vanilla.managers.menus.MenuManager;
import online.nasgar.vanilla.managers.playerdata.PlayerData;
import online.nasgar.vanilla.managers.playerdata.parser.PlayerMongoModelParser;
import online.nasgar.vanilla.services.playerdata.PlayerService;
import online.nasgar.vanilla.providers.BoardListener;
import online.nasgar.vanilla.providers.TablistListener;
import online.nasgar.vanilla.services.redis.ChatChannelListener;
import online.nasgar.vanilla.services.redis.data.MessageData;
import online.nasgar.vanilla.shop.ShopItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Getter
public class Vanilla extends JavaPlugin {

    @Getter
    private static Vanilla instance;

    private String serverId;

    private ConfigFile configFile;

    private MongoManager mongoManager;

    private Executor executor;

    private ChatService chatService;

    private MessageHandler messageHandler;

    private Redis redis;

    private PlayerService playerService;
    private CachedRemoteModelService<PlayerData> playerModelService;
    private ShopItemManager shopItemManager;

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
        new AuctionsManager();

        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getPluginManager().registerEvents(new PlayerListener(playerService, messageHandler, playerModelService), this);
            Bukkit.getPluginManager().registerEvents(new ChatListener(redis, chatService), this);
            Bukkit.getPluginManager().registerEvents(new SpawnersListener(), this);
            Bukkit.getPluginManager().registerEvents(new TablistListener(), this);
            Bukkit.getPluginManager().registerEvents(new BoardListener(playerModelService), this);
            Bukkit.getPluginManager().registerEvents(new BackPackMenu(playerModelService), this);
        } else {
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        AuctionsManager.getInstance().save();

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
                        .collection("users")
                        .executor(executor)
                        .build();

        this.playerService = new PlayerService(playerModelService);
    }

    private void setupManagers() {
        new CommandManager(playerModelService, messageHandler);

        this.shopItemManager = new ShopItemManager();
    }

    private void setupDatabases() {

        this.mongoManager = new MongoManager(new Authentication(

                this.configFile.getString("mongodb.address"),
                this.configFile.getInt("mongodb.port"),
                this.configFile.getString("mongodb.database"),
                this.configFile.getString("mongodb.authentication.username"),
                this.configFile.getString("mongodb.authentication.password"),
                this.configFile.getString("mongodb.authentication.uri")
        )

        );
    }
}