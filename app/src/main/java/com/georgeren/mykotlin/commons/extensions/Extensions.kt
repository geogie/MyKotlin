@file:JvmName("ExtensionsUtils")

package com.georgeren.mykotlin.commons.extensions

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.georgeren.mykotlin.R
import com.squareup.picasso.Picasso

/**
 * Created by georgeRen on 2017/11/28.
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImg(imageUrl: String) {
    if (TextUtils.isEmpty(imageUrl)) {
        Picasso.with(context).load(R.mipmap.ic_launcher).into(this)
    } else {
        Picasso.with(context).load(imageUrl).into(this)
    }
}

fun Fragment.showSnackbar(view: View, message: String, duration: Int= Snackbar.LENGTH_SHORT){
    Snackbar.make(view,message,duration).show()
}