package com.ct.ksl.tronlib.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferStats {

    // transactions
    private List<Stat> total;

    // transfers
    private List<Stat> value;

    public List<Stat> getValue() {
        return value;
    }

    public List<Stat> getTotal() {
        return total;
    }
}
