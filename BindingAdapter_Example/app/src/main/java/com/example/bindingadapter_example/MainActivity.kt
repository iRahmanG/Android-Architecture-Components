package com.example.bindingadapter_example

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.bindingadapter_example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)

        val post = Post("Introduction to Android Architecture Components"
            ,"CheezyCode",
            url = "https://gratisography.com/wp-content/uploads/2024/11/gratisography-augmented-reality-1170x780.jpg")
        binding.post = post
    }
}