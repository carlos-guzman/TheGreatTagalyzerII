package io.tagalyzer.api.core.common;


import io.tagalyzer.api.TagalyzerConfiguration;
import io.tagalyzer.api.core.dao.TagDAO;
import io.tagalyzer.api.core.models.Post;
import io.tagalyzer.api.core.models.Stats;
import io.tagalyzer.api.core.models.Tag;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResource {
    private final TagDAO tagDAO;

    public TagResource(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

//    @POST
//    @Path("/posts")
//    public Response createTag(@Valid Post post) {
//
//        int id = tagDAO.create(test);
//
//        if (id == 0) {
//            return Response.status(Response.Status.NOT_MODIFIED).build();
//        }
//
//        test.setId(id);
//
//        return Response.ok(test).build();
//    }

    @GET
    @Path("/hashtags/{tag_name}")
    public Response listPosts(@PathParam("tag_name") String tagName,
                              @QueryParam("since") String sinceStr) {
        List<Post> postList;
        String shardStr = getShard(tagName);

        postList = tagDAO.listPosts(shardStr, tagName);

        return Response.ok(postList).build();
    }

    @GET
    @Path("/hashtags")
    public Response listHashtags(){
        List<Tag> tagList;

        tagList = tagDAO.listTags();

        return Response.ok(tagList).build();
    }

    @GET
    @Path("/analysis/{tag_name}")
    public Response getAnalysis(@PathParam("tag_name") String tagName){
        Tag tag;

        tag = tagDAO.getTag(tagName);

        return Response.ok(tag).build();
    }

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

    public String getShard(String tagName){
        int shardId = ((tagName.hashCode() % TagalyzerConfiguration.shardTotal) + TagalyzerConfiguration.shardTotal) % TagalyzerConfiguration.shardTotal;
        return String.format("shard_%07d", shardId);
    }
}
