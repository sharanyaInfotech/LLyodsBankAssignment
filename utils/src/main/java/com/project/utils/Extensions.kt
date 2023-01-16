package com.project.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import coil.Coil
import coil.load
import coil.request.ImageRequest

fun ImageView.loadWithException(
    imageUri: String?,
    placeHolder: Int
) {
    val request = ImageRequest.Builder(context)
        .data(imageUri)
        .target(
            onStart = {
                this.load(placeHolder)
            },
            onSuccess = { result ->
                this.load(result)
            },
            onError = {
                this.load(placeHolder)
            }
        )
        .build()
    Coil.imageLoader(context).enqueue(request)
}

fun View.showOrGone(show: Boolean) {
    visibility = if (show) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun Context.showToast(msg: Any)
{
    Toast.makeText(applicationContext, if (msg is Int) { getString(msg) } else { msg as String }, Toast.LENGTH_SHORT ).show()
}
