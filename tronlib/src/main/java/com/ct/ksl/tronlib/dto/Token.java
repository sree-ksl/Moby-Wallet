package com.ct.ksl.tronlib.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {

    private String id;

    private long block;

    private int num;

    private String name;

    private String description;

    private long startTime;

    private long endTime;

    private int price;

    private long totalSupply;

    private long issued;

    private double percentage;

    private String ownerAddress;

    private String transaction;

    private long trxNum;

    private String url;

    private int voteScore;

    private long created;

    private long dateCreated;

    private long nrOfTokenHolders;

    private long totalTransactions;

    public String getName() {
        return name;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public long getTotalSupply() {
        return totalSupply;
    }

    public long getNrOfTokenHolders() {
        return nrOfTokenHolders;
    }

    public long getTotalTransactions() {
        return totalTransactions;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getIssued() {
        return issued;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getId() {
        return id;
    }
}