package com.georgeren.mykotlin.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by georgeRen on 2017/11/28.
 */
interface RedditApi {
    @GET("/top.json")
    fun getTop(@Query("after") after: String, @Query("limit") limit: Int)
            : Call<RedditNewsResponse>
}