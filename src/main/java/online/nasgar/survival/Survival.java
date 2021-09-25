package online.nasgar.survival;

import lombok.Getter;
import online.nasgar.survival.chat.ChatPacket;
import online.nasgar.survival.chat.ChatPacketListener;
import online.nasgar.survival.command.management.CommandManager;
import online.nasgar.survival.config.ConfigFile;
import online.nasgar.survival.database.Authentication;
import online.nasgar.survival.listeners.PlayerListener;
import online.nasgar.survival.database.mongodb.MongoManager;
import online.nasgar.survival.playerdata.PlayerDataManager;
import online.nasgar.survival.rankup.RankManager;
import online.nasgar.survival.scoreboard.NautilusManager;
import online.nasgar.survival.scoreboard.NautilusScoreboardAdapter;
import online.nasgar.survival.tab.Tab;
import online.nasgar.survival.tab.adapter.TabAdapter;
import online.nasgar.survival.shop.ShopItemManager;
import online.nasgar.survival.utils.pyrite.Pyrite;
import online.nasgar.survival.utils.pyrite.PyriteCredentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Survival extends JavaPlugin {

    @Getter private static Survival instance;

    private ConfigFile configFile;

    private MongoManager mongoManager;

    private PlayerDataManager playerDataManager;
    private RankManager rankManager;
    private ShopItemManager shopItemManager;

    private Pyrite pyrite;
    private ChatPacketListener packetListener;

    @Override public void onEnable() {
        instance = this;

        this.configFile = new ConfigFile(this, "config.yml");

        this.setupDatabases();
        this.setupProviders();
        this.setupManagers();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override public void onDisable() {
        this.mongoManager.close();
        this.pyrite.unregisterContainer(this.packetListener);

        instance = null;
    }

    private void setupManagers(){
        new CommandManager(this);

        this.rankManager = new RankManager(this);
        this.playerDataManager = new PlayerDataManager(this);
        this.shopItemManager = new ShopItemManager();
    }

    private void setupDatabases(){

        this.mongoManager = new MongoManager(new Authentication(

                this.configFile.getString("mongodb.address"),
                this.configFile.getInt("mongodb.port"),
                this.configFile.getString("mongodb.database"),
                this.configFile.getBoolean("mongodb.authentication.enabled"),
                this.configFile.getString("mongodb.authentication.username"),
                this.configFile.getString("mongodb.authentication.password"))

        );

        this.pyrite = new Pyrite(new PyriteCredentials(
                this.configFile.getString("redis.address"),
                this.configFile.getString("redis.password"),
                this.configFile.getInt("redis.port")));

        this.packetListener = new ChatPacketListener();
        this.pyrite.registerContainer(this.packetListener);
    }

    private void setupProviders(){
        new NautilusManager(this, new NautilusScoreboardAdapter(), 20L);
        new Tab(this, new TabAdapter());
    }

    /*
    Tengo que testear ésto
     */

    public void sendMessage(Player player, String message) {
        this.pyrite.sendPacket(
                new ChatPacket(
                        player,
                        message,
                        this.getServer().getName()
                ),
                "chat"
        );
    }

}
