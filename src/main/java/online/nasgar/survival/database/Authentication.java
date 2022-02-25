package online.nasgar.survival.database;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Authentication {

    private String uri;
    private String address;
    private String database;
    private String username;
    private String password;

    private int port;
    private boolean authentication;

    public Authentication(String address, int port, String database){
        this(address, port, database, false, null, null, null);
    }

    public Authentication(String address, int port, String database, boolean authentication, String username, String password, String uri){
        this.address = address;
        this.port = port;
        this.database = database;
        this.authentication = authentication;
        this.username = username;
        this.password = password;
        this.uri = uri;
    }


}
