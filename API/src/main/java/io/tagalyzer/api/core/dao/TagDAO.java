package io.tagalyzer.api.core.dao;

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

    @Mapper(PostMapper.class)
    @SqlQuery("select posts.* from <shard>.posts left join <shard>.hashtags on posts.hashtag_id=hashtags.id where hashtags.name=:tag ORDER BY created_at DESC")
    List<Post> listPosts(@Define("shard") String shard,@Bind("tag") String tag);

    @SqlQuery("select * from <shard>.hashtags WHERE id > 0 ORDER BY name ASC")
    List<Tag> listTags(@Define("shard") String shard);

    @SqlQuery("select * from <shard>.hashtags where name=:tag")
    Tag getTag(@Define("shard") String shard, @Bind("tag") String tag);

//    @Mapper(StatsMapper.class)
//    @SqlQuery("select hashtags.*, count from hashtags left join (select tag_id,count(*) as count from hashtag_post GROUP BY tag_id) as hp on hp.tag_id=hashtags.id where tag=:tag")
//    Stats getStats(@Bind("tag") String tag);

    @SqlQuery("select count(*) from hashtag_post left join hashtags on tag_id=id where tag=:tag")
    int getCount(@Bind("tag") String tag);

    @SqlQuery("insert into <shard>.hashtags (name) select :tag where not exists (select id from <shard>.hashtags where name=:tag) returning id;")
    @GetGeneratedKeys
    long createTag(@Define("shard") String shard, @Bind("tag") String tag);

    @SqlQuery("insert into <shard>.posts (client_id, text, created_at, hashtag_id) values (:p.client_id, :p.text, :p.created_at, :tag_id) returning id;")
    long createPost(@Define("shard") String shard, @BindBean("p") Post post, @Bind("tag_id") long tag_id);
}
