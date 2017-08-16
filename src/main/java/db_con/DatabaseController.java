package db_con;

import model.Result;
import model.WordCard;
import org.json.JSONException;

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
    public void addNewResult(String firstName, String lastName, String result, String  startTime, String endTime) {

        String insertIntoTable = "INSERT INTO result (first_name, last_name, result, start_time, end_time) VALUES (?,?,?,?,?);";

        try {
            // Adding record to DB
            preparedStatement = dbConnection.prepareStatement(insertIntoTable);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, result);
            preparedStatement.setString(4, startTime);
            preparedStatement.setString(5, endTime);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<WordCard> getAllWordCards() throws JSONException {

        String query = "SELECT * FROM word_card";
        return queryToPreparedStatement(query);
    }

    public List<Result> getAllResults() throws JSONException {

        String query = "SELECT * FROM result";
        return queryToPreparedStatementForResults(query);
    }

    public void delResult(Integer id){
        String query = "DELETE FROM result WHERE id= ?;";
        deleteRowById(id,query);
    }

    public void delWordCard(Integer id){
        String query = "DELETE FROM word_card WHERE id= ?;";
        deleteRowById(id,query);
    }

    private void deleteRowById(Integer id, String query){
        try {
            preparedStatement = dbConnection.prepareStatement(query,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Result> queryToPreparedStatementForResults(String query) {
        try {
            preparedStatement = dbConnection.prepareStatement(query,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            ResultSet result = preparedStatement.executeQuery();
            return resultFactory(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<WordCard> queryToPreparedStatement(String query) {
        try {
            preparedStatement = dbConnection.prepareStatement(query,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            ResultSet result = preparedStatement.executeQuery();
            return cardFactory(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<WordCard> getExamCards() {
        String query = "SELECT * FROM word_card\n" +
                "ORDER BY RAND()\n" +
                "LIMIT 10";
        return queryToPreparedStatement(query);
    }

    public List<WordCard> getCardsByTheme(String theme) {

        String query = "SELECT * FROM word_card WHERE theme = ? ORDER BY RAND() LIMIT 10";
        try {
            preparedStatement = dbConnection.prepareStatement(query,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            preparedStatement.setString(1, theme);
            ResultSet result = preparedStatement.executeQuery();
            return cardFactory(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<WordCard> cardFactory(ResultSet result) {
        List<WordCard> wordCardList = new ArrayList<>();
        try {
            while (result.next()) {
                WordCard wordCard = new WordCard(result.getInt("id"),
                        result.getString("pic_location"),
                        result.getString("theme"),
                        result.getString("eng"),
                        result.getString("hun"));
                wordCardList.add(wordCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wordCardList;
    }

    private List<Result> resultFactory(ResultSet result) {
        List<Result> resultList = new ArrayList<>();
        try {
            while (result.next()) {
                Result newResult = new Result(result.getInt("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getInt("result"),
                        result.getString("start_time"),
                        result.getString("end_time"));
                resultList.add(newResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

}






