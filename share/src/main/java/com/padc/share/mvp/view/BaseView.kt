package com.padc.share.mvp.view

import androidx.lifecycle.LifecycleOwner

interface BaseView {
    fun showErrorMessage(errorMessage : String)
    fun getLifeCycleOwner() : LifecycleOwner
}