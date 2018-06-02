package com.ct.ksl.tronlib.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenHolder {

    private String address;

    private String name;

    private long totalSupply;

    private long balance;

    private double balancePercent;

    public void setTotalSupply(long totalSupply) {
        this.totalSupply = totalSupply;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalancePercent(double balancePercent) {
        this.balancePercent = balancePercent;
    }

    public String getAddress() {
        return address;
    }

    public double getBalancePercent() {
        return balancePercent;
    }

    public long getTotalSupply() {
        return totalSupply;
    }
}
