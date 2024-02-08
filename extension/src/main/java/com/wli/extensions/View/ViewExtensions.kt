package com.wli.extensions.View

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import androidx.core.view.*

//set view as visible
fun View.visible() {
    this.visibility = View.VISIBLE
}

//set view as gone
fun View.gone() {
    this.visibility = View.GONE
}

//set view as invisible
fun View.invisible() {
    this.visibility = View.INVISIBLE
}

//resize view with input height & width
fun View.resize(width: Int, height: Int) {
    val params = layoutParams
    params?.let {
        params.width = width
        params.height = height
        layoutParams = params
    }
}

/**
 * INVISIBLE TO GONE AND OTHERWISE
 */
fun View.toggleVisibilityGoneToVisible(): View {
    visibility = if (visibility == View.VISIBLE) {
        View.GONE
    } else {
        View.VISIBLE
    }
    return this
}

/**
 *  View as bitmap.
 */
fun View.getBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    draw(canvas)
    canvas.save()
    return bitmap
}

/**
 * Check view visibility
 */
val View.isVisible: Boolean
    get() {
        return this.visibility == View.VISIBLE
    }

val View.isGone: Boolean
    get() {
        return this.visibility == View.GONE
    }

val View.isInvisible: Boolean
    get() {
        return this.visibility == View.INVISIBLE
    }

/**
 * Execute block if view is visible
 */
inline fun View.ifVisible(action: () -> Unit) {
    if (isVisible) {
        action()
    }
}

/**
 * Sets right margin for views
 */
fun View.endMargin(size: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams?
    params?.apply {
        marginEnd = size
    }
    this.layoutParams = params

}

/**
 * Sets bottom margin for views
 */
fun View.bottomMargin(size: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams?
    params?.apply {
        setMargins(marginLeft, marginTop, marginRight, size)
    }
    this.layoutParams = params

}

/**
 * Sets top margin for views
 */
fun View.topMargin(size: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams?
    params?.apply {
        setMargins(marginLeft, size, marginRight, marginBottom)
    }
    this.layoutParams = params

}

/**
 * Sets top margin for views
 */
fun View.startMargin(size: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams?
    params?.apply {
        marginStart = size
    }
    this.layoutParams = params
}

/**
 * Sets an on click listener for a view, but ensures the action cannot be triggered more often than [coolDown] milliseconds.
 */
inline fun View.setOnClickListenerCoolDown(coolDown: Long = 1000L, crossinline action: (view: View) -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        var lastTime = 0L
        override fun onClick(v: View) {
            val now = System.currentTimeMillis()
            if (now - lastTime > coolDown) {
                action(v)
                lastTime = now
            }
        }
    })
}

/**
* Post functions
*/
inline fun <T : View> T.postDelayedLet(delay: Long, crossinline block: (T) -> Unit) {
    postDelayed({ block(this) }, delay)
}

fun View.getLocationOnScreen() = IntArray(2).apply { getLocationOnScreen(this) }


