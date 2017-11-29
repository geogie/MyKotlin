package com.georgeren.mykotlin.commons

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by georgeRen on 2017/11/28.
 */
class InfiniteScrollListener(val func: () -> Unit,
                             var layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {
    var previousTotal = 0// 之前的总数
    private var loading = true// 可以加载吗：true可以加载，
    var netError = false;
    private val visibleThreshold = 2
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0// item总数

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {
            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if (loading) {
                if (totalItemCount > previousTotal || netError) {
                    netError = false
                    loading = false
                    previousTotal = totalItemCount
                }
            }
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE && !loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            func()
            loading = true
        }
    }
}