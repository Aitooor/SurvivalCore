package online.nasgar.survivalcore.database;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import online.nasgar.survivalcore.Core;
import org.bson.Document;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;

@Getter
public class MongoHandler {

    private final Core core;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> profiles;

    public MongoHandler(Core core) {
        this.core = core;
    }

    private void connect() {
        FileConfiguration config = core.getInstance().getConfig();
        MongoClient client;

        if(config.getBoolean("MONGODB.AUTHENTICATION.ENABLED")) {
            MongoCredential credential = MongoCredential.createCredential(
                    config.getString("MONGODB.AUTHENTICATION.USER"),
                    config.getString("MONGODB.DATABASE"),
                    config.getString("MONGODB.AUTHENTICATION.PASSWORD").toCharArray());

            client = new MongoClient(new ServerAddress(config.getString("MONGODB.HOST"),
                    config.getInt("MONGODB.PORT")), Collections.singletonList(credential));
        } else {
            client = new MongoClient(config.getString("MONGODB.HOST"),
                    config.getInt("MONGODB.PORT"));
        }
        this.mongoDatabase = client.getDatabase(config.getString("MONGODB.DATABASE"));
        profiles = mongoDatabase.getCollection("profiles");
    }

}
