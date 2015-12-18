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

@RegisterMapper(PostMapper.class)
@UseStringTemplate3StatementLocator
public interface PostDAO {
    int perPageCount = TagalyzerConfiguration.perPageCount;

    @SqlQuery("select posts.* from <shard>.posts left join <shard>.hashtags on posts.hashtag_id=hashtags.id where hashtags.name=:tag ORDER BY created_at DESC  OFFSET :page_num*" + perPageCount + " LIMIT " + perPageCount)
    List<Post> listPosts(@Define("shard") String shard, @Bind("tag") String tag, @Bind("page_num") int pageNum);

    @SqlQuery("select posts.* from <shard>.posts left join <shard>.hashtags on posts.hashtag_id=hashtags.id where hashtags.name=:tag ORDER BY created_at DESC")
    List<Post> listAllPosts(@Define("shard") String shard, @Bind("tag") String tag);

    @SqlQuery("select * from <shard>.hashtags where name=:tag")
    Tag getTag(@Define("shard") String shard, @Bind("tag") String tag);


    @Mapper(PostMapper.class)
    @SqlQuery("select * from <shard>.posts where id=:post_id")
    Post getPost(@Define("shard") String shard, @Bind("post_id") long postId);

    @SqlQuery("insert into <shard>.posts (client_id, text, created_at, hashtag_id) values (:p.client_id, :p.text, :p.created_at, :tag_id) returning id;")
    long createPost(@Define("shard") String shard, @BindBean("p") Post post, @Bind("tag_id") long tag_id);
}
