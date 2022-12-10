package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    private final Connection connection = Util.getConnection();

    public void createUsersTable() {
        final String SQL = "CREATE TABLE users " +
                "(id INT NOT NULL AUTO_INCREMENT," +
                " name varchar(50), " +
                "lastName varchar(50)," +
                "age INT(3), PRIMARY KEY (id))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
        }
    }

    public void dropUsersTable() {
        final String SQL = "DROP  TABLE users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String SQL = "INSERT INTO users (name, lastName, age) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        final String SQL = "DELETE FROM users WHERE id = ? ";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> resList = new ArrayList<>();
        final String SQL = "SELECT * FROM users";

        try (Statement statement = connection.createStatement()) {
//            User user = new User();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge((byte) resultSet.getInt("age"));
                resList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resList;
    }


    public void cleanUsersTable() {
        final String SQL = "TRUNCATE users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
