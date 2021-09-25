package online.nasgar.survival.database;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Authentication {

    private String
            address,
            database,
            username,
            password;

    private int port;
    private boolean authentication;

    public Authentication(String address, int port, String database){
        this(address, port, database, false, null, null);
    }

    public Authentication(String address, int port, String database, boolean authentication, String username, String password){
        this.address = address;
        this.port = port;
        this.database = database;
        this.authentication = authentication;
        this.username = username;
        this.password = password;
    }


}
