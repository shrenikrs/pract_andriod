package com.wli.extensions.context

import android.app.ActivityManager
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.BoolRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * Returns true if application is in Foreground
 */
fun Context.isAppInForeground(): Boolean {
    val am = this.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager ?: return false
    val info = am.runningAppProcesses
    if (info.isNullOrEmpty()) return false
    for (aInfo in info) {
        if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            return aInfo.processName == this.packageName
        }
    }
    return false
}

fun Context.color(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)
fun Context.boolean(@BoolRes id: Int): Boolean = resources.getBoolean(id)
fun Context.drawable(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(this, id)
fun Context.string(@StringRes resourceId: Int, vararg args: Any?): String = resources.getString(resourceId, *args)

/**
 * Convert dp to px from input
 */
fun Context.dp2px(dpValue: Float): Int {
    return (dpValue * resources.displayMetrics.density + 0.5f).toInt()
}