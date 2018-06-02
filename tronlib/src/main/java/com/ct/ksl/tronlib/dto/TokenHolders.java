package com.ct.ksl.tronlib.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenHolders {

    private long total;

    private List<TokenHolder> data;

    public List<TokenHolder> getData() {
        return data;
    }

    public long getTotal() {
        return total;
    }
}
