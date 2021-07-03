package pl.coderslab.workshop2.db;

import java.sql.*;

public class DBUtil {

    private static final String DB_URL_PRODUCTS_EX = "jdbc:mysql://localhost:3306/workshop2?serverTimezone=UTC&useSSL=false&characterEncoding=utf8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private static final String DELETE_QUERY = "DELETE FROM tableName where id = ?";

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(DB_URL_PRODUCTS_EX, DB_USER, DB_PASSWORD);
    }

    public static void remove(Connection conn, String tableName, int id) {
        try (PreparedStatement statement =
                     conn.prepareStatement(DELETE_QUERY.replace("tableName", tableName));) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
