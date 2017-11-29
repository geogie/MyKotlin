package com.georgeren.mykotlin.fatures.news

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.georgeren.mykotlin.bean.RedditNewsItem
import com.georgeren.mykotlin.commons.adapter.AdapterConstants
import com.georgeren.mykotlin.commons.adapter.ViewType
import com.georgeren.mykotlin.commons.adapter.ViewTypeDelegateAdapter
import com.georgeren.mykotlin.fatures.news.adapter.FailDelegateAdapter
import com.georgeren.mykotlin.fatures.news.adapter.LoadingDelegateAdapter
import com.georgeren.mykotlin.fatures.news.adapter.NewsDelegateAdapter

/**
 * Created by georgeRen on 2017/11/28.
 */
class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    private val loadingItem = object : ViewType {
        override fun getViewType() = AdapterConstants.LOADING
    }

    private val failItem = object : ViewType {
        override fun getViewType() = AdapterConstants.FAIL
    }

    init {
        delegateAdapters.put(AdapterConstants.LOADING, LoadingDelegateAdapter())// 加载中
        delegateAdapters.put(AdapterConstants.FAIL, FailDelegateAdapter())// 加载失败
        delegateAdapters.put(AdapterConstants.NEWS, NewsDelegateAdapter())// item列表
        items = ArrayList()
        items.add(loadingItem)
    }

    /**
     * 添加新数据
     */
    fun addNews(news: List<RedditNewsItem>) {
        var beforeSize = items.size - 1
        items.removeAt(beforeSize)
        notifyItemRemoved(beforeSize)

        items.addAll(news)
        items.add(loadingItem)
        notifyItemRangeChanged(beforeSize, items.size + 1)
    }

    fun showFail() {
        var beforeSize = items.size - 1
        items.removeAt(beforeSize)

        items.add(failItem)
        notifyItemRangeChanged(beforeSize, 1)
    }

    fun showLoading(){
        if (getItemViewType(items.size-1) == AdapterConstants.FAIL){
            var beforeSize = items.size - 1
            items.removeAt(beforeSize)

            items.add(loadingItem)
            notifyItemRangeChanged(beforeSize, 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items.get(position).getViewType()

    fun getNews(): List<RedditNewsItem> {
        return items.filter {
            it.getViewType() == AdapterConstants.NEWS
        }.map {
            it as RedditNewsItem
        }
    }

    fun clearAndAddNews(news: List<RedditNewsItem>) {
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())

        items.addAll(news)
        items.add(loadingItem)
//        notifyItemRangeInserted(0, items.size)
//        notifyItemRangeChanged(0,items.size-1)
        notifyDataSetChanged()
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex

}