package com.ct.ksl.mobywallet.tron.repository;

import android.support.annotation.NonNull;

import com.ct.ksl.mobywallet.database.model.AccountModel;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public interface AccountRepository {

    Single<Boolean> storeAccount(@NonNull AccountModel accountModel);

    Single<Boolean> updateAccount(@NonNull AccountModel accountModel);

    Maybe<AccountModel> loadAccount(int index);

    Single<List<AccountModel>> loadAllAccounts();

    Single<Integer> countAccount();

    Maybe<AccountModel> loadByAccountKey(String accountKey);
}
