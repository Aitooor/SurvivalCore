package online.nasgar.vanilla.services.database;

import com.mongodb.ConnectionString;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Authentication {

    private final String STRING_CONNECTION = "mongodb://%s:%s@%s:%s/?authSource=%s";

    private String uri;
    private String address;
    private String database;
    private String username;
    private String password;

    private int port;

    public Authentication(String address, int port, String database){
        this(address, port, database,  null, null, null);
    }

    public Authentication(String address, int port, String database, String username, String password, String uri){
        this.address = address;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.uri = uri;
    }

    public ConnectionString toConnectionString() {
        return new ConnectionString(
                (getUri() != null) ? getUri() :
                        String.format(
                                STRING_CONNECTION,
                                getUsername(),
                                getPassword(),
                                getAddress(),
                                getPort(),
                                getDatabase()
                        )
        );

    }

}
