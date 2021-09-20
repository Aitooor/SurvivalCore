package online.nasgar.survival.playerdata;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import online.nasgar.survival.mongodb.MongoManager;
import online.nasgar.survival.mongodb.MongoSerializer;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager implements MongoSerializer<PlayerData> {

    private final MongoManager mongoManager;
    private final Map<UUID, PlayerData> dataMap;

    public PlayerDataManager(MongoManager mongoManager){
        this.mongoManager = mongoManager;
        this.dataMap = new HashMap<>();
    }

    @Override public Document toDocument(PlayerData data) {
        Document document = new Document();

        document.put("_id", data.getUuid());
        document.put("lastConverser", data.getLastConverser());
        document.put("coins", data.getCoins());
        document.put("ignoredPlayers", data.getIgnoredPlayers());
        document.put("tpm", data.isTpm());

        return document;
    }

    @Override public PlayerData fromDocument(Document document) {
        PlayerData data = new PlayerData();

        data.setUuid((UUID) document.get("_id"));
        data.setLastConverser(document.getString("lastConverser"));
        data.setCoins(document.getInteger("coins"));
        data.setIgnoredPlayers(document.getList("ignoredPlayers", String.class));
        data.setTpm(document.getBoolean("tpm"));

        return data;
    }

    public void create(UUID uuid){
        if (!this.contains(uuid)){
            this.dataMap.put(uuid, new PlayerData(uuid));
        }
    }

    public void load(UUID uuid){
        if (this.contains(uuid)){
            return;
        }

        Document document = this.getDataCollection().find(Filters.eq("_id", uuid)).first();

        if (document == null) {
            return;
        }

        this.dataMap.put(uuid, new PlayerData(uuid));
    }

    public void save(UUID uuid){
        PlayerData data = this.get(uuid);

        if (data == null){
            return;
        }

        this.getDataCollection().replaceOne(Filters.eq("_id", uuid), this.toDocument(data), new ReplaceOptions().upsert(true));
        this.dataMap.remove(uuid);
    }

    public PlayerData get(UUID uuid){
        if (!this.contains(uuid)){
            return null;
        }

        Document document = this.getDataCollection().find(Filters.eq("_id", uuid)).first();

        if (document == null) {
            return null;
        }

        return this.fromDocument(document);
    }

    public boolean contains(UUID uuid){
        return this.dataMap.containsKey(uuid);
    }

    private MongoCollection<Document> getDataCollection(){
        return this.mongoManager.getCollection("playerData");
    }

}
