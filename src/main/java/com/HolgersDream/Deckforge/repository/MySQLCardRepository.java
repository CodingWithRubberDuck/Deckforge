package com.HolgersDream.Deckforge.repository;

import com.HolgersDream.Deckforge.config.DatabaseConfig;
import com.HolgersDream.Deckforge.domain.Deck;
import com.HolgersDream.Deckforge.domain.Format;
import com.HolgersDream.Deckforge.domain.interfaces.ICardRepository;
import com.HolgersDream.Deckforge.exceptions.DataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MySQLCardRepository implements ICardRepository {

    private final DatabaseConfig databaseConfig;

    public MySQLCardRepository(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public Optional<List<Deck>> findDecksById(int userId) {
        String sql = """
                SELECT d.deck_id, d.user_id, d.deck_name, d.format, count(dcc.card_id) as card_amount
                from deck d
                left JOIN deck_contain_card dcc
                 ON d.deck_id = dcc.deck_id
                WHERE user_id = ?
                group by d.deck_id, d.deck_name, d.format;
                """;


        try(Connection c = databaseConfig.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql)){

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            List<Deck> decks = new ArrayList<>();

            while(rs.next()) {
                Deck deck = new Deck(
                        rs.getInt("deck_id"),
                        rs.getInt("user_id"),
                        rs.getInt("card_amount"),
                        rs.getString("deck_name"),
                        Format.valueOf(rs.getString("format"))
                );
                decks.add(deck);
            }

            return Optional.of(decks);

        } catch (SQLException sqle){
            throw new DataAccessException("Der gik noget galt i forbindelse med at finde listen af deck", sqle);
        }
    }

    @Override
    public void addDeckToUser(Deck newDeck) {
        String sql = "INSERT INTO deck (user_id, deck_name, format) " +
                "VALUES (?, ?, ?)";

        try (Connection c = databaseConfig.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql)){

            stmt.setInt(1, newDeck.getUserId());
            stmt.setString(2, newDeck.getDeckName());
            stmt.setString(3, newDeck.getFormat().name());

            stmt.executeUpdate();

        } catch (SQLException sqle){
            throw new DataAccessException("Der gik noget galt i forbindelse med at oprette et nyt deck", sqle);
        }

    }
}
