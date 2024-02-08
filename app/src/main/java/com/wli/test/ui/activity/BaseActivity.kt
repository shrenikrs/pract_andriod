package com.wli.test.ui.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.wli.test.R
import com.wli.test.data.database.SampleDatabase
import com.wli.test.interfaces.CommonAlertCallbackInterface

@SuppressLint("Registered")
open class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var mBinding: T
    protected lateinit var mDatabase: SampleDatabase
    private lateinit var progressDialog: Dialog
    private lateinit var mAlertDialog: AlertDialog

    /**
     * Common method to perform initial stuffs
     * setting content view to activity, initialize binding object
     * initialize database object to use activity wide
     *
     * @param layoutId
     */
    protected fun bindView(layoutId: Int) {
        mBinding = DataBindingUtil.setContentView(this, layoutId)
        //mDatabase = SampleDatabase.getInstance(this)!!
    }

    /**
     * Common method to show loading indication while performing long operation or network calls
     *
     * @param context   activity or fragment activity context
     */
    fun showLoader(context: Context) {
        if (::progressDialog.isInitialized) {
            if (progressDialog.isShowing)
                progressDialog.dismiss()
        }
        progressDialog = Dialog(context)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.dialog_loading)
        progressDialog.window?.setDimAmount(0.75f)
        progressDialog.show()

    }

    /**
     * Common method to dismiss loading indication
     */
    fun dismissLoader() {
        if (::progressDialog.isInitialized)
            progressDialog.dismiss()
    }

    /**
     * Reusable system alert method to show custom message with only positive button
     *
     * @param title             string resource id or string or null
     * @param message           string resource id or string or null
     * @param isCancelable      true or false
     * @param positiveButton    string resource id or string or null
     * @param negativeButton    string resource id or string or null
     * @param neutralButton     string resource id or string or null
     * @param callback          DialogInterface.OnClickListener or CommonAlertCallbackInterface or null
     */
    fun showSystemDefaultAlertDialog(
        @Nullable title: Any?,
        @Nullable message: Any?,
        isCancelable: Boolean?,
        @Nullable positiveButton: Any?,
        @Nullable negativeButton: Any?,
        @Nullable neutralButton: Any?,
        @Nullable callback: Any?
    ) {
        val alertDialog = AlertDialog.Builder(this@BaseActivity)

        if (title != null) {
            alertDialog.setTitle(
                if (title is Int)
                    getString(title)
                else if (title is String)
                    title
                else
                    ""
            )
        }

        if (message != null) {
            alertDialog.setMessage(
                if (message is Int)
                    getString(message)
                else if (message is String)
                    message
                else
                    ""
            )
        }
        alertDialog.setCancelable(isCancelable!!)

        if (positiveButton != null) {
            alertDialog.setPositiveButton(
                if (positiveButton is Int)
                    getString(positiveButton)
                else if (positiveButton is String)
                    positiveButton
                else
                    ""
            ) { dialog, which ->
                setCallbackForSystemDefaultAlertDialog(callback, dialog, which)
            }
        }

        if (negativeButton != null) {
            alertDialog.setNegativeButton(
                if (negativeButton is Int)
                    getString(negativeButton)
                else if (negativeButton is String)
                    negativeButton
                else
                    ""
            ) { dialog, which ->
                setCallbackForSystemDefaultAlertDialog(callback, dialog, which)
            }
        }

        if (neutralButton != null) {
            alertDialog.setNeutralButton(
                if (neutralButton is Int)
                    getString(neutralButton)
                else if (neutralButton is String)
                    neutralButton
                else
                    ""
            ) { dialog, which ->
                setCallbackForSystemDefaultAlertDialog(callback, dialog, which)
            }
        }

        mAlertDialog = alertDialog.create()
        mAlertDialog.show()
    }

    /**
     * set call back for
     * @Link BaseActivity.showSystemDefaultAlertDialog
     */
    private fun setCallbackForSystemDefaultAlertDialog(
        callback: Any?,
        dialog: DialogInterface,
        which: Int
    ) {
        if (callback != null) {
            if (callback is DialogInterface.OnClickListener) {
                callback.onClick(dialog, which)
            } else if (callback is CommonAlertCallbackInterface) {
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> callback.onPositiveButtonClicked()
                    DialogInterface.BUTTON_NEGATIVE -> callback.onNegativeButtonClicked()
                    DialogInterface.BUTTON_NEUTRAL -> callback.onNeutralButtonClicked()
                }
            }
        }
    }

    /**
     * make sure to dispose dialogs and other heavy objects before destroying activity
     * it will help to prevent window leaks, memory leaks etc..
     */
    override fun onStop() {
        super.onStop()
        if (::progressDialog.isInitialized) {
            if (progressDialog.isShowing)
                progressDialog.dismiss()
        }
        if (::mAlertDialog.isInitialized) {
            if (mAlertDialog.isShowing)
                mAlertDialog.dismiss()
        }
    }

}