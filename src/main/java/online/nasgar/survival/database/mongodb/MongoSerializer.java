package online.nasgar.survival.database.mongodb;

import org.bson.Document;

public interface MongoSerializer<T> {

    Document toDocument(T value);

    T fromDocument(Document document);

}
