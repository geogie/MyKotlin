package com.georgeren.mykotlin.fatures.news

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.georgeren.mykotlin.R
import com.georgeren.mykotlin.bean.RedditNews
import com.georgeren.mykotlin.commons.BaseFragment
import com.georgeren.mykotlin.commons.InfiniteScrollListener
import com.georgeren.mykotlin.commons.extensions.inflate
import com.georgeren.mykotlin.commons.extensions.showSnackbar
import kotlinx.android.synthetic.main.fragment_news.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by georgeRen on 2017/11/27.
 */
class NewsFragment : BaseFragment() {
    private var redditNews: RedditNews? = null
    private var infiniteScrollListen: InfiniteScrollListener? = null
    private val idle = -1
    private val refresh = 0
    private val loadMore = 1
    private var type = idle


    companion object {
        // 静态内部类
        private var KEY_REDDIT_NEWS = "redditNes"
    }

    private val newsManager by lazy {
        NewsManager()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_news)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        news_refresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            if (type == idle) {
                type = refresh
                requestData()
            } else {
                news_refresh.isRefreshing = false
            }

        })
        news_list.apply {
            // 初始化recyclerView：itemFixed、linearManager、addOnListener、adapter
            news_list.setHasFixedSize(true)
            news_list.layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
            infiniteScrollListen = InfiniteScrollListener({
                if (type == idle) {
                    type = loadMore
                    requestData()
                } else {

                }
            }, news_list.layoutManager as LinearLayoutManager)
            addOnScrollListener(infiniteScrollListen) // 加载更多
            adapter = NewsAdapter()
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REDDIT_NEWS)) {
            var redditNews = savedInstanceState.get(KEY_REDDIT_NEWS) as RedditNews
            var news = redditNews.news
            (news_list.adapter as NewsAdapter).clearAndAddNews(news)
        } else {
            requestData()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {// 保护现场
        super.onSaveInstanceState(outState)
        var news = (news_list.adapter as NewsAdapter).getNews()
        if (redditNews != null && news.isNotEmpty()) {
            outState?.putParcelable(KEY_REDDIT_NEWS, redditNews?.copy(news = news))
        }
    }

    private fun requestData() {// 根据after获取网络数据：一次10条
        (news_list.adapter as NewsAdapter).showLoading()
        var after = ""
        if (type == refresh)
            after = ""
        else
            after = redditNews?.after ?: ""
        val subscribe = newsManager.getNews(after, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ retrievedNews ->
                    if (type == refresh) {
                        news_refresh.isRefreshing = false
                        redditNews = retrievedNews
                        (news_list.adapter as NewsAdapter).clearAndAddNews(retrievedNews.news)
                        infiniteScrollListen!!.previousTotal = 0
                    } else {
                        redditNews = retrievedNews
                        (news_list.adapter as NewsAdapter).addNews(redditNews!!.news)
                    }
                    type = idle
                }, { error ->
                    if (type==refresh){
                        news_refresh.isRefreshing = false
                    }
                    (news_list.adapter as NewsAdapter).showFail()
                    infiniteScrollListen!!.netError = true
                    showSnackbar(news_list, error.message ?: "")
                    type = idle
                })
        subscriptions.add(subscribe)
    }
}