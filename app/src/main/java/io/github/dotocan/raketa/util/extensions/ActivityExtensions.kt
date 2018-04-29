package io.github.dotocan.raketa.util.extensions

import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import io.github.dotocan.raketa.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by dotocan on 3.3.2018..
 */

fun AppCompatActivity.replaceFragment(layoutId: Int, fragment: Fragment, tag: String = "") {
    supportFragmentManager
            .beginTransaction()
            .replace(layoutId, fragment, tag)
            .commit()
}

fun AppCompatActivity.replaceFragmentAddToBackstack(layoutId: Int, fragment: Fragment,
                                                    tag: String = "") {
    supportFragmentManager
            .beginTransaction()
            .replace(layoutId, fragment, tag)
            .addToBackStack(null)
            .commit()
}

fun AppCompatActivity.findByTag(tag: String): Fragment?
        = supportFragmentManager.findFragmentByTag(tag)

fun AppCompatActivity.setupDrawerAnimationFor(drawerLayout: DrawerLayout, toolbar: Toolbar) {
    val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.open, R.string.close) {
        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            super.onDrawerSlide(drawerView, slideOffset)
            val slideX: Float = drawerView.width * slideOffset
            content_container.translationX = slideX
        }
    }

    drawerLayout.addDrawerListener(actionBarDrawerToggle)
    actionBarDrawerToggle.syncState()

    drawer_layout.setScrimColor(Color.TRANSPARENT)
    drawer_layout.drawerElevation = 0f
}