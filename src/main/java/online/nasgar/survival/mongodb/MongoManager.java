package online.nasgar.survival.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;

import java.util.concurrent.TimeUnit;

@Getter
public class MongoManager {

    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;

    public MongoManager(MongoAuth auth){
        this(auth, "mongodb://" + auth.getAddress() + ":" + auth.getPort());
    }

    public MongoManager(MongoAuth auth, String customUrl){
        MongoClientSettings.Builder builder = MongoClientSettings.builder();

        builder.applyConnectionString(new ConnectionString(customUrl));
        builder.applyToConnectionPoolSettings(block -> block.maxConnectionIdleTime(15, TimeUnit.SECONDS));
        builder.retryWrites(true);

        if (auth.isAuthentication()) {
            builder.credential(auth.getCredential());
        }

        this.mongoClient = MongoClients.create(builder.build());
        this.mongoDatabase = this.mongoClient.getDatabase(auth.getDatabase());
    }

    public void close(){
        if (this.mongoClient != null){
            this.mongoClient.close();
        }
    }

    public MongoCollection<Document> getCollection(String name) {
        return this.mongoDatabase.getCollection(name);
    }

}
