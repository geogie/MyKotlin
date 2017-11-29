package com.georgeren.mykotlin.commons.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.ViewParent

/**
 * Created by georgeRen on 2017/11/28.
 */
interface ViewTypeDelegateAdapter {

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}