package io.tagalyzer.api.core.mappers;

import io.tagalyzer.api.core.models.Tag;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements ResultSetMapper<Tag> {

    @Override
    public Tag map(int i, ResultSet rs, StatementContext ctx) throws SQLException {
        return new Tag(rs.getLong("id"), rs.getString("tag"));
    }
}
