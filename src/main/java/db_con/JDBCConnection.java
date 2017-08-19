package db_con;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * This class responsible for  database connection.
 */
public class JDBCConnection {


    /**
     * This method read the properties(database name, username, password)
     * from the properties.txt file for the correct connection to the database.
     * @return ArrayList of String objects with the prperties
     */
    public static ArrayList<String> connectProps() {
        Properties prop = new Properties();
        InputStream input = null;
        ArrayList<String> proplist = new ArrayList<>();
        String pathToDB = "src/main/resources/sql/properties.txt";
        try {
            input = new FileInputStream(pathToDB);

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            proplist.add(prop.getProperty("database"));
            proplist.add(prop.getProperty("username"));
            proplist.add(prop.getProperty("password"));
            return proplist;

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
