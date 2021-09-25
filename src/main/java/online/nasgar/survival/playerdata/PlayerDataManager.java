package online.nasgar.survival.playerdata;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import online.nasgar.survival.Survival;
import online.nasgar.survival.database.mongodb.MongoManager;
import online.nasgar.survival.database.mongodb.MongoSerializer;
import online.nasgar.survival.utils.TaskUtil;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class PlayerDataManager implements Listener, MongoSerializer<PlayerData> {

    private final MongoManager mongoManager;
    private final Map<UUID, PlayerData> dataMap;

    public PlayerDataManager(MongoManager mongoManager){
        this.mongoManager = mongoManager;
        this.dataMap = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, Survival.getInstance());
    }

    @Override public Document toDocument(PlayerData data) {
        Document document = new Document("_id", data.getUuid());

        document.put("lastConverser", data.getLastConverser());
        document.put("ignoredPlayers", data.getIgnoredPlayers());
        document.put("coins", data.getCoins());
        document.put("time", data.getTime().get());
        document.put("tpm", data.isTpm());

        return document;
    }

    @Override public PlayerData fromDocument(Document document) {
        PlayerData data = new PlayerData(UUID.fromString(document.getString("_id")));

        data.setLastConverser(document.getString("lastConverser"));
        data.setIgnoredPlayers(Collections.singletonList(document.getString("ignoredPlayers")));
        data.setCoins(document.getInteger("coins"));
        data.getTime().set(document.getInteger("time"));
        data.setTpm(document.getBoolean("tpm"));

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

        this.dataMap.put(uuid, this.fromDocument(document));
    }

    public void save(UUID uuid){
        PlayerData data = this.get(uuid);

        if (data == null){
            return;
        }

        this.getDataCollection().replaceOne(Filters.eq("_id", uuid), this.toDocument(data), new UpdateOptions().upsert(true));
        this.dataMap.remove(uuid);
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
        TaskUtil.runTaskAsync(() -> Bukkit.getOnlinePlayers().forEach(player -> this.save(player.getUniqueId())));
    }

}
