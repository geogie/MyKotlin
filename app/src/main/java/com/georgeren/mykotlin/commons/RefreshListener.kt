package com.georgeren.mykotlin.commons

import android.support.v4.widget.SwipeRefreshLayout

/**
 * Created by georgeRen on 2017/11/29.
 */
class RefreshListener(val func: () -> Unit) : SwipeRefreshLayout.OnRefreshListener{
    override fun onRefresh() {
        func()
    }
}