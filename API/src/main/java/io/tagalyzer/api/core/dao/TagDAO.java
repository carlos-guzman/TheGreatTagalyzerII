package io.tagalyzer.api.core.dao;

import io.tagalyzer.api.TagalyzerConfiguration;
import io.tagalyzer.api.core.mappers.PostMapper;
import io.tagalyzer.api.core.mappers.TagMapper;
import io.tagalyzer.api.core.models.Post;
import io.tagalyzer.api.core.models.Tag;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Define;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import java.util.List;

@RegisterMapper(TagMapper.class)
@UseStringTemplate3StatementLocator
public interface TagDAO {
    int perPageCount = TagalyzerConfiguration.perPageCount;

    @SqlQuery("select * from <shard>.hashtags WHERE id > 0 ORDER BY name ASC")
    List<Tag> listTags(@Define("shard") String shard);

    @SqlQuery("select * from <shard>.hashtags where name=:tag")
    Tag getTag(@Define("shard") String shard, @Bind("tag") String tag);

//    @Mapper(StatsMapper.class)
//    @SqlQuery("select hashtags.*, count from hashtags left join (select tag_id,count(*) as count from hashtag_post GROUP BY tag_id) as hp on hp.tag_id=hashtags.id where tag=:tag")
//    Stats getStats(@Bind("tag") String tag);

    @SqlQuery("select count(*) from  <shard>.posts left join <shard>.hashtags on posts.hashtag_id=hashtags.id where hashtags.name=:tag")
    int getCount(@Define("shard") String shard, @Bind("tag") String tag);

    @SqlQuery("insert into <shard>.hashtags (name) select :tag where not exists (select id from <shard>.hashtags where name=:tag) returning id;")
    @GetGeneratedKeys
    long createTag(@Define("shard") String shard, @Bind("tag") String tag);

}
