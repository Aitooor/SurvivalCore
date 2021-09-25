package online.nasgar.survival.playerdata;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import online.nasgar.survival.Survival;
import online.nasgar.survival.database.mongodb.MongoManager;
import online.nasgar.survival.database.mongodb.MongoSerializer;
import online.nasgar.survival.utils.BukkitUtil;
import online.nasgar.survival.utils.TaskUtil;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        Document document = new Document("_id", data.getUuid());

        document.put("lastConverser", data.getLastConverser());
        document.put("ignoredPlayers", data.getIgnoredPlayers());
        document.put("coins", data.getCoins());
        document.put("xp", data.getXp());
        document.put("time", data.getTime().get());
        document.put("tpm", data.isTpm());
        document.put("rank", data.getRank().getName());

        document.put("items", this.set(data.getItems(), BukkitUtil.serializeItemStackArray(data.getItems())));
        document.put("armor", this.set(data.getArmor(), BukkitUtil.serializeItemStackArray(data.getArmor())));

        return document;
    }

    @Override public PlayerData fromDocument(Document document) {
        PlayerData data = new PlayerData(UUID.fromString(document.getString("_id")));

        data.setLastConverser(document.getString("lastConverser"));
        data.setIgnoredPlayers(Collections.singletonList(document.getString("ignoredPlayers")));
        data.setCoins(document.getInteger("coins"));
        data.setXp(document.getInteger("xp"));
        data.getTime().set(document.getInteger("time"));
        data.setTpm(document.getBoolean("tpm"));
        data.setRank(Survival.getInstance().getRankManager().getByName(document.getString("rank")));

        data.setItems(this.getArrayOrNull(document.getString("items"), BukkitUtil.deserializeItemStackArray(document.getString("items"))));
        data.setArmor(this.getArrayOrNull(document.getString("armor"), BukkitUtil.deserializeItemStackArray(document.getString("armor"))));

        return data;
    }

    /*
    AVISO | ESTOS METODOS NO SON MIOS - SOLO ES UN PEQUEÑO FORK
     */

    public void create(UUID uuid){
        if (!this.contains(uuid)){
            this.getDataCollection().insertOne(new Document("_id", uuid));
            this.dataMap.put(uuid, new PlayerData(uuid));
        }
    }

    public void load(UUID uuid){
        if (this.contains(uuid)){
            return;
        }

        Document document = this.getDataCollection().find(Filters.eq("_id", uuid)).first();

        if (document == null) {
            this.dataMap.put(uuid, new PlayerData(uuid));
            return;
        }

        PlayerData data = this.fromDocument(document);

        PlayerInventory inventory = Bukkit.getPlayer(uuid).getInventory();

        inventory.setArmorContents(data.getArmor());
        inventory.setContents(data.getItems());

        this.dataMap.put(uuid, data);
    }

    public void save(UUID uuid, boolean remove) {
        PlayerData data = this.get(uuid);

        if (data == null) {
            return;
        }

        PlayerInventory inventory = Bukkit.getPlayer(uuid).getInventory();

        data.setArmor(data.getArmor());
        data.setItems(inventory.getContents());

        this.getDataCollection().replaceOne(Filters.eq("_id", uuid), this.toDocument(data), new UpdateOptions().upsert(true));

        if (remove) {
            this.dataMap.remove(uuid);
        }
    }

    public PlayerData get(UUID uuid){
        if (this.contains(uuid)){
            return this.dataMap.get(uuid);
        }

        Document document = this.getDataCollection().find(Filters.eq("_id", uuid)).first();

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
