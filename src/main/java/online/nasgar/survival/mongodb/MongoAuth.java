package online.nasgar.survival.mongodb;

import com.mongodb.MongoCredential;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MongoAuth {

    private String
            address,
            database,
            username,
            password;

    private int port;
    private boolean authentication;

    public MongoAuth(String address, int port, String database){
        this(address, port, database, false, null, null);
    }

    public MongoAuth(String address, int port, String database, boolean authentication, String username, String password){
        this.address = address;
        this.port = port;
        this.database = database;
        this.authentication = authentication;
        this.username = username;
        this.password = password;
    }

    public MongoCredential getCredential(){
        return MongoCredential.createCredential(this.username, this.database, this.password.toCharArray());
    }

}
