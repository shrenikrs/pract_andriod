package com.wli.test.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Common method to check if list is not null or empty
 */
inline fun <T> List<T>?.withNotNullOrEmpty(action: () -> Unit) {
    if (!this.isNullOrEmpty()) {
        action.invoke()
    }
}

inline fun <T> List<T?>?.withNotNullOrEmpty(action: () -> Unit, nullAction: () -> Unit) {
    if (!this.isNullOrEmpty()) {
        action.invoke()
    } else {
        nullAction.invoke()
    }
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>, liveDataAction: () -> Unit) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            liveDataAction()
            removeObserver(observer)
            removeObserver(this)
        }
    })
}

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }

fun List<String>.equalsList(answer: List<String>): Boolean {

    if (this.size != answer.size) {
        return false
    }

    val one = mutableListOf<String>()
    one.addAll(trimExtraSpaces().toLowerCase())
    val two = mutableListOf<String>()
    two.addAll(answer.trimExtraSpaces().toLowerCase())

    one.sort()
    two.sort()

    return one == two
}

fun String.trimExtraSpaces(): String {
    return this.trim().replace("\\s+".toRegex(), " ")
}

fun List<String>.trimExtraSpaces(): MutableList<String> {
    return this.map {
        it.trimExtraSpaces()
    }.toMutableList()
}

fun List<String>.toLowerCase(): MutableList<String> {
    return this.map {
        it.toLowerCase()
    }.toMutableList()
}

fun String.toStringList(): List<String> = mutableListOf(this)

fun isValidContext(context: Context?): Boolean {
    if (context == null) {
        return false
    } else if (context !is Application) {
        if (context is FragmentActivity) {
            if (context.isDestroyed) {
                return false
            }
        } else if (context is Activity) {
            if (context.isDestroyed) {
                return false
            }
        }
    }
    return true
}
