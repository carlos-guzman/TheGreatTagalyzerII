package io.tagalyzer.api.core.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@JsonSnakeCase
public class Stats {
    private Tag tag;
    private int count;
    private Date lastUsed;
    private List<Tag> mostUsedTags;

    @JsonCreator
    public Stats(@JsonProperty("tag") Tag tag,
                 @JsonProperty("count") int count,
                 @JsonProperty("last_used") Date lastUsed){
//
//    },
//                 @JsonProperty("most_used_tags") List<Tag> mostUsedTags) {
        this.tag = tag;
        this.count = count;
        this.lastUsed = lastUsed;
        this.mostUsedTags = mostUsedTags;
    }

    public Stats(){
        this(null, 0, null);
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    public List<Tag> getMostUsedTags() {
        return mostUsedTags;
    }

    public void setMostUsedTags(List<Tag> mostUsedTags) {
        this.mostUsedTags = mostUsedTags;
    }
}
