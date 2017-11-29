package com.georgeren.mykotlin.api

import retrofit2.Call


/**
 * Created by georgeRen on 2017/11/28.
 */
interface NewsApi {
    fun getNews(after: String, limit: Int): Call<RedditNewsResponse>
}