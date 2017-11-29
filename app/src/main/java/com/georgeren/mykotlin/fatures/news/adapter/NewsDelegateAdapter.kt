package com.georgeren.mykotlin.fatures.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.georgeren.mykotlin.R
import com.georgeren.mykotlin.bean.RedditNewsItem
import com.georgeren.mykotlin.commons.adapter.ViewType
import com.georgeren.mykotlin.commons.adapter.ViewTypeDelegateAdapter
import com.georgeren.mykotlin.commons.extensions.getFriendlyTime
import com.georgeren.mykotlin.commons.extensions.inflate
import com.georgeren.mykotlin.commons.extensions.loadImg
import kotlinx.android.synthetic.main.news_item.view.*

/**
 * Created by georgeRen on 2017/11/28.
 */
class NewsDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = TurnsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TurnsViewHolder//重命名，解决名字或包冲突
        holder.bind(item as RedditNewsItem)
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.news_item)) {
        private val img_thumbnail = itemView.img_thumbnail
        private val description = itemView.description
        private val author = itemView.author
        private val comments = itemView.comments
        private val time = itemView.time

        fun bind(item: RedditNewsItem) {
            description.text = item.title
            author.text = item.author
            img_thumbnail.loadImg(item.thumbnail)
            comments.text = "${item.numComments} comments"
            time.text = item.created.getFriendlyTime()
        }
    }
}