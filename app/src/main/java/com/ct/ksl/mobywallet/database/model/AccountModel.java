package com.ct.ksl.mobywallet.database.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

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
@Entity(tableName = "account")
public class AccountModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String account;

    private boolean imported;

    private Date created;

    private Date updated;

    @Override
    public String toString() {
        if (imported) {
            return "[I] " + name;
        }

        return name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
