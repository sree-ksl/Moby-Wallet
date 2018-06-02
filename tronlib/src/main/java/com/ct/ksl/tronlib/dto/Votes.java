package com.ct.ksl.tronlib.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Votes {

    @JsonProperty("total_votes")
    private long totalVotes;

    @JsonProperty("candidates")
    private List<Representative> votesList;

    public List<Representative> getVotesList() {
        return votesList;
    }

    public long getTotalVotes() {
        return totalVotes;
    }
}
