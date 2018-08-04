package com.kai.meo.view

import android.app.ProgressDialog
import android.content.Context
import com.kai.meo.utils.Common
import com.kai.meowallpaper.R

class LoadingProgress(context: Context) {
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(context) }

    fun showLoding() {
        progressDialog.setMessage(Common.context.getString(R.string.loading))
        progressDialog.show()
    }

    fun dismissLoding() {
        progressDialog.dismiss()
    }
}
