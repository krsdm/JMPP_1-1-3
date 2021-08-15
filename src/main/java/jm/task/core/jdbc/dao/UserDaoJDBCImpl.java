package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(45) NOT NULL, lastname VARCHAR(45) NOT NULL, age INT(3) NULL, PRIMARY KEY (`id`)) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users;";
    private static final String INSERT_USER = "INSERT INTO users(name, lastname, age) VALUES (?, ?, ?);";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?;";
    private static final String GET_ALL_USERS = "SELECT * FROM users;";
    private static final String DEL_ALL_USERS = "TRUNCATE TABLE users;";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_TABLE)) {

            statement.execute();

        } catch (SQLException throwables) {
            System.out.println("Не удалось создать таблицу");
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(DROP_TABLE)) {

            statement.execute();

        } catch (SQLException throwables) {
            System.out.println("Не удалось удалить таблицу");
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException throwables) {
            System.out.println("Не удалось добавить пользователя");
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException throwables) {
            System.out.println("Не удалось удалить пользователья");
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS)) {

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                users.add(new User(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("lastname"),
                        result.getByte("age")
                ));
            }

        } catch (SQLException throwables) {
            System.out.println("Не удалось получить всех пользователей");
            throwables.printStackTrace();
        }
        users.forEach(System.out::println);
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(DEL_ALL_USERS)) {

            statement.execute();

        } catch (SQLException throwables) {
            System.out.println("Не удалось очистить таблицу");
            throwables.printStackTrace();
        }
    }
}
