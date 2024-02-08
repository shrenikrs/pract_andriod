package com.wli.test.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.wli.test.R


@BindingAdapter("bind:date")
fun TextView.convertDate(dateTime: String) {
    //this.text = CommonUtils.changeDateFormat(dateTime, Constants.DATE_TIME_FORMAT_SERVER, Constants.DATE_TIME_FORMAT_DISPLAY)
}

/**
 * Sample of concatenation of two string params
 */
@BindingAdapter("bind:city", "bind:country")
fun TextView.concateCityCountry(city: String, country: String) {
    this.text = city.plus(", ").plus(country)
}

/**
 * Load image right from your data class
 */
@BindingAdapter("bind:imageUrl")
fun ImageView.loadImageFromServer(url: String) {
    this.post {
        if (isValidContext(context)) {
            try {
                context?.let {
                    Glide.with(context)
                        .load(url)/*.apply(RequestOptions().circleCrop())*/
                        .error(R.drawable.ic_player_avatar)
                        .fallback(R.drawable.ic_player_avatar)
                        .into(this)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

fun getBitmapFromView(view: ImageView): Bitmap? {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun ImageView.loadImageFromGlide(url: String?) {
    if(url!=null) {
        Glide.with(this)
            .load(url)
            .error(R.drawable.ic_launcher_background)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }

}