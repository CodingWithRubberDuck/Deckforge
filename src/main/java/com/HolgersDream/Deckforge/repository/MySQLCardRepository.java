package com.HolgersDream.Deckforge.repository;

import com.HolgersDream.Deckforge.config.DatabaseConfig;
import com.HolgersDream.Deckforge.domain.*;
import com.HolgersDream.Deckforge.domain.interfaces.ICardRepository;
import com.HolgersDream.Deckforge.exceptions.DataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
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


        try (Connection c = databaseConfig.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            List<Deck> decks = new ArrayList<>();

            while (rs.next()) {
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

        } catch (SQLException sqle) {
            throw new DataAccessException("Der gik noget galt i forbindelse med at finde listen af deck", sqle);
        }
    }

    @Override
    public void addDeckToUser(Deck newDeck) {
        String sql = "INSERT INTO deck (user_id, deck_name, format) " +
                "VALUES (?, ?, ?)";

        try (Connection c = databaseConfig.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setInt(1, newDeck.getUserId());
            stmt.setString(2, newDeck.getDeckName());
            stmt.setString(3, newDeck.getFormat().name());

            stmt.executeUpdate();

        } catch (SQLException sqle) {
            throw new DataAccessException("Der gik noget galt i forbindelse med at oprette et nyt deck", sqle);
        }

    }

    @Override
    public List<OwnedCard> getUserCardCollection(int userId) {

        List<OwnedCard> ownedCards = new ArrayList<>();

        String sql = """
                    SELECT owned_card.owned_card_id, card_list.*
                    FROM owned_card
                    JOIN card_list ON owned_card.card_id = card_list.card_id
                    WHERE owned_card.user_id = ?
                    ORDER BY card_list.name ASC
                """;

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int ownedCardId = rs.getInt("owned_card_id");
                String picture = rs.getString("picture");

                OwnedCard card = new OwnedCard(ownedCardId, picture);

                ownedCards.add(card);
            }

        } catch (SQLException sqle) {
            throw new DataAccessException("Der gik noget galt i forbindelse med databasen", sqle);
        }

        return ownedCards;
    }

    @Override
    public List<OwnedCard> findCardByName(int userId, String name) {
        String sql = """
                SELECT owned_card.owned_card_id, card_list.name, card_list.picture
                FROM owned_card
                JOIN card_list ON owned_card.card_id = card_list.card_id
                WHERE owned_card.user_id = ?
                  AND card_list.name LIKE ?
                ORDER BY card_list.name ASC
                """;


        try (Connection con = databaseConfig.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            List<OwnedCard> ownedCards = new ArrayList<>();

            while (rs.next()) {
                OwnedCard ownedCard = new OwnedCard();
                ownedCard.setOwnedCardId(rs.getInt("owned_card_id"));
                ownedCard.setName(rs.getString("name"));
                ownedCard.setPicture(rs.getString("picture"));
                ownedCards.add(ownedCard);
            }

            return ownedCards;

        } catch (SQLException sqle) {
            throw new DataAccessException("Der gik noget galt i forbindelse med databasen", sqle);
        }
    }

    @Override
    public void addCardToCollection(OwnedCard ownedCard) {
        String sql = """
                INSERT INTO owned_card (user_id, card_id)
                VALUES (?, ?)
                """;

        try (Connection con = databaseConfig.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, ownedCard.getUserId());
            stmt.setInt(2, ownedCard.getCardId());
            stmt.executeUpdate();

        } catch (SQLException sqle) {
            throw new DataAccessException("Kunne ikke tilføje kort til samling", sqle);
        }
    }

    @Override
    public List<Card> getAllCards() {
        List<Card> cards = new ArrayList<>();

        String sql = "SELECT * FROM card_list ORDER BY name ASC";

        try (Connection con = databaseConfig.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                cards.add(mapCard(rs));
            }

            return cards;

        } catch (SQLException sqle) {
            throw new DataAccessException("Kunne ikke hente kort fra databasen", sqle);
        }
    }

    public Card getCardById(int cardId) {
        String sql = "SELECT * FROM card_list WHERE card_id = ?";

        try (Connection con = databaseConfig.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, cardId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapCard(rs);
            }

            return null;

        } catch (SQLException sqle) {
            throw new DataAccessException("Kunne ikke hente kort detaljer", sqle);
        }
    }

    @Override
    public OwnedCard getOwnedCardById(int ownedCardId) {

        String sql = """
                    SELECT owned_card.*, card_list.*
                    FROM owned_card
                    JOIN card_list ON owned_card.card_id = card_list.card_id
                    WHERE owned_card.owned_card_id = ?
                """;

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ownedCardId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapOwnedCard(rs);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Kunne ikke hente owned card", e);
        }

        return null;
    }

    @Override
    public void removeOwnedCard(int ownedCardId, int userId) {
        String sql = """
                DELETE FROM owned_card
                WHERE owned_card_id = ? AND user_id = ?
                """;

        try (Connection con = databaseConfig.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, ownedCardId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

        } catch (SQLException sqle) {
            throw new DataAccessException("Kunne ikke fjerne kort fra samling", sqle);
        }
    }


    private Card mapCard(ResultSet rs) throws SQLException {
        return new Card(
                rs.getInt("card_id"),
                rs.getInt("black_mana"),
                rs.getInt("blue_mana"),
                rs.getInt("green_mana"),
                rs.getInt("red_mana"),
                rs.getInt("white_mana"),
                rs.getInt("neutral_mana"),
                rs.getString("name"),
                checkSuperType(rs.getString("super_type")),
                Type.valueOf(rs.getString("card_type")),
                checkMultiType(rs.getString("multi_type")),
                rs.getString("sub_type"),
                rs.getBoolean("can_be_commander"),
                rs.getString("picture"),
                rs.getString("set_name"),
                rs.getString("rule_text"),
                rs.getInt("power"),
                rs.getInt("toughness"),
                Rarity.valueOf(rs.getString("rarity"))
        );
    }

    private OwnedCard mapOwnedCard(ResultSet rs) throws SQLException {
        return new OwnedCard(
                rs.getInt("card_id"),
                rs.getInt("black_mana"),
                rs.getInt("blue_mana"),
                rs.getInt("green_mana"),
                rs.getInt("red_mana"),
                rs.getInt("white_mana"),
                rs.getInt("neutral_mana"),
                rs.getString("name"),
                checkSuperType(rs.getString("super_type")),
                Type.valueOf(rs.getString("card_type")),
                checkMultiType(rs.getString("multi_type")),
                rs.getString("sub_type"),
                rs.getBoolean("can_be_commander"),
                rs.getString("picture"),
                rs.getString("set_name"),
                rs.getString("rule_text"),
                rs.getInt("power"),
                rs.getInt("toughness"),
                Rarity.valueOf(rs.getString("rarity")),

                rs.getInt("owned_card_id"),
                rs.getInt("user_id"),
                checkCondition(rs.getString("card_condition")),
                rs.getString("foil")
        );
    }

    private SuperType checkSuperType(String superType) {
        if (superType == null) {
            return null;
        }
        return SuperType.valueOf(superType);
    }

    private Type checkMultiType(String multiType) {
        if (multiType == null) {
            return null;
        }
        return Type.valueOf(multiType);
    }

    private Condition checkCondition(String condition) {
        if (condition == null) {
            return null;
        }
        return Condition.valueOf(condition);
    }
}





