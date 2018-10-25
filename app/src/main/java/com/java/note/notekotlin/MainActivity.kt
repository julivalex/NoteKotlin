package com.java.note.notekotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.Menu
import android.view.MenuItem
import com.java.note.notekotlin.fragment.SplashFragment
import com.java.note.notekotlin.utils.AppPreferences

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppPreferences.init(this)

        fragmentManager = supportFragmentManager
        runSplash()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val splashItem = menu?.findItem(R.id.menuActionSplash)
        splashItem?.isChecked = AppPreferences.firstRun

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.menuActionSplash) {
            item.isChecked = !item.isChecked
            AppPreferences.firstRun = item.isChecked

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun runSplash() {
        //show or don't show splash screen
        if (!AppPreferences.firstRun) {

            val splashFragment = SplashFragment()
            fragmentManager
                .beginTransaction()
                .replace(R.id.container, splashFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
