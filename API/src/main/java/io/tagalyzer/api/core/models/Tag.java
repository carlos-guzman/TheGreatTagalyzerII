package io.tagalyzer.api.core.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
public class Tag {
    private long id;
    private String name;
    private float sentimentValue;

    @JsonCreator
    public Tag(@JsonProperty("id") long id,
               @JsonProperty("name") String testName,
               @JsonProperty("sentiment_value") float sentimentValue) {
        this.id = id;
        this.name = testName;
        this.sentimentValue=sentimentValue;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getSentimentValue() {
        return sentimentValue;
    }

    public void setSentimentValue(float sentimentValue) {
        this.sentimentValue = sentimentValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (id != tag.id) return false;
        if (Float.compare(tag.sentimentValue, sentimentValue) != 0) return false;
        return !(name != null ? !name.equals(tag.name) : tag.name != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sentimentValue != +0.0f ? Float.floatToIntBits(sentimentValue) : 0);
        return result;
    }
}