package com.ct.ksl.tronlib.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    private String name;

    private String address;

    private long balance;

    private List<Balance> balances;

    private Bandwidth bandwidth;

    private List<Balance> tokenBalances;

    private Representative representative;

    private Frozen frozen;

    public Frozen getFrozen() {
        return frozen;
    }

    public List<Balance> getTokenBalances() {
        return tokenBalances;
    }

    public String getName() {
        return name;
    }

    public long getBalance() {
        return balance;
    }

    public Bandwidth getBandwidth() {
        return bandwidth;
    }

    public String getAddress() {
        return address;
    }

    public Representative getRepresentative() {
        return representative;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Balance {
        private String name;
        private long balance;

        public String getName() {
            return name;
        }

        public long getBalance() {
            return balance;
        }
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Representative {
        private long allowance;
        private boolean enabled;
        private long lastWithDrawTime;
        private String url;

        public boolean isEnabled() {
            return enabled;
        }
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Bandwidth {
        private long freeNetLimit;
        private double freeNetPercentage;
        private long freeNetRemaining;
        private long freeNetUsed;
        private long netLimit;
        private double netPercentage;
        private long netRemaining;
        private long netUsed;

        private Map<String, Bandwidth> assets;

        public long getNetRemaining() {
            return netRemaining;
        }
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Frozen {
        private long total;
        private List<FrozenTrx> balances;

        public List<FrozenTrx> getBalances() {
            return balances;
        }
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FrozenTrx {
        private long amount;
        private long expires;

        public long getAmount() {
            return amount;
        }

        public long getExpires() {
            return expires;
        }
    }
}
