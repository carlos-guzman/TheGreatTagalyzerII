package io.tagalyzer.api.core.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;

import java.sql.Timestamp;

@JsonSnakeCase
public class Post {
    private int id;
    private String client_id;
    private int owner_id;
    private String text;
    private Float sentiment_value;
    private Timestamp created_at;
    private Timestamp inserted_at;

    @JsonCreator
    public Post(@JsonProperty("id") int id,
                @JsonProperty("client_id") String client_id,
                @JsonProperty("owner_id") int owner_id,
                @JsonProperty("text") String text,
                @JsonProperty("sentiment_value") Float sentiment_value,
                @JsonProperty("created_at") Timestamp created_at,
                @JsonProperty("inserted_at") Timestamp inserted_at) {
        this.id = id;
        this.client_id = client_id;
        this.owner_id = owner_id;
        this.text = text;
        this.sentiment_value = sentiment_value;
        this.created_at = created_at;
        this.inserted_at = inserted_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Float getSentiment_value() {
        return sentiment_value;
    }

    public void setSentiment_value(Float sentiment_value) {
        this.sentiment_value = sentiment_value;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getInserted_at() {
        return inserted_at;
    }

    public void setInserted_at(Timestamp inserted_at) {
        this.inserted_at = inserted_at;
    }
}