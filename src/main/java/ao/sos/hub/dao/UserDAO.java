package ao.sos.hub.dao;

import ao.sos.hub.model.UserDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/sos?useSSL=false&serverTimezone=Africa/Luanda&allowPublicKeyRetrieval=true";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    private static final String INSERT_USERS_SQL = "INSERT INTO utilizadores (nome, numero_estudante, email, papel, activo) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "SELECT id, nome, numero_estudante, email, papel, activo FROM utilizadores WHERE id = ?;";
    private static final String SELECT_ALL_USERS = "SELECT id, nome, numero_estudante, email, papel, activo FROM utilizadores;";
    private static final String DELETE_USERS_SQL = "DELETE FROM utilizadores WHERE id = ?;";
    private static final String UPDATE_USERS_SQL = "UPDATE utilizadores SET nome = ?, numero_estudante = ?, email = ?, papel = ?, activo = ? WHERE id = ?;";

    public UserDAO() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertUser(UserDTO user) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getNumeroEstudante());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPapel() != null ? user.getPapel() : "AUTOR");
            preparedStatement.setBoolean(5, user.isActivo());
            preparedStatement.executeUpdate();
        }
    }

    public UserDTO selectUser(long id) {
        UserDTO user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new UserDTO(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("numero_estudante"),
                        rs.getString("email"),
                        rs.getString("papel"),
                        rs.getBoolean("activo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<UserDTO> selectAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                users.add(new UserDTO(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("numero_estudante"),
                        rs.getString("email"),
                        rs.getString("papel"),
                        rs.getBoolean("activo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean deleteUser(long id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL)) {
            statement.setLong(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateUser(UserDTO user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL)) {
            statement.setString(1, user.getNome());
            statement.setString(2, user.getNumeroEstudante());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPapel());
            statement.setBoolean(5, user.isActivo());
            statement.setLong(6, user.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}
