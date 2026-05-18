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

    @Override
    public List<OwnedCard> getUserCardCollection(int userId) {

        List<OwnedCard> ownedCards = new ArrayList<>();

        String sql = """
                    SELECT owned_card.owned_card_id, card_list.picture
                    FROM owned_card
                    JOIN card_list ON owned_card.card_id = card_list.card_id
                    WHERE owned_card.user_id = ?
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
        String sql = "SELECT * FROM owned_card WHERE user_id = ? AND name LIKE ? ORDER BY name ASC";

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
                INSERT INTO owned_card (user_id, card_id, card_condition, foil)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = databaseConfig.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, ownedCard.getUserId());
            stmt.setInt(2, ownedCard.getCardId());
            stmt.setString(3, ownedCard.getCondition().name());
            stmt.setString(4, ownedCard.getFoil());

            stmt.executeQuery();

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
                Card card = new Card();

                card.setCardId(rs.getInt("card_id"));
                card.setBlackMana(rs.getInt("black_mana"));
                card.setBlueMana(rs.getInt("blue_mana"));
                card.setGreenMana(rs.getInt("green_mana"));
                card.setRedMana(rs.getInt("red_mana"));
                card.setWhiteMana(rs.getInt("white_mana"));
                card.setNeutralMana(rs.getInt("neutral_mana"));

                card.setName(rs.getString("name"));

                card.setSuperType(SuperType.valueOf(rs.getString("super_type")));
                card.setType(Type.valueOf(rs.getString("card_type")));
                String multiType = rs.getString("multi_type");
                if (multiType != null) {
                    card.setMultiType(Type.valueOf(multiType));
                }
                card.setSubType(rs.getString("sub_type"));

                card.setCanBeCommander(rs.getBoolean("can_be_commander"));
                card.setPicture(rs.getString("picture"));
                card.setSetName(rs.getString("set_name"));
                card.setRuleText(rs.getString("rule_text"));
                card.setPower(rs.getInt("power"));
                card.setToughness(rs.getInt("toughness"));
                card.setRarity(Rarity.valueOf(rs.getString("rarity")));

                cards.add(card);
            }

        } catch (SQLException sqle) {
            throw new DataAccessException("Kunne ikke hente kort fra databasen", sqle);
        }

        return cards;
    }

}


