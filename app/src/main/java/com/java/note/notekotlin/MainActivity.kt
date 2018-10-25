package com.java.note.notekotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.java.note.notekotlin.fragment.SplashFragment

class MainActivity : AppCompatActivity() {

    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager
        runSplash()
    }

    fun runSplash() {
        val splashFragment = SplashFragment()

        fragmentManager
            .beginTransaction()
            .replace(R.id.container, splashFragment)
            .addToBackStack(null)
            .commit()

    }
}
