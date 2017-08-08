package db_con;

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

        String insertIntoTable = "INSERT INTO word_card (pic_location, theme, hung,engl) VALUES (?,?,?,?);";

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

    public void remove(int id, String table) {
        String removeFromTable = "";
        switch (table) {
            case "Product":
                removeFromTable = "DELETE FROM product WHERE id = ?;";
                break;

            case "ProductCategory":
                removeFromTable = "DELETE FROM productcategory WHERE id = ?;";
                break;

            case "Supplier":
                removeFromTable = "DELETE FROM supplier WHERE id = ?;";
                break;
        }
        try {
            preparedStatement = dbConnection.prepareStatement(removeFromTable);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}






