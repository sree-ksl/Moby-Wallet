package com.ct.ksl.mobywallet.ui.main.dto;

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
public class Frozen {

    private long frozenBalance;

    private long expireTime;

    public long getFrozenBalance() {
        return frozenBalance;
    }

    public long getExpireTime() {
        return expireTime;
    }
}
