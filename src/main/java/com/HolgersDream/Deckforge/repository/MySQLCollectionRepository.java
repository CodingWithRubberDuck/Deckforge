package com.HolgersDream.Deckforge.repository;

import com.HolgersDream.Deckforge.config.DatabaseConfig;
import com.HolgersDream.Deckforge.domain.OwnedCard;
import com.HolgersDream.Deckforge.domain.Rarity;
import com.HolgersDream.Deckforge.domain.Type;
import com.HolgersDream.Deckforge.domain.interfaces.ICollectionRepository;
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
public class MySQLCollectionRepository implements ICollectionRepository {


    private final DatabaseConfig databaseConfig;
    private final EnumCardMapper enumCardMapper;

    public MySQLCollectionRepository(DatabaseConfig databaseConfig, EnumCardMapper enumCardMapper) {
        this.databaseConfig = databaseConfig;
        this.enumCardMapper = enumCardMapper;
    }

    @Override
    public List<OwnedCard> getUserCardCollection(int userId) {

        List<OwnedCard> ownedCards = new ArrayList<>();

        String sql = """
                    SELECT owned_card.owned_card_id, user_id, card_condition, foil, card_list.*
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
                ownedCards.add(mapOwnedCard(rs));
            }

        } catch (SQLException sqle) {
            throw new DataAccessException("Der gik noget galt i forbindelse med databasen", sqle);
        }

        return ownedCards;
    }

    @Override
    public List<OwnedCard> findOwnedCardByName(int userId, String name) {
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
    public void addCardByIdToCollection(int userId, int cardId) {
        String sql = """
                INSERT INTO owned_card (user_id, card_id)
                VALUES (?, ?)
                """;

        try (Connection con = databaseConfig.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, cardId);
            stmt.executeUpdate();

        } catch (SQLException sqle) {
            throw new DataAccessException("Kunne ikke tilføje kort til samling", sqle);
        }
    }


    @Override
    public Optional<OwnedCard> getOwnedCardById(int ownedCardId) {

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
                return Optional.of(mapOwnedCard(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DataAccessException("Kunne ikke hente owned card", e);
        }
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
                enumCardMapper.checkSuperType(rs.getString("super_type")),
                Type.valueOf(rs.getString("card_type")),
                enumCardMapper.checkMultiType(rs.getString("multi_type")),
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
                enumCardMapper.checkCondition(rs.getString("card_condition")),
                rs.getString("foil")
        );
    }

}
