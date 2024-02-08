package com.wli.test.utils

import android.util.Log
import com.wli.test.BuildConfig

/**
 * Utility class for logging
 */
object Logger {
    /**
     * @param msg The message you would like logged.
     */
    fun v(tag: String?, msg: String?) {
        if (isDebugEnabled) Log.v(tag, msg!!)
    }

    /**
     * @param msg The message you would like logged.
     */
    fun d(tag: String?, msg: String?) {
        if (isDebugEnabled) Log.d(tag, msg!!)
    }

    /**
     * @param msg The message you would like logged.
     */
    fun i(tag: String?, msg: String?) {
        if (isDebugEnabled) Log.i(tag, msg!!)
    }

    /**
     * @param msg The message you would like logged.
     */
    fun w(tag: String?, msg: String?) {
        if (isDebugEnabled) Log.w(tag, msg!!)
    }

    /**
     * @param msg The message you would like logged.
     */
    fun e(tag: String?, msg: String?) {
        if (isDebugEnabled) Log.e(tag, msg!!)
    }

    private val isDebugEnabled: Boolean
        get() = BuildConfig.DEBUG

    fun largeLog(tag: String?, content: String) {
        if (content.length > 4000) {
            Log.d(tag, content.substring(0, 4000))
            largeLog(tag, content.substring(4000))
        } else {
            Log.d(tag, content)
        }
    }
}