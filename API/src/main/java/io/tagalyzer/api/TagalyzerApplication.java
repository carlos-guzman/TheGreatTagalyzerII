package io.tagalyzer.api;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.tagalyzer.api.core.resources.TagResource;
import io.tagalyzer.api.core.dao.PostDAO;
import io.tagalyzer.api.core.dao.TagDAO;
import io.tagalyzer.api.exception_mapper.UnableToExecuteStatementExceptionMapper;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.io.IOException;
import java.util.EnumSet;

public class TagalyzerApplication extends Application<TagalyzerConfiguration> {
    
    public static void main(String[] args) throws Exception {
        new TagalyzerApplication().run(args);
    }
    
    @Override
    public void initialize(Bootstrap<TagalyzerConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<TagalyzerConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(TagalyzerConfiguration configuration) {
                return configuration.getDatabase();
            }
        });
    }

    @Override
    public void run(TagalyzerConfiguration configuration, Environment environment) throws IOException, ClassNotFoundException {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDatabase(), "database");

        TagDAO tagDAO = jdbi.onDemand(TagDAO.class);
        PostDAO postDAO = jdbi.onDemand(PostDAO.class);

        TagResource tagResource = new TagResource(tagDAO, postDAO);

        environment.jersey().register(new UnableToExecuteStatementExceptionMapper());

        environment.jersey().register(tagResource);

        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
