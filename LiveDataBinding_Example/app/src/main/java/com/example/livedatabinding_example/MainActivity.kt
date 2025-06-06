package com.example.livedatabinding_example

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import com.example.livedatabinding_example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mainViewModel =ViewModelProvider(this).get(MainViewModel::class.java)

        // replaced in layout file using binding adapter
//        mainViewModel.qouteLiveData.observe(this, Observer{
//            binding.txtQuote.text = it
//        })
        binding.lifecycleOwner =this

        // replaced in layout file using onClick attribute
//        binding.btnUpdate.setOnClickListener{
//            mainViewModel.updateQuote()
//        }
        binding.mainViewModel=mainViewModel

    }
}