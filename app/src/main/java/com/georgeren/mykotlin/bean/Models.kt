package com.georgeren.mykotlin.bean

import android.os.Parcel
import android.os.Parcelable
import com.georgeren.mykotlin.commons.adapter.AdapterConstants
import com.georgeren.mykotlin.commons.adapter.ViewType

/**
 * Created by georgeRen on 2017/11/27.
 */
data class RedditNews(
        val after: String,// 请求参数
        val before: String,
        val news: List<RedditNewsItem> // 请求后的数据列表
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(RedditNewsItem.CREATOR)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(after)
        parcel.writeString(before)
        parcel.writeTypedList(news)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RedditNews> = object : Parcelable.Creator<RedditNews> {
            override fun createFromParcel(parcel: Parcel): RedditNews = RedditNews(parcel)

            override fun newArray(size: Int): Array<RedditNews?> = arrayOfNulls(size)
        }
    }
}

data class RedditNewsItem(
        val author: String,
        val title: String,
        val numComments: Int,
        val created: Long,
        val thumbnail: String,
        val url: String?
) : ViewType, Parcelable {
    override fun getViewType() = AdapterConstants.NEWS

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeInt(numComments)
        parcel.writeLong(created)
        parcel.writeString(thumbnail)
        parcel.writeString(url)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RedditNewsItem> = object : Parcelable.Creator<RedditNewsItem> {
            override fun createFromParcel(parcel: Parcel): RedditNewsItem = RedditNewsItem(parcel)
            override fun newArray(size: Int): Array<RedditNewsItem?> = arrayOfNulls(size)
        }
    }
}