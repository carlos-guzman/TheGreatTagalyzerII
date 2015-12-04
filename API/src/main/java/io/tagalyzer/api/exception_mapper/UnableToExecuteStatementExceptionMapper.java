package io.tagalyzer.api.exception_mapper;

import org.postgresql.util.PSQLException;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnableToExecuteStatementExceptionMapper implements ExceptionMapper<UnableToExecuteStatementException> {

        public UnableToExecuteStatementExceptionMapper(){}

        @Override
        public Response toResponse(UnableToExecuteStatementException e) {
            String errorMessage;
            Response.Status statusCode;
            System.out.println(e);
            String errorCode = ((PSQLException) e.getCause()).getSQLState();
            switch (errorCode){
                case "23505":
                    errorMessage = "Object already exists in the database.";
                    statusCode = Response.Status.CONFLICT;
                    break;

                case "23503":
                    errorMessage = "Foreign key constraint violation. You are trying to reference a non-existing element.";
                    statusCode = Response.Status.BAD_REQUEST;
                    break;

                default:
                    errorMessage = "Unknown PSQL error: " + errorCode;
                    statusCode = Response.Status.INTERNAL_SERVER_ERROR;
                    break;
            }
            return Response.status(statusCode).entity("Error: " + errorMessage).build();
        }
}
