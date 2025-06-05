package com.example.livedata_example

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    private val factsTextView: TextView
         get() = findViewById(R.id.textView)

    private val btnUpdate: TextView
        get()=findViewById(R.id.btnUpate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.factsLiveData.observe(this, Observer {
            factsTextView.text = it

        })
        btnUpdate.setOnClickListener {
            mainViewModel.updateLiveData()
        }

    }
}