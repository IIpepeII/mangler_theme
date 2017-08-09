package db_con;

import com.google.gson.Gson;
import model.WordCard;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {

    private static ArrayList<String> dbProps = JDBCConnection.connectProps();
    String DATABASE;
    String DB_USER;
    String DB_PASSWORD;
    PreparedStatement preparedStatement;
    Connection dbConnection;

    public DatabaseController() {
        try {
            DATABASE = dbProps.get(0);
            DB_USER = dbProps.get(1);
            DB_PASSWORD = dbProps.get(2);
            dbConnection = getConnection();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    public void addNewWordCard(List<String> cardDetails) {

        String insertIntoTable = "INSERT INTO word_card (pic_location, theme, hun, eng) VALUES (?,?,?,?);";

        try {
            // Adding record to DB
            preparedStatement = dbConnection.prepareStatement(insertIntoTable);
            preparedStatement.setString(1, cardDetails.get(0));
            preparedStatement.setString(2, cardDetails.get(1));
            preparedStatement.setString(3, cardDetails.get(2));
            preparedStatement.setString(4, cardDetails.get(3));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<WordCard> getAll() throws JSONException {
        String query = "SELECT * FROM word_card";
        List<WordCard> wordCardList = cardFactory(query);
        return wordCardList;
    }

    public List<WordCard> getExamCards(){
        String query = "SELECT * FROM word_card\n" +
                "ORDER BY RAND()\n" +
                "LIMIT 10";
        List<WordCard> wordCardList = cardFactory(query);
        return wordCardList;
    }

    private List<WordCard> cardFactory(String query){

        List<WordCard> wordCardList = new ArrayList<>();
        try {
            preparedStatement = dbConnection.prepareStatement(query,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                WordCard wordCard = new WordCard(result.getInt("id"),
                        result.getString("pic_location"),
                        result.getString("theme"),
                        result.getString("hun"),
                        result.getString("eng"));
                wordCardList.add(wordCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wordCardList;
    }
}






