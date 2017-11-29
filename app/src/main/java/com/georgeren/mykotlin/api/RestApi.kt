package com.georgeren.mykotlin.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by georgeRen on 2017/11/28.
 */
class RestApi : NewsApi {
    private val redditApi: RedditApi

    init {// 初始化代码块 初始化 redditApi
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        redditApi = retrofit.create(RedditApi::class.java)
    }

    override fun getNews(after: String, limit: Int): Call<RedditNewsResponse> {
        return redditApi.getTop(after, limit)
    }
}