package com.wli.extensions.fragment

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*

/**
 * Fragments related extensions
 */

/**
 * Replace current fragment with incoming fragment
 */
fun FragmentActivity.replace(@IdRes layoutId: Int, fragment: Fragment, shouldAddToBackStack: Boolean = false) =
    supportFragmentManager.commit {
        if (shouldAddToBackStack) {
            addToBackStack(fragment::class.simpleName)
        }
        replace(layoutId, fragment, fragment::class.simpleName)
    }

/**
 * Add incoming fragment with option of backstack
 */
fun FragmentActivity.add(@IdRes layoutId: Int, fragment: Fragment, shouldAddToBackStack: Boolean = false) =
    supportFragmentManager.commit {
        if (shouldAddToBackStack) {
            addToBackStack(fragment::class.simpleName)
        }
        add(layoutId, fragment, fragment::class.simpleName)
    }

/**
 * Remove all the fragment which are in stack
 */
fun FragmentManager.removeAll() {
    val frg = getFragmentManagerFragments()
    if (frg.isEmpty()) return

    val ft = this.beginTransaction()
    for (fragment in frg) {
        ft.remove(fragment)
    }
    ft.commit()
}

fun FragmentManager.getFragmentManagerFragments(): List<Fragment> {
    return this.fragments
}

/**
 * Remove provided fragment
 */
fun FragmentManager.remove(vararg removeFragment: Fragment) {
    val ft = this.beginTransaction()
    for (fragment in removeFragment) {
        ft.remove(fragment)
    }
    ft.commit()
}

/**
 * Listen the fragment's invocation with setFragmentResult function and provided requestKey
 */
inline fun Fragment.fragmentStringResult(
    requestKey: String,
    bundleKey: String,
    defaultValue: String? = null,
    crossinline action: (String?) -> Unit
) {
    setFragmentResultListener(requestKey) { _, bundle ->
        action(bundle.getString(bundleKey, defaultValue))
    }
}

inline fun Fragment.fragmentBooleanResult(
    requestKey: String,
    bundleKey: String,
    defaultValue: Boolean = false,
    crossinline action: (predicate: Boolean) -> Unit
) {
    setFragmentResultListener(requestKey) { _, bundle ->
        action(bundle.getBoolean(bundleKey, defaultValue))
    }
}

/**
 * showing provided dialog
 */
fun FragmentActivity.showLoadingDialog(dialog: DialogFragment) {
    if (!dialog.isAdded) {
        dialog.show(supportFragmentManager, dialog::class.simpleName)
    }
}

fun Fragment.finish() {
    requireActivity().finish()
}

/**
 * Pop current fragment and navigate to previous
 */
fun FragmentActivity.popFragment() {
    val fm = supportFragmentManager
    if (fm.backStackEntryCount == 0) return
    fm.popBackStack()
}

/**
 * Do action if fragment is visible
 */
fun Fragment.ifIsVisibleAction(action: () -> Unit = {}) {
    if (isVisible) action()
}

/**
 * Clear all the fragment from stack
 */
fun AppCompatActivity.clearAllFragments() = supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

/**
 * Checking the provided fragment is on top
 */
fun FragmentActivity.isAtTheTop(fragment: Fragment): Boolean = supportFragmentManager.fragments.last() == fragment

/**
 * get top fragment's name
 */
fun FragmentManager.getTopFragment(): Fragment? {
    val frg = getFragmentManagerFragments()
    return frg.ifEmpty { return null }[frg.size - 1]
}