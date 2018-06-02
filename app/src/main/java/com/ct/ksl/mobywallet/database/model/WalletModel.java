package com.ct.ksl.mobywallet.database.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.squareup.okhttp.HttpUrl;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "wallet")
public class WalletModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String password;

    private boolean agree;

    private Date created;

    private Date updated;

    public String getPassword() {
        return password;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
