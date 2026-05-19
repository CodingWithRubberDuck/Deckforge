package com.HolgersDream.Deckforge.repository;

import com.HolgersDream.Deckforge.config.DatabaseConfig;
import com.HolgersDream.Deckforge.domain.Event;
import com.HolgersDream.Deckforge.domain.interfaces.IEventRepository;
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
public class MySQLEventRepository implements IEventRepository {

    private final DatabaseConfig databaseConfig;

    public MySQLEventRepository(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }


    @Override
    public Optional<List<Event>> findComingEvents() {
        String sql = """
                SELECT e.event_id, e.owner_id, e.event_name, e.max_slots, e.location, e.start_time, e.date, max_slots - count(upe.user_id) as available_slots
                FROM event e
                LEFT JOIN user_participate_event upe
                ON e.event_id = upe.event_id
                Where e.date >= current_date()
                GROUP BY e.event_id, e.owner_id, e.event_name, e.max_slots, e.location, e.start_time, e.date;
                """;

        try (Connection con = databaseConfig.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            List<Event> events = new ArrayList<>();

            while(rs.next()){
                events.add(mapRow(rs));
            }

            return Optional.of(events);

        } catch (SQLException sqle){
            throw new DataAccessException("Der gik noget galt i forbindelse med databasen", sqle);
        }
    }

    private Event mapRow(ResultSet rs) throws SQLException{
        return new Event(
                rs.getInt("event_id"),
                rs.getInt("owner_id"),
                rs.getString("event_name"),
                rs.getInt("max_slots"),
                rs.getInt("available_slots"),
                rs.getString("location"),
                LocalTime.parse(rs.getString("start_time")),
                LocalDate.parse(rs.getString("date"))
                );
    }
}

