package online.nasgar.survival.services.database;

import com.mongodb.ConnectionString;

public class Authentication {

    private final String STRING_CONNECTION = "mongodb://%s:%s@%s:%s/%s?authSource=admin";

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

    public String getSTRING_CONNECTION() {
        return STRING_CONNECTION;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
