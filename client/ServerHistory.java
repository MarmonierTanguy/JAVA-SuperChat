package client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerHistory {

    private static final String COMMA_DELIMITER = ";";
    private List<ServerAlias> serverAliases = new ArrayList<ServerAlias>();

    /**
     * Constructor
     */
    ServerHistory(String fileName) {

        BufferedReader fileReader = null;

        try {

            String line = "";

            // Create the file reader.
            fileReader = new BufferedReader(new FileReader(fileName));

            // Read the CSV file header to skip it.
            fileReader.readLine();

            // Read the file line by line starting from the second line.
            while ((line = fileReader.readLine()) != null) {
                // Get all tokens available in line.
                String[] tokens = line.split(COMMA_DELIMITER);
                if (tokens.length > 0) {
                    // Create a new student object and fill his  data.
                    String alias = tokens[0];
                    String server = tokens[1];
                    System.out.println("Added new alias : " + alias + " -> " + server);
                    ServerAlias serverAlias = new ServerAlias(alias, server);
                    this.serverAliases.add(serverAlias);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Erreur lors de la lecture du CSV.");
            e.printStackTrace();
        }
        finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Erreur lors de la fermeture du fichier.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Public method used to find a server address from an alias.
     */
    public String findServerFromAlias(String alias) {
        String server = alias;
        for(int i=0; i<this.serverAliases.size(); i++) {
            if (serverAliases.get(i).getAlias().equals(alias)) {
                server = serverAliases.get(i).getServer();
            }
        }
        return server;
    }

}
