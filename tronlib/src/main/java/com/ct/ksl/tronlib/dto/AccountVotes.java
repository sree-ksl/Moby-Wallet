package com.ct.ksl.tronlib.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountVotes {

    @JsonProperty("total")
    private int total;

    @JsonProperty("data")
    List<AccountVote> data;

    private long totalVotes;

    public List<AccountVote> getData() {
        return data;
    }

    public long getTotalVotes() {
        return totalVotes;
    }

    public int getTotal() {
        return total;
    }
}
