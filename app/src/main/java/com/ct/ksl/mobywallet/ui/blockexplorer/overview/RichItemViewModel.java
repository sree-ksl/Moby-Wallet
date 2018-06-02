package com.ct.ksl.mobywallet.ui.blockexplorer.overview;

import com.ct.ksl.tronlib.dto.CoinMarketCap;
import com.ct.ksl.tronlib.dto.RichInfo;
import com.ct.ksl.tronlib.dto.RichTotal;

import lombok.Getter;

/**
 * Created by user on 2018. 5. 30..
 */

@Getter
public class RichItemViewModel {
    private String balanceRange;
    private int addressCount;
    private float addressPercentage;
    private double coinPercentage;
    private double coins;
    private double usd;

    public RichItemViewModel(RichTotal total, RichInfo richInfo, CoinMarketCap coinMarketCap) {
        balanceRange = richInfo.getFrom() + "\n-\n" + richInfo.getTo();
        addressCount = richInfo.getAccounts();
        addressPercentage = richInfo.getAccounts() / (float) total.getAccounts() * 100f;
        coins = richInfo.getBalance();
        coinPercentage = richInfo.getBalance() / total.getCoins() * 100f;
        usd = coins * Double.parseDouble(coinMarketCap.getPriceUsd());
    }

    public String getBalanceRange() {
        return balanceRange;
    }

    public int getAddressCount() {
        return addressCount;
    }

    public float getAddressPercentage() {
        return addressPercentage;
    }

    public double getCoins() {
        return coins;
    }

    public double getCoinPercentage() {
        return coinPercentage;
    }

    public double getUsd() {
        return usd;
    }
}
