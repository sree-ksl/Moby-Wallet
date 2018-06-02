package com.ct.ksl.tronlib.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transactions {

    private int total;
    private List<Transaction> data;

    public List<Transaction> getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }
}
