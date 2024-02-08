package com.wli.extensions.glide

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Load image in view with any source
 */
fun Context.loadImg(
    imgUrl: Any?,
    view: AppCompatImageView,
    error: Drawable?,
    placeHolder: Drawable?,
    diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.NONE
) {
    Glide.with(this)
        .load(imgUrl)
        .placeholder(placeHolder)
        .diskCacheStrategy(diskCacheStrategy)
        .error(error)
        .into(view)
}

/**
 * convert bitmap from uri input
 */
fun Activity.getBitmapFromUri(uri: Uri): Bitmap? {
    return contentResolver.openInputStream(uri)?.use {
        return@use BitmapFactory.decodeStream(it)
    }
}

/**
 * resizing of existing bitmap to new bitmap
 */
fun rescaleBitmap(source: Bitmap, newHeight: Int, newWidth: Int): Bitmap {
    val sourceWidth = source.width
    val sourceHeight = source.height

    val xScale = newWidth.toFloat() / sourceWidth
    val yScale = newHeight.toFloat() / sourceHeight
    val scale = xScale.coerceAtLeast(yScale)

    val scaledWidth = scale * sourceWidth
    val scaledHeight = scale * sourceHeight

    val left = (newWidth - scaledWidth) / 2
    val top = (newHeight - scaledHeight) / 2

    val targetRect = RectF(left, top, left + scaledWidth, top + scaledHeight)

    val dest = Bitmap.createBitmap(newWidth, newHeight, source.config)
    val canvas = Canvas(dest)
    canvas.drawBitmap(source, null, targetRect, null)

    return dest
}