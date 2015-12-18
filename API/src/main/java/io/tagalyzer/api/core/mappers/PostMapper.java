package io.tagalyzer.api.core.mappers;

import io.tagalyzer.api.core.models.Post;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapper implements ResultSetMapper<Post> {

    @Override
    public Post map(int i, ResultSet rs, StatementContext ctx) throws SQLException {
        return new Post(rs.getLong("id"), rs.getString("client_id"), rs.getString("text"),
                rs.getFloat("sentiment_value"), rs.getTimestamp("created_at"), rs.getTimestamp("inserted_at"));
    }
}