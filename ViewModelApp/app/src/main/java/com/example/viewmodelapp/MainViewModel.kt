package com.example.viewmodelapp


import androidx.lifecycle.ViewModel

class MainViewModel(val initilaValue:Int) : ViewModel() {
    var count: Int = initilaValue

    fun incrementCounter() { // independent of view
        count++
    }

}