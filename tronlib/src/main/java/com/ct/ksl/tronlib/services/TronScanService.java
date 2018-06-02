package com.ct.ksl.tronlib.services;

import com.ct.ksl.tronlib.dto.Blocks;
import com.ct.ksl.tronlib.dto.Market;
import com.ct.ksl.tronlib.dto.Stat;
import com.ct.ksl.tronlib.dto.SystemStatus;
import com.ct.ksl.tronlib.dto.TransactionStats;
import com.ct.ksl.tronlib.dto.Transactions;
import com.ct.ksl.tronlib.dto.TransferStats;
import com.ct.ksl.tronlib.dto.Transfers;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by user on 2018. 5. 17..
 */

public interface TronScanService {

    @GET("api/transfer")
    Single<Transfers> getTransfers(@Query("sort") String sort, @Query("count") boolean hasCount, @Query("limit") int limit,
            @Query("start") long start, @Query("block") long block);

    @GET("api/transfer")
    Single<Transfers> getTransfersByAddress(@Query("sort") String sort, @Query("count") boolean hasCount, @Query("limit") int limit,
        @Query("start") long start, @Query("address") String address);

    @GET("api/transfer")
    Single<Transfers> getTransfers(@Query("address") String address, @Query("token") String token);

    @GET("api/transfer")
    Single<Transfers> getTransfers(@Query("sort") String sort, @Query("count") boolean hasCount, @Query("limit") int limit,
            @Query("start") long start, @Query("token") String token);

    @GET("api/transfer")
    Single<Transfers> getTransfers(@Query("address") String address, @Query("start") long start,
            @Query("limit") int limit, @Query("sort") String sort, @Query("count") boolean count);

    @GET("api/transfer")
    Single<Transfers> getTransfers(@Query("start") long start, @Query("limit") int limit,
            @Query("sort") String sort, @Query("count") boolean count);

    @GET("api/transfer")
    Single<Transfers> getTransfers(@Query("start") int start, @Query("limit") int limit,
            @Query("sort") String sort, @Query("count") boolean count, @Query("address") String address);

    @GET("api/market/markets")
    Single<List<Market>> getMarket();

    @GET("api/block")
    Single<Blocks> getBlocks(@Query("sort") String sort, @Query("limit") int limit, @Query("start") long start);

    @GET("api/block")
    Single<Blocks> getBlock(@Query("sort") String sort, @Query("limit") int limit, @Query("number") long blockNumber);

    @GET("api/account/{address}/stats")
    Single<TransactionStats> getTransactionStats(@Path("address") String address);

    @GET("api/transaction")
    Single<Transactions> getTransactions(@Query("start") long start, @Query("limit") int limit,
            @Query("sort") String sort, @Query("count") boolean count);

    @GET("api/transaction")
    Single<Transactions> getTransactions(@Query("address") String address, @Query("start") long start, @Query("limit") int limit,
            @Query("sort") String sort, @Query("count") boolean count);

    @GET("api/system/status")
    Single<SystemStatus> getStatus();

    @GET("api/block/stats")
    Single<List<Stat>> getBlockStats(@Query("info") String info);

    @GET("api/transaction")
    Single<Transactions> getTransactions(@Query("sort") String sort, @Query("count") boolean count,
                                         @Query("limit") int limit, @Query("start") long start, @Query("block") long block);

    @GET("api/transfer/stats")
    Single<TransferStats> getTransferStats(@Query("groupby") String groupBy, @Query("interval") String interval);
}
