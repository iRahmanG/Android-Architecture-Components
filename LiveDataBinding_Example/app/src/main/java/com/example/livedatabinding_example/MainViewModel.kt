package com.example.livedatabinding_example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {
    val qouteLiveData =MutableLiveData("What you give is what you get")

    fun updateQuote() {

        qouteLiveData.value ="You'll see it when you believe it."
    }

}