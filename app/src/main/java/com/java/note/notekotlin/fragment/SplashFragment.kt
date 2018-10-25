package com.java.note.notekotlin.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.java.note.notekotlin.R
import kotlinx.coroutines.experimental.*
import kotlin.coroutines.experimental.CoroutineContext

class SplashFragment : Fragment(), CoroutineScope {

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        hideSplashScreen()
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun hideSplashScreen() = launch {
        async(Dispatchers.IO) {
            delay(2000L)
        }.await()

        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}