package online.nasgar.vanilla.services.database.mongodb;

import com.mongodb.MongoDriverInformation;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import online.nasgar.vanilla.services.database.Authentication;
import org.bson.Document;

import java.io.Closeable;

@Getter
public class MongoManager implements Closeable {

    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;

    public MongoManager(Authentication auth){
        MongoDriverInformation mongoDriverInformation = MongoDriverInformation.builder().driverName("sync").build();

        this.mongoClient = MongoClients.create(auth.toConnectionString(), mongoDriverInformation);
        this.mongoDatabase = this.mongoClient.getDatabase(auth.getDatabase());
    }

    @Override
    public void close(){
        if (this.mongoClient != null){
            this.mongoClient.close();
        }
    }

    public MongoCollection<Document> getCollection(String name) {
        return this.mongoDatabase.getCollection(name);
    }

}
