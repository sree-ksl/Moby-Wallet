package com.ct.ksl.mobywallet.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ct.ksl.mobywallet.database.model.WalletModel;

@Dao
public interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WalletModel walletModel);

    @Update
    void update(WalletModel walletModel);

    @Delete
    void delete(WalletModel walletModel);

    @Query("SELECT COUNT(*) from wallet")
    int countWallets();

    @Query("SELECT * from wallet LIMIT 1")
    WalletModel loadWallet();
}
