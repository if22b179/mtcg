package org.if22b179.apps.mtcg.repository;

import org.if22b179.apps.mtcg.data.Database;
import org.if22b179.apps.mtcg.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepo implements CrudRepo<User,String> {

    private final Database database = new Database();

    private final String SAVE_SQL = "INSERT INTO UserTable(username, password) VALUES(?, ?)";

    private final String DELETE_SQL = "DELETE FROM UserTable WHERE username = ?";

    private final String FIND_BY_ID_SQL = "SELECT * FROM UserTable WHERE username = ?";


    @Override
    public User save(User user) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SAVE_SQL)
        ) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());

            pstmt.execute();
            return user;
        } catch (SQLException e) {
            // THOUGHT: how do i handle exceptions (hint: look at the TaskApp)
            throw new RuntimeException("Fehler beim Speichern in die DB des Benutzers: " + user.getUsername(), e);
        }
    }

    @Override
    public Optional<User> findById(String username) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_BY_ID_SQL)
        ) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    // Add more fields here if your User entity has more fields
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Suchen in der DB des Benutzers: " + username, e);
        }

        return Optional.empty();
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void deleteById(String username) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(DELETE_SQL)
        ) {
            pstmt.setString(1, username);
            pstmt.execute();
        } catch (SQLException e) {
            // Handling the exception as suggested in the comment
            throw new RuntimeException("Fehler beim Löschen in der DB des Benutzers: " + username, e);
        }
    }
}
