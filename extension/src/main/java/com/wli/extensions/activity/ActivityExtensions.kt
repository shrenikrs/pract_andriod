package com.wli.extensions.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity

/**
 * Hide keyboard for an activity.
 */
fun Activity.hideSoftKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

/**
 * Show keyboard for an activity.
 */
fun Activity.showKeyboard(toFocus: View) {
    toFocus.requestFocus()
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(toFocus, 0)
}

/**
 * Returns the StatusBarHeight in Pixels
 */
val Activity.getStatusBarHeight: Int
    get() {
        val rect = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rect)
        return rect.top
    }

/**
 * Set Status Bar Color
 */
fun Activity.setStatusBarColor(@ColorInt color: Int) {
    window.statusBarColor = color
}

/**
 * Set Navigation Bar Color
 */
fun Activity.setNavigationBarColor(@ColorInt color: Int) {
    window.navigationBarColor = color
}

/**
 * Start new activity with bundle inputs
 */
inline fun <reified T> Activity.startActivityForResult(
    requestCode: Int,
    bundleBuilder: Bundle.() -> Unit = {},
    intentBuilder: Intent.() -> Unit = {}
) {
    val intent = Intent(this, T::class.java)
    intent.intentBuilder()
    val bundle = Bundle()
    bundle.bundleBuilder()
    startActivityForResult(intent, requestCode, if (bundle.isEmpty) null else bundle)
}

inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()

    startActivityForResult(intent, requestCode, options)
}

/**
 * to check if activity isFinishing or not
 */
fun Activity.isActivityFinishing(): Boolean = this.isFinishing || this.isDestroyed

/**
 * passing different values to activity
 */
fun Intent.putExtras(vararg params: Pair<String, Any>): Intent {
    if (params.isEmpty()) return this
    params.forEach { (key, value) ->
        when (value) {
            is Int -> putExtra(key, value)
            is Byte -> putExtra(key, value)
            is Char -> putExtra(key, value)
            is Long -> putExtra(key, value)
            is Float -> putExtra(key, value)
            is Short -> putExtra(key, value)
            is Double -> putExtra(key, value)
            is Boolean -> putExtra(key, value)
            is Bundle -> putExtra(key, value)
            is String -> putExtra(key, value)
        }
    }
    return this
}

/**
 * Set the action bar title
 * @param title String value of the title
 */
fun AppCompatActivity.setToolbarTitle(title: String) {
    supportActionBar?.title = title
}