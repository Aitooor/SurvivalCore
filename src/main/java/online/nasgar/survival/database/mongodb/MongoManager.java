package online.nasgar.survival.database.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import online.nasgar.survival.database.Authentication;
import org.bson.Document;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

@Getter
public class MongoManager implements Closeable {

    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;

    public MongoManager(Authentication auth){
        MongoClientSettings.Builder builder = MongoClientSettings.builder();

        builder.applyConnectionString(new ConnectionString("mongodb://" + auth.getAddress() + ":" + auth.getPort()));
        builder.applyToConnectionPoolSettings(block -> block.maxConnectionIdleTime(15, TimeUnit.SECONDS));
        builder.retryWrites(true);

        if (auth.isAuthentication()) {
            builder.credential(MongoCredential.createCredential(
                    auth.getUsername(),
                    auth.getDatabase(),
                    auth.getPassword().toCharArray()));
        }

        this.mongoClient = MongoClients.create(builder.build());
        this.mongoDatabase = this.mongoClient.getDatabase(auth.getDatabase());
    }

    @Override public void close(){
        if (this.mongoClient != null){
            this.mongoClient.close();
        }
    }

    public MongoCollection<Document> getCollection(String name) {
        return this.mongoDatabase.getCollection(name);
    }

}
