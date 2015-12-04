package io.tagalyzer.api.core.common;


import io.tagalyzer.api.core.dao.TestDAO;
import io.tagalyzer.api.core.models.Test;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tests")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestResource {
    private final TestDAO testDAO;

    public TestResource(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

    @POST
    public Response createTest(@Context HttpServletRequest request,
                               @Valid Test test) {
        int id = testDAO.create(test);

        if (id == 0) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }

        test.setId(id);

        return Response.ok(test).build();
    }

    @GET
    public Response listTests(@Context HttpServletRequest request) {
        List<Test> testList;

        testList = testDAO.list();

        return Response.ok(testList).build();
    }
}
