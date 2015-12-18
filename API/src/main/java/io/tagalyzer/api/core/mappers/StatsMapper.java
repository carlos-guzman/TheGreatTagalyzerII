package io.tagalyzer.api.core.mappers;

import io.tagalyzer.api.core.models.Stats;
import io.tagalyzer.api.core.models.Tag;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsMapper implements ResultSetMapper<Stats> {

    @Override
    public Stats map(int i, ResultSet rs, StatementContext ctx) throws SQLException {
        return new Stats(new Tag(rs.getInt("id"), rs.getString("tag")), rs.getInt("count"), rs.getDate("last_used"));
    }
}
