package io.tagalyzer.api.core.mappers;

import io.tagalyzer.api.core.models.Test;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestMapper implements ResultSetMapper<Test> {

    @Override
    public Test map(int i, ResultSet rs, StatementContext ctx) throws SQLException {
        return new Test(rs.getInt("id"), rs.getString("name"));
    }
}
