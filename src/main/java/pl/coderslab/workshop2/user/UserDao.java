package pl.coderslab.workshop2.user;

import pl.coderslab.workshop2.db.DBUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static final String INSERT_QUERY = "INSERT INTO users(username, password, email) VALUES (?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
    private static final String UPDATE_QUERY = "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?";

    public User create(User user) {
        try (Connection conn = DBUtil.connection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getLong(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User read(int userId) {
        try (Connection conn = DBUtil.connection()) {
            PreparedStatement prepStmt = conn.prepareStatement(SELECT_QUERY);
            prepStmt.setInt(1, userId);
            ResultSet rs = prepStmt.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                return new User(id, username, password, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User[] findAll() {
        User[] allUsers = new User[0];
        try (Connection conn = DBUtil.connection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL_QUERY);
            while (rs.next()) {
                long id = rs.getLong("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                User user = new User(id, username, password, email);
                allUsers = Arrays.copyOf(allUsers, allUsers.length + 1);
                allUsers[allUsers.length - 1] = user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public void delete(int userId) {
        try(Connection conn = DBUtil.connection()) {
            DBUtil.remove(conn, "users", userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        try (Connection conn = DBUtil.connection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
