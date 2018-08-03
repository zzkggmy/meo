package com.kai.meowallpaper.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.kai.meowallpaper.R
import com.kai.meowallpaper.fragment.CategoriesFragment
import com.kai.meowallpaper.fragment.MainFragment
import com.kai.meowallpaper.utils.StatusBarUtil
import com.kai.meowallpaper.utils.setToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val list: ArrayList<Fragment> = ArrayList()
    private val mainFragment = MainFragment()
    private val categoriesFragment = CategoriesFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.zhihu_primary))
        list.run {
            add(mainFragment)
            add(categoriesFragment)
        }
        supportFragmentManager.beginTransaction()
                .add(R.id.fl_main, mainFragment).add(R.id.fl_main, categoriesFragment)
                .show(mainFragment).hide(categoriesFragment).commit()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun showFrag(position: Int) {
        list.forEachIndexed { index, fragment ->
            if (index == position) {
                supportFragmentManager.beginTransaction().show(fragment).commitNow()
            } else {
                supportFragmentManager.beginTransaction().hide(fragment).commitNow()
            }
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                setToken("123")
                showFrag(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_categories -> {
                showFrag(1)
                return@OnNavigationItemSelectedListener true
            }
//            R.id.nav_hot_search -> {
//
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.nav_mine -> {
//                Toast.makeText(this, getToken, Toast.LENGTH_SHORT).show()
//                return@OnNavigationItemSelectedListener true
//            }
        }
        false
    }

}
