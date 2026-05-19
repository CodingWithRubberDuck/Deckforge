package com.HolgersDream.Deckforge.repository;

import com.HolgersDream.Deckforge.config.DatabaseConfig;
import com.HolgersDream.Deckforge.domain.interfaces.IUserRepository;
import com.HolgersDream.Deckforge.exceptions.DataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

@Repository
public class MySQLUserRepository implements IUserRepository {

    private final DatabaseConfig databaseConfig;

    public MySQLUserRepository(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public void markToDeleteById(int userId) {
        String sql = "UPDATE user " +
                "SET  date_asked_for_delete = ? " +
                "WHERE user_id = ?";
        try (Connection con = databaseConfig.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setInt(2, userId);

            stmt.executeUpdate();

        } catch (SQLException sqle){
            throw new DataAccessException("Der gik noget galt i forbindelse med databasen", sqle);
        }
    }


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
