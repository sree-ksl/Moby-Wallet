package com.ct.ksl.mobywallet.ui.representative.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepresentativeList {

    private int representativeCount;

    private long highestVotes;

    private List<Representative> representativeList;

    public int getRepresentativeCount() {
        return representativeCount;
    }

    public long getHighestVotes() {
        return highestVotes;
    }
}
