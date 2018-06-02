package com.ct.ksl.mobywallet.ui.mytransfer.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by user on 2018. 5. 17..
 */

@Getter
@Setter
public class TransferInfo {

    private String hash;
    private long block;
    private long timestamp;
    private String transferFromAddress;
    private String transferToAddress;
    private boolean isSend;
    private long amount;
    private String tokenName;
    private boolean confirmed;
    private long total;
    private boolean send;

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setBlock(long block) {
        this.block = block;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }


    public void setTransferFromAddress(String transferFromAddress) {
        this.transferFromAddress = transferFromAddress;
    }

    public void setTransferToAddress(String transferToAddress) {
        this.transferToAddress = transferToAddress;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getAmount() {
        return amount;
    }

    public String getTokenName() {
        return tokenName;
    }

    public String getTransferToAddress() {
        return transferToAddress;
    }

    public String getTransferFromAddress() {
        return transferFromAddress;
    }

    public boolean isSend() {
        return send;
    }

    public String getHash() {
        return hash;
    }

    public long getBlock() {
        return block;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setSend(boolean send) {
        this.send = send;
    }
}
