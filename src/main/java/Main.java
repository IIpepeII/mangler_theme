import db_con.JDBCConnection;

import static spark.Spark.get;

public class Main {
    public static void main(String[] args) {

        JDBCConnection.connectProps();

        get("/hello", (req, res) -> "Hello World");


    }
}