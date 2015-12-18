package io.tagalyzer.api.core.resources;


import io.tagalyzer.api.TagalyzerConfiguration;
import io.tagalyzer.api.core.dao.PostDAO;
import io.tagalyzer.api.core.dao.TagDAO;
import io.tagalyzer.api.core.models.Post;
import io.tagalyzer.api.core.models.Tag;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResource {

    //TODO divide this into PostResource and TagResource

    private final TagDAO tagDAO;
    private final PostDAO postDAO;

    public TagResource(TagDAO tagDAO, PostDAO postDAO) {
        this.tagDAO = tagDAO;
        this.postDAO = postDAO;
    }

    @POST
    @Path("/posts")
    public Response createPost(@Valid Post post) {

        Set<String> tagList = parsePost(post);
        String shard = null;
        long tag_id = 0;
        long post_id= 0;
        for(String tag : tagList) {
            shard = getShard(tag);
            tag_id = tagDAO.createTag(shard, tag);
            if (tag_id==0)
                tag_id = tagDAO.getTag(shard, tag).getId();
            postDAO.createPost(shard, post, tag_id);
            System.out.print("a");
        }
        if (tagList.isEmpty()){
            System.out.print("b");
            postDAO.createPost("shard_0000001", post, 0);
        }

        System.out.println(shard+"  "+tag_id+"   "+post_id);
        return Response.ok().build();
    }

    @GET
    @Path("/hashtags/{tag_name}/posts")
    public Response listPosts(@PathParam("tag_name") String tagName,
                              @QueryParam("page") int pageNum) {
        List<Post> postList;
        String shardStr = getShard(tagName);
        HashMap<String, Object> entity = new HashMap<>();

        if (pageNum < -1) {
            return Response.status(400).entity(new HashMap<String, String>(){{put("message","Please enter a positive page number");}}).build();
        } else if (pageNum > 0) {
            pageNum--;
        }

        int count = tagDAO.getCount(shardStr, tagName);

        if (pageNum == -1){
            postList = postDAO.listAllPosts(shardStr, tagName);
        } else {
            postList = postDAO.listPosts(shardStr, tagName, pageNum);
            if (count > (pageNum+1)*TagalyzerConfiguration.perPageCount) {
                entity.put("next_page", "api.tagalyzer.io/hashtags/" + tagName + "/posts?page=" + (pageNum + 2));
            }
            if (pageNum > 0){
                entity.put("previous_page", "api.tagalyzer.io/hashtags/" + tagName + "/posts?page=" + pageNum);
            }
        }
        entity.put("posts", postList);
        entity.put("count", count);
        entity.put("page", pageNum+1);



        return Response.ok(entity).build();
    }

    @GET
    @Path("/hashtags/{tag_name}")
    public Response getHashtag(@PathParam("tag_name") String tagName) {
        String shardStr = getShard(tagName);

        Tag tag = tagDAO.getTag(shardStr, tagName);

        if (tag == null){
            return Response.status(404).build();
        }

        return Response.ok(tag).build();
    }

    @GET
    @Path("/hashtags")
    public Response listHashtags(){
        List<Tag> tagList = new ArrayList<Tag>();

        for(int i=1;i<=TagalyzerConfiguration.shardTotal;i++) {
            tagList.addAll(tagDAO.listTags(String.format("shard_%07d", i)));
        }
        Collections.sort(tagList, new Comparator<Tag>() {
            @Override
            public int compare(Tag o1, Tag o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return Response.ok(tagList).build();
    }

    @GET
    @Path("/posts/{post_id}")
    public Response getPostById(@PathParam("post_id") long postId){
        Post post= null;
        for(int i=1;i<=TagalyzerConfiguration.shardTotal;i++) {
            post = postDAO.getPost(String.format("shard_%07d", i), postId);
            if (post != null) break;
        }
        if (post==null){
            return Response.status(404).entity(new HashMap<String, String>() {{
                put("message", "Post with id "+postId+" not found.");
            }}).build();
        }
        return Response.ok(post).build();
    }

//    @GET
//    @Path("/analysis/{tag_name}")
//    public Response getAnalysis(@PathParam("tag_name") String tagName){
//        Tag tag;
//
//        tag = tagDAO.getTag(tagName);
//
//        return Response.ok(tag).build();
//    }

//    @GET
//    @Path("/stats/{tag_name}")
//    public Response getStats(@PathParam("tag_name") String tagName,
//                             @QueryParam("since") String sinceStr){
//        Stats stats = new Stats();
//        LocalDateTime since;
//        String whereClause = "";
//        try{
//            if(sinceStr != null) {
//                since = LocalDateTime.parse(sinceStr);
//                whereClause += " AND created_at >= '" + since.toLocalDate().toString() + " " +
//            }
//        } catch (DateTimeParseException e){
//            return Response.status(Response.Status.BAD_REQUEST).entity("Please enter a valid date (e.g 2015-10-28T14:21:08.412 or 2015-10-28T00:00)").build();
//        }
//
//        //Put in one query or keep it as several small ones?
//        stats.setTag(tagDAO.getTag(tagName));
//        stats.setCount(tagDAO.getCount(tagName));
//        //Percentages
//        stats.setLastUsed(tagDAO.getRecent(tagName, whereClause));
//
//        stats = tagDAO.getStats(tagName);
//
//        return Response.ok(stats).build();
//    }

    private String getShard(String tagName){
        int shardId = ((tagName.hashCode() % TagalyzerConfiguration.shardTotal) + TagalyzerConfiguration.shardTotal) % TagalyzerConfiguration.shardTotal;
        return String.format("shard_%07d", shardId+1);
    }
    private Set<String> parsePost(Post post){
        String[] words = post.getText().split(" ");
        Set<String> hashtags = new HashSet<String>();
        for (String word : words) {
            if (word.startsWith("#")) {
                hashtags.add(word.substring(1));
            }
        }
        return hashtags;
    }
}
