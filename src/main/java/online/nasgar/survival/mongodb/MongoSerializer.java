package online.nasgar.survival.mongodb;

import org.bson.Document;

public interface MongoSerializer<T> {

    Document toDocument(T value);

    T fromDocument(Document document);

}
