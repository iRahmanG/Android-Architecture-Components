package com.example.lifecyleaware

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class Observer : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.d("MAIN", "Observer-ON CREATE")
    }

    // You can override other lifecycle methods as needed:
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d("MAIN", "Observer-ON START")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.d("MAIN", "Observer-ON RESUME")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d("MAIN", "Observer-ON PAUSE")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d("MAIN", "Observer-ON STOP")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.d("MAIN", "Observer-ON DESTROY")
    }
}