package com.example.galleryviewerapp.presemtation.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

fun showLog(tag: String = "Shahbaz12345", message: Any) = Log.d(tag, message.toString())

fun Context.showToast(message: Any) =
    Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show()

fun Context.loadImages(fileUri: Uri, imageView: ImageView) {
    Glide.with(this).load(fileUri).centerCrop().into(imageView)
}

fun View.visible(isVisible: Boolean = true) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.gone() {
    visibility =  View.GONE
}

fun View.invisible() {
    visibility =  View.INVISIBLE
}
