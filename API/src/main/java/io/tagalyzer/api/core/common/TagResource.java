package io.tagalyzer.api.core.common;


import io.tagalyzer.api.core.dao.TagDAO;
import io.tagalyzer.api.core.models.Post;
import io.tagalyzer.api.core.models.Tag;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
//    public Response createTest(@Context HttpServletRequest request,
//                               @Valid Tag test) {
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
    public Response listPosts(@PathParam("tag_name") String tagName) {
        List<Post> postList;

        postList = tagDAO.listPosts(tagName);

        return Response.ok(postList).build();
    }

    @GET
    @Path("/hashtags")
    public Response listHashtags(){
        List<Tag> tagList;

        tagList = tagDAO.listTags();

        return Response.ok(tagList).build();
    }

}
