package client;

public class ServerAlias {

    private String alias;
    private String server;

    /**
     * Constructor
     */
    ServerAlias(String alias, String server) {
        this.alias = alias;
        this.server = server;
    }

    /**
     * Getters
     */
    public String getAlias() {
        return this.alias;
    }
    public String getServer() {
        return this.server;
    }

}
