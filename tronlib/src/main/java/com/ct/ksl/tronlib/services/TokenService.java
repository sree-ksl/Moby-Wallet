package com.ct.ksl.tronlib.services;

import com.ct.ksl.tronlib.dto.Token;
import com.ct.ksl.tronlib.dto.TokenHolders;
import com.ct.ksl.tronlib.dto.Tokens;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TokenService {

    @GET("api/token")
    Single<Tokens> getTokens(@Query("start") long start, @Query("limit") int limit,
            @Query("sort") String sort, @Query("status") String status);

    @GET("api/token/{name}")
    Single<Token> getTokenDetail(@Path("name") String tokenName);

    @GET("api/token/{name}/address")
    Single<TokenHolders> getTokenHolders(@Path("name") String tokenName, @Query("start") long start,
            @Query("limit") int limit, @Query("sort") String sort);
}
