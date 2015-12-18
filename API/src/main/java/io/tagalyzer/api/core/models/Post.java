package io.tagalyzer.api.core.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;

import java.sql.Timestamp;

@JsonSnakeCase
public class Post {
    private long id;
    private String clientId;
    private int ownerId;
    private String text;
    private Float sentimentValue;
    private Timestamp created_at;
    private Timestamp inserted_at;
    private String url;

    @JsonCreator
    public Post(@JsonProperty("id") long id,
                @JsonProperty("client_id") String clientId,
                @JsonProperty("owner_id") int ownerId,
                @JsonProperty("text") String text,
                @JsonProperty("sentiment_value") Float sentimentValue,
                @JsonProperty("created_at") Timestamp createdAt,
                @JsonProperty("inserted_at") Timestamp insertedAt) {
        this(id, clientId, ownerId, text, sentimentValue, createdAt, insertedAt, null);
    }

    public Post(long id,
                String clientId,
                String text,
                Float sentimentValue,
                Timestamp createdAt,
                Timestamp insertedAt){
        this(id, clientId, 0, text, sentimentValue, createdAt, insertedAt, null);
    }

    public Post(long id,
                String clientId,
                int ownerId,
                String text,
                Float sentimentValue,
                Timestamp createdAt,
                Timestamp insertedAt,
                String url){
        this.id = id;
        this.clientId = clientId;
        this.ownerId = ownerId;
        this.text = text;
        this.sentimentValue = sentimentValue;
        this.created_at = createdAt;
        this.inserted_at = insertedAt;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient_id() {
        return clientId;
    }

    public void setClient_id(String client_id) {
        this.clientId = client_id;
    }

    @JsonIgnore
    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Float getSentimentValue() {
        return sentimentValue;
    }

    public void setSentimentValue(Float sentimentValue) {
        this.sentimentValue = sentimentValue;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Timestamp getInserted_at() {
        return inserted_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setInserted_at(Timestamp inserted_at) {
        this.inserted_at = inserted_at;
    }

    @JsonIgnore
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}