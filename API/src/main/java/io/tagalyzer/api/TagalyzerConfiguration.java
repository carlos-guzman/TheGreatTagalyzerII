package io.tagalyzer.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class TagalyzerConfiguration extends Configuration {
    private DataSourceFactory database = new DataSourceFactory();
    public static final int shardTotal = 4;
    public static final int perPageCount = 5;

    @JsonProperty("database")
    public DataSourceFactory getDatabase(){
        return database;
    }

}