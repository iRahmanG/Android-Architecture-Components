package com.example.databinding_example

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.databinding_example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        normal method
//        val qouteTextView =findViewById<TextView>(R.id.quoteText)
//        val qouteAuthorTextView =findViewById<TextView>(R.id.quoteAuthor)
//
//        qouteTextView.text="Do or do not. There is no try."
//        qouteAuthorTextView.text="Yoda"

//        //data binding
//        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
//        binding.quoteText.text="Do or do not. There is no try."
//        binding.quoteAuthor.text="Yoda"

        //data binding with object
        val quoteObj=Quote("Do or do not. There is no try.","Yoda")
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.quote=quoteObj

    }
}