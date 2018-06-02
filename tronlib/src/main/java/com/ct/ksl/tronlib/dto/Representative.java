package com.ct.ksl.tronlib.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Representative {

    private String address;

    @JsonProperty("change_day")
    private long changeDay;

    @JsonProperty("change_hour")
    private long changeHour;

    private boolean hasPage;

    private String url;

    private Long votes;

    public String getAddress() {
        return address;
    }

    public String getUrl() {
        return url;
    }

    public Long getVotes() {
        return votes;
    }

    public boolean isHasPage() {
        return hasPage;
    }
}
