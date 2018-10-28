package com.java.note.notekotlin

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.java.note.notekotlin.adapter.TabAdapter
import com.java.note.notekotlin.fragment.SplashFragment
import com.java.note.notekotlin.model.Item
import com.java.note.notekotlin.model.ModelTask
import com.java.note.notekotlin.newtask.NewTaskActivity
import com.java.note.notekotlin.utils.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppPreferences.init(this)

        fragmentManager = supportFragmentManager
        runSplash()

        setUI()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val splashItem = menu.findItem(R.id.menuActionSplash)
        splashItem?.isChecked = AppPreferences.firstRun

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
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
                .replace(R.id.contentFrame, splashFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setUI() {

        toolbar.setTitleTextColor(getToolbarTitleColor(this))
        setSupportActionBar(toolbar)

        tabLayout.addTab(tabLayout.newTab().setText(R.string.current_task))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.done_task))

        val tabAdapter = TabAdapter(fragmentManager, 2)
        viewPager.adapter = tabAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabReselected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
        })

        fab.setOnClickListener {
            val intent = Intent(this, NewTaskActivity::class.java)
            startActivityForResult(intent, RequestCode.REQUEST_CODE_NEW_TASK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> when (requestCode) {
                1 -> {
                    if(data != null) {
                        val date: ModelTask = data.getParcelableExtra(ModelTaskConstants.TASK)
                    }
                }
            }
            Activity.RESULT_CANCELED -> toast(this, "Result Cancel")
        }
    }
}
