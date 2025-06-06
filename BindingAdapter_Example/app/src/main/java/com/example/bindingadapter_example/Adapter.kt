package com.example.bindingadapter_example

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
@BindingAdapter("imageFromUrl")
fun ImageView.imageFromUrl(url:String){
    Glide.with(this)
        .load(url)
        .centerCrop()
        .into(this)

}


