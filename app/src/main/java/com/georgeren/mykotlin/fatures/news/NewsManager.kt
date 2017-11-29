package com.georgeren.mykotlin.fatures.news

import com.georgeren.mykotlin.api.NewsApi
import com.georgeren.mykotlin.api.RestApi
import com.georgeren.mykotlin.bean.RedditNews
import com.georgeren.mykotlin.bean.RedditNewsItem
import rx.Observable

/**
 * Created by georgeRen on 2017/11/28.
 */
class NewsManager(private val api: NewsApi = RestApi()) {
    fun getNews(after: String, limit: Int = 10): Observable<RedditNews> {
        return Observable.create { subscriber ->
            val execute = api.getNews(after, limit).execute()
            var response = execute.body().data
            if (execute.isSuccessful) {
                var items = response.children.map {
                    var item = it.data
                    RedditNewsItem(item.author, item.title, item.num_comments,
                            item.created, item.thumbnail, item.url)
                }
                var news = RedditNews(response.after ?: "", response.before ?: "", items)
                subscriber.onNext(news)
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(execute.message()))
            }
        }
    }
}