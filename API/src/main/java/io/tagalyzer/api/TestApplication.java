package io.tagalyzer.api;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.tagalyzer.api.core.common.TestResource;
import io.tagalyzer.api.core.dao.TestDAO;
import io.tagalyzer.api.exception_mapper.UnableToExecuteStatementExceptionMapper;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.io.IOException;
import java.util.EnumSet;

public class TestApplication extends Application<TestConfiguration> {
    
    public static void main(String[] args) throws Exception {
        new TestApplication().run(args);
    }
    
    @Override
    public void initialize(Bootstrap<TestConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<TestConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(TestConfiguration configuration) {
                return configuration.getDatabase();
            }
        });
    }

    @Override
    public void run(TestConfiguration configuration, Environment environment) throws IOException, ClassNotFoundException {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDatabase(), "database");

        TestDAO testDAO = jdbi.onDemand(TestDAO.class);

        TestResource testResource = new TestResource(testDAO);

        environment.jersey().register(new UnableToExecuteStatementExceptionMapper());

        environment.jersey().register(testResource);

        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
