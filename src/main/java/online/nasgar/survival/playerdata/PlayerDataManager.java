package online.nasgar.survival.playerdata;

import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import online.nasgar.survival.Survival;
import online.nasgar.survival.database.mongodb.MongoManager;
import online.nasgar.survival.database.mongodb.MongoSerializer;
import online.nasgar.survival.utils.BukkitUtil;
import online.nasgar.survival.utils.TaskUtil;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

@Getter
public class PlayerDataManager implements Listener, MongoSerializer<PlayerData> {

    private final MongoManager mongoManager;

    private final Map<UUID, PlayerData> dataMap;

    public PlayerDataManager(Survival plugin){
        this.mongoManager = plugin.getMongoManager();
        this.dataMap = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, plugin);

        TaskUtil.runTaskTimer(() -> Bukkit.getOnlinePlayers().forEach(player -> this.save(player.getUniqueId(), false)), 0L, 30 * 1200L);
    }

    @Override public Document toDocument(PlayerData data) {
        Document document = new Document();

        document.put("uuid", data.getUuid().toString());
        document.put("lastConverser", data.getLastConverser());
        document.put("ignoredPlayers", data.getIgnoredPlayers());
        document.put("coins", data.getCoins());
        document.put("xp", data.getXp());
        document.put("time", data.getTime().get());
        document.put("tpm", data.isTpm());
        document.put("food", data.getFoodLevel());
        document.put("health", data.getHealth());
        document.put("effects", new GsonBuilder().serializeNulls().create().toJson(data.getEffects()));

        if (data.getRank() != null) {
            document.put("rank", data.getRank().getName());
        }

        try {
            document.put("items", BukkitUtil.itemStackArrayToBase64(data.getItems()));
            document.put("armor", BukkitUtil.itemStackArrayToBase64(data.getArmor()));
            document.put("backPackItems", BukkitUtil.itemStackArrayToBase64(data.getBackPackItems()));
        } catch (Exception e) {}

        return document;
    }

    @Override public PlayerData fromDocument(Document document) {
        PlayerData data = new PlayerData(UUID.fromString(document.getString("uuid")));

        data.setLastConverser(document.getString("lastConverser"));
        data.setIgnoredPlayers(document.getList("ignoredPlayers", String.class));
        data.setCoins(document.getInteger("coins"));
        data.setXp(document.getDouble("xp"));
        data.getTime().set(document.getInteger("time"));
        data.setTpm(document.getBoolean("tpm"));

        try {
            data.setBackPackItems(BukkitUtil.itemStackArrayFromBase64(document.getString("backPackItems")));
        } catch (Exception e) {}

        if (document.containsKey("rank")) {
            data.setRank(Survival.getInstance().getRankManager().getByName(document.getString("rank")));
        }

        try {
            data.setItems(BukkitUtil.itemStackArrayFromBase64(document.getString("items")));
            data.setArmor(BukkitUtil.itemStackArrayFromBase64(document.getString("armor")));
            data.setFoodLevel(document.getInteger("food"));
            data.setHealth(document.getDouble("health"));
            data.setEffects(document.get("effects") == null ? new ArrayList<>() : new GsonBuilder().serializeNulls().create().fromJson(document.getString("effects"), List.class));
        } catch (Exception e) {}

        return data;
    }

    /*
    AVISO | ESTOS METODOS NO SON MIOS - SOLO ES UN PEQUEÑO FORK
     */

    public void create(UUID uuid){
        if (!this.contains(uuid)){
            this.dataMap.put(uuid, new PlayerData(uuid));
        }
    }

    public void load(UUID uuid){
        PlayerData data = null;

        if (!this.contains(uuid)){
            Document document = this.getDataCollection().find(Filters.eq("uuid", uuid.toString())).first();

            if (document == null) {
                data = new PlayerData(uuid);
            } else {
                data = this.fromDocument(document);
            }
        }

        Player player = Bukkit.getPlayer(uuid);
        PlayerInventory inventory = player.getInventory();

        inventory.setArmorContents(data.getArmor());
        inventory.setContents(data.getItems());

        data.getEffects().forEach(player::addPotionEffect);

        player.setHealth(data.getHealth());
        player.setFoodLevel((int) data.getFoodLevel());

        this.dataMap.put(uuid, data);
    }

    public void save(UUID uuid, boolean remove) {
        PlayerData data = this.get(uuid);

        if (data == null) {
            return;
        }

        this.getDataCollection().replaceOne(Filters.eq("uuid", uuid.toString()), this.toDocument(data), new ReplaceOptions().upsert(true));

        if (remove) {
            this.dataMap.remove(uuid);
        }
    }

    public PlayerData get(UUID uuid){
        if (this.contains(uuid)){
            return this.dataMap.get(uuid);
        }

        Document document = this.getDataCollection().find(Filters.eq("uuid", uuid.toString())).first();

        if (document == null) {
            return null;
        }

        PlayerData data = this.fromDocument(document);
        this.dataMap.put(uuid, data);

        return data;
    }

    public boolean contains(UUID uuid){
        return this.dataMap.containsKey(uuid);
    }

    private MongoCollection<Document> getDataCollection(){
        return this.mongoManager.getCollection("playerData");
    }

    @EventHandler public void onPluginDisable(PluginDisableEvent event){
        TaskUtil.runTaskAsync(() -> Bukkit.getOnlinePlayers().forEach(player -> this.save(player.getUniqueId(), true)));
    }

    private String set(Object objectToSerialize, String string){
        return objectToSerialize == null ? string : null;
    }

    private ItemStack[] getArrayOrNull(String path, ItemStack[] itemStacks){
        return path == null ? itemStacks : null;
    }

}
