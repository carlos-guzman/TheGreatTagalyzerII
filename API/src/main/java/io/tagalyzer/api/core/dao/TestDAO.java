package io.tagalyzer.api.core.dao;

import io.tagalyzer.api.core.mappers.TestMapper;
import io.tagalyzer.api.core.models.Test;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import java.util.List;

@RegisterMapper(TestMapper.class)
@UseStringTemplate3StatementLocator
public interface TestDAO {
    String myTable = "tests";

    @SqlQuery("INSERT INTO " + myTable +
              " (name) " +
              " VALUES " +
              " (:t.name)" +
              " RETURNING * ")
    @GetGeneratedKeys
    Integer create(@BindBean("t") Test test);

    @SqlQuery("SELECT * FROM " + myTable + " ORDER BY id ASC")
    List<Test> list();
}
