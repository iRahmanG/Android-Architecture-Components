package com.example.lifecyleaware

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Create a single instance of your Observer
    private val lifecycleObserver = Observer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Add the observer once in onCreate
        lifecycle.addObserver(lifecycleObserver)
        Log.d("MAIN", "Activity- OnCreate")
        // The Observer's onCreate will be called
    }

    override fun onStart() {
        super.onStart()
        Log.d("MAIN", "Activity- OnStart")
        // The Observer's onStart will be called (if added in onCreate)
    }

    override fun onResume() {
        super.onResume()
        Log.d("MAIN", "Activity- OnResume")
        // The Observer's onResume will be called (if added in onCreate)
    }

    override fun onPause() {
        super.onPause()
        Log.d("MAIN", "Activity- OnPause")
        // The Observer's onPause will be called (if added in onCreate)
    }

    override fun onStop() {
        super.onStop()
        Log.d("MAIN", "Activity- OnStop")
        // The Observer's onStop will be called (if added in onCreate)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove the same observer instance in onDestroy
        lifecycle.removeObserver(lifecycleObserver)
        Log.d("MAIN", "Activity- OnDestroy")
        // The Observer's onDestroy will be called before being removed
    }
}