package com.georgeren.mykotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.georgeren.mykotlin.fatures.news.NewsFragment

/**
 * extends 和 implement 替换为 :
 * fun 函数  类型void--》Unit
 * 变量var：推断
 * 常量val：推断
 * kotlin中都是对象
 * 安全调用：xx?
 * 参考：http://caimuhao.com/2017/11/03/Learn-Kotlin-While-Developing-An-Android-App-Part2/
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?): Unit {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeFragment(NewsFragment())
    }

    fun changeFragment(f: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_container, f)
        ft.commit()
    }
}
