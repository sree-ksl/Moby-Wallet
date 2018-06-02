package com.ct.ksl.mobywallet.ui.vote.dto;

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
public class VoteItem {

    private int no;

    private String address;

    private String url;

    private boolean hasTeamPage;

    private Long totalVoteCount;

    private Long voteCount;

    private Long myVoteCount;

    public String getAddress() {
        return address;
    }

    public String getUrl() {
        return url;
    }

    public Long getMyVoteCount() {
        return myVoteCount;
    }

    public int getNo() {
        return no;
    }

    public void setTotalVoteCount(long totalVoteCount) {
        this.totalVoteCount = totalVoteCount;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public Long getTotalVoteCount() {
        return totalVoteCount;
    }

    public boolean isHasTeamPage() {
        return hasTeamPage;
    }
}
