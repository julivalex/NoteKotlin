package com.java.note.notekotlin

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.view.Menu
import android.view.MenuItem
import com.java.note.notekotlin.adapter.TabAdapter
import com.java.note.notekotlin.database.DbHelper
import com.java.note.notekotlin.extensions.onTabSelect
import com.java.note.notekotlin.extensions.queryTextChange
import com.java.note.notekotlin.fragment.CurrentTaskFragment
import com.java.note.notekotlin.fragment.DoneTaskFragment
import com.java.note.notekotlin.fragment.SplashFragment
import com.java.note.notekotlin.fragment.TaskFragment
import com.java.note.notekotlin.model.ModelTask
import com.java.note.notekotlin.newtask.NewTaskActivity
import com.java.note.notekotlin.utils.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CurrentTaskFragment.OnTaskDoneListener,
    DoneTaskFragment.OnTaskRestoreListener {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var tabAdapter: TabAdapter
    private var currentTaskFragment: TaskFragment? = null
    private var doneTaskFragment: TaskFragment? = null

    lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppPreferences.init(this)
        dbHelper = DbHelper(this)

        fragmentManager = supportFragmentManager
        runSplash()
        setUI()
    }

    override fun onResume() {
        super.onResume()
        MyApplication.activityResumed()
    }

    override fun onPause() {
        super.onPause()
        MyApplication.activityPaused()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val splashItem = menu.findItem(R.id.menuActionSplash)
        splashItem?.isChecked = AppPreferences.firstRun
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
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

        tabAdapter = TabAdapter(fragmentManager, 2)
        viewPager.adapter = tabAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.onTabSelect {
            viewPager.currentItem = it.position
        }

        fab.setOnClickListener {
            val intent = Intent(this, NewTaskActivity::class.java)
            startActivityForResult(intent, RequestCode.REQUEST_CODE_NEW_TASK)
        }

        searchView.queryTextChange {
            currentTaskFragment?.findTasks(it)
            doneTaskFragment?.findTasks(it)
        }

        val currentTask: TaskFragment = tabAdapter.getItem(TabAdapterConst.CURRENT_TASK_FRAGMENT_POSITION)
        currentTaskFragment = currentTask as? CurrentTaskFragment

        val doneTask: TaskFragment = tabAdapter.getItem(TabAdapterConst.DONE_TASK_FRAGMENT_POSITION)
        doneTaskFragment = doneTask as? DoneTaskFragment

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RequestCode.REQUEST_CODE_NEW_TASK -> {
                getResultCodeData(data, resultCode) {
                    onTaskAdded(data)
                }
            }
            RequestCode.REQUEST_CODE_EDIT_TASK -> {
                getResultCodeData(data, resultCode) {
                    onTaskEdited(data)
                }
            }
        }
    }

    private fun onTaskAdded(data: Intent?) {
        if (data != null) {
            val modelTask: ModelTask = data.getParcelableExtra(ModelTaskConst.TASK_BACK)
            currentTaskFragment?.addTask(modelTask, true)
        }
    }

    private fun onTaskEdited(data: Intent?) {
        if (data != null) {
            val updateTask: ModelTask = data.getParcelableExtra(ModelTaskConst.TASK_BACK)
            currentTaskFragment?.updateTask(updateTask)
            dbHelper.updateManager.updateTask(updateTask)
        }
    }

    private inline fun getResultCodeData(data: Intent?, resultCode: Int, useTask: (Intent?) -> Unit) {
        when (resultCode) {
            Activity.RESULT_OK -> useTask(data)
            Activity.RESULT_CANCELED -> onTaskCancel()
        }
    }

    private fun onTaskCancel() {
        toast(this, "Result Cancel")
    }

    override fun onTaskDone(task: ModelTask) {
        doneTaskFragment?.addTask(task, false)
    }

    override fun onTaskRestore(task: ModelTask) {
        currentTaskFragment?.addTask(task, false)
    }

}
