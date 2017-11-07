package com.chikeandroid.eventbuslivedatademo

import android.app.IntentService
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.os.SystemClock

/**
 * Created by Chike on 11/6/2017.
 */
class MyIntentService: IntentService("MyIntentService") {

    companion object {
        var BUS = MutableLiveData<CustomEvent>()
    }

    override fun onHandleIntent(intent: Intent?) {

        // do download or any work
        SystemClock.sleep(3000)

        // assuming download complete
        val event = CustomEvent("value")

        if (BUS.hasActiveObservers()) {
            BUS.postValue(event)
        } else {
           // show notification
        }
    }
}