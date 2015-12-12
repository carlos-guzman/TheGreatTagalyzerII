package io.tagalyzer.api.core.dao;

import io.tagalyzer.api.core.mappers.PostMapper;
import io.tagalyzer.api.core.mappers.TagMapper;
import io.tagalyzer.api.core.models.Post;
import io.tagalyzer.api.core.models.Tag;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import java.util.List;

@RegisterMapper(TagMapper.class)
@UseStringTemplate3StatementLocator
public interface TagDAO {

    @Mapper(PostMapper.class)
    @SqlQuery("select posts.* from hashtag_post left join posts on hashtag_post.post_id=posts.id left join hashtags on hashtags.id=hashtag_post.tag_id where hashtags.tag=:tag ORDER BY hashtags.id ASC")
    List<Post> listPosts(@Bind("tag") String tag);

    @SqlQuery("select * from hashtags ORDER BY id ASC")
    List<Tag> listTags();


}
