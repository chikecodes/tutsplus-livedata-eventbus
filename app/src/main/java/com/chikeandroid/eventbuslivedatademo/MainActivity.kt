package com.chikeandroid.eventbuslivedatademo

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var doWorkButton: Button
    private lateinit var resultTextView: TextView

    private val registry = LifecycleRegistry(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        doWorkButton = findViewById(R.id.btn_download)
        doWorkButton.setOnClickListener {
            doWorkButton.isEnabled = false
            resultTextView.visibility = View.INVISIBLE
            val serviceIntent = Intent(this, MyIntentService::class.java)
            startService(serviceIntent)
        }

        resultTextView = findViewById(R.id.tv_result)

        MyIntentService.BUS.observe(
                this,
                Observer { event ->
                    resultTextView.visibility = View.VISIBLE
                    doWorkButton.isEnabled = true
                    Log.d("MainActivity", event?.eventProp)
                })
    }

    override fun getLifecycle() : Lifecycle = registry

    override fun onStart() {
        super.onStart()
        registry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onResume() {
        super.onResume()
        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onPause() {
        super.onPause()
        registry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    override fun onStop() {
        super.onStop()
        registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    override fun onDestroy() {
        super.onDestroy()
        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}
