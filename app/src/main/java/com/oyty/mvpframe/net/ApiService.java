package com.oyty.mvpframe.net;

import com.oyty.mvpframe.entity.MarketEntity;
import com.oyty.mvpframe.entity.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by oyty on 2019/3/15.
 */
public interface ApiService {

    @GET("/api/category/v2")
    Observable<List<MarketEntity>> getMarkets(@Query("offset") String offset,
                                              @Query("category_id") String categoryId);

    String HEADER_API_VERSION = "Accept: application/vnd.github.v3+json";

    @Headers({HEADER_API_VERSION})
    @GET("/users")
    Observable<List<User>> getUsers(@Query("since") int lastIdQueried, @Query("per_page") int perPage);
}
