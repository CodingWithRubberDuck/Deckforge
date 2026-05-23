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
    private final EnumCardMapper enumCardMapper;

    public MySQLCardRepository(DatabaseConfig databaseConfig, EnumCardMapper enumCardMapper) {
        this.databaseConfig = databaseConfig;
        this.enumCardMapper = enumCardMapper;
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

    @Override
    public Optional<Card> getCardById(int cardId) {
        String sql = "SELECT * FROM card_list WHERE card_id = ?";

        try (Connection con = databaseConfig.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, cardId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapCard(rs));
            }
            return Optional.empty();

        } catch (SQLException sqle) {
            throw new DataAccessException("Kunne ikke hente kort detaljer", sqle);
        }
    }


    @Override
    public List<Card> findCardsByName(String name) {
        List<Card> cards = new ArrayList<>();

        String sql = "SELECT * FROM card_list WHERE name LIKE ?";

        try (Connection con = databaseConfig.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cards.add(mapCard(rs));
            }

            return cards;

        } catch (SQLException sqle) {
            throw new DataAccessException("Kunne ikke søge efter kort", sqle);
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
                Rarity.valueOf(rs.getString("rarity"))
        );
    }



}





