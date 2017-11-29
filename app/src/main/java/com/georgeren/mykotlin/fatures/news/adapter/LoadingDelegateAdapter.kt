package com.georgeren.mykotlin.fatures.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.georgeren.mykotlin.R
import com.georgeren.mykotlin.commons.adapter.ViewType
import com.georgeren.mykotlin.commons.adapter.ViewTypeDelegateAdapter
import com.georgeren.mykotlin.commons.extensions.inflate

/**
 * Created by georgeRen on 2017/11/28.
 */
class LoadingDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = TurnsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.news_item_loading))
}