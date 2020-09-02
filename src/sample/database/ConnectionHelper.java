package sample.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

    private static String url = "jdbc:mysql://127.0.0.1:3306/czas_pracy?serverTimezone = ECT";
    private static String user = "root";
    private static String passwd = "admin";
    private static String dbDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn;

    public static Connection getConnection(){
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            System.out.println("Problem ze sterownikiem"+e.getMessage());
        }
        try {
            conn = DriverManager.getConnection(url,user,passwd);
        } catch (SQLException e) {
            System.out.println("Nie udało się nawiązać połącznia"+e.getMessage());
        }
        return conn;
    }
}
