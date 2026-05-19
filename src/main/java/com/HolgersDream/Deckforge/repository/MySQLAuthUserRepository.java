package com.HolgersDream.Deckforge.repository;

import com.HolgersDream.Deckforge.config.DatabaseConfig;
import com.HolgersDream.Deckforge.domain.Role;
import com.HolgersDream.Deckforge.domain.User;
import com.HolgersDream.Deckforge.domain.interfaces.IAuthUserRepository;
import com.HolgersDream.Deckforge.exceptions.DataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public class MySQLAuthUserRepository implements IAuthUserRepository {

    private final DatabaseConfig databaseConfig;

    public MySQLAuthUserRepository(DatabaseConfig databaseConfig){
        this.databaseConfig = databaseConfig;
    }

    @Override
    public Optional<User> findByEmail(String email){
        String sql = "SELECT user_id, name, email, password_hash, role FROM user WHERE email = ?";

        try (Connection con = databaseConfig.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        Role.valueOf(rs.getString("role"))
                );
                return Optional.of(user);
            } else {
                return Optional.empty();
            }


        } catch (SQLException sqle) {
            throw new DataAccessException("Der gik noget galt i forbindelse med databasen", sqle);
        }
    }

    @Override
    public void saveNewUser(User user){
        String sql = "INSERT INTO user (name, email, password_hash, role) VALUES (?, ?, ?, ?)";

        try (Connection con = databaseConfig.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql))  {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole().name());
            stmt.executeUpdate();

        } catch (SQLException sqle){
            throw new DataAccessException("Der gik noget galt i forbindelse med databasen", sqle);
        }
    }

    @Override
    public void updateLastLogin(int userId) {
        String sql =
                "UPDATE user " +
                "SET last_logged_in = ? , date_asked_for_delete = NULL " +
                "WHERE user_id = ?";

        try (Connection con = databaseConfig.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setInt(2, userId);

            stmt.executeUpdate();

        } catch (SQLException sqle) {
            throw new DataAccessException("Der gik noget galt i forbindelse med databasen", sqle);
        }
    }
}
