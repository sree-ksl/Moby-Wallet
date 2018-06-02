package com.ct.ksl.tronlib.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TronAccounts {

    private long total;

    private List<TronAccount> data;

    public List<TronAccount> getData() {
        return data;
    }

    public long getTotal() {
        return total;
    }
}
