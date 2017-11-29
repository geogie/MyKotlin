package com.georgeren.mykotlin.commons

import android.support.v4.app.Fragment
import rx.subscriptions.CompositeSubscription

/**
 * Created by georgeRen on 2017/11/27.
 */
open class BaseFragment : Fragment() {
    protected var subscriptions = CompositeSubscription()// 异步处理

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeSubscription()
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }
}