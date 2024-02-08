package com.wli.extensions.string

import java.util.*

fun <T> T.concatAsString(b: T): String {
    return this.toString() + b.toString()
}

inline fun String.ifIsNullOrEmpty(action: () -> Unit) {
    if (isNullOrEmpty()) action()
}

inline fun String.ifIsNotNullOrEmpty(action: () -> Unit) {
    if (!isNullOrEmpty()) action()
}

fun String.capitalize() = replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }