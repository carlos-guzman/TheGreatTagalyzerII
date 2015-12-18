package io.tagalyzer.api.core.common;


import io.tagalyzer.api.TagalyzerConfiguration;
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
    private final TagDAO tagDAO;

    public TagResource(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
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
            tagDAO.createPost(shard, post, tag_id);
            System.out.print("a");
        }
        if (tagList.isEmpty()){
            System.out.print("b");
            tagDAO.createPost("shard_0000001", post, 0);
        }

        System.out.println(shard+"  "+tag_id+"   "+post_id);
        return Response.ok().build();
    }

    @GET
    @Path("/hashtags/{tag_name}")
    public Response listPosts(@PathParam("tag_name") String tagName) {
        List<Post> postList;
        String shardStr = getShard(tagName);

        postList = tagDAO.listPosts(shardStr, tagName);

        return Response.ok(postList).build();
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
