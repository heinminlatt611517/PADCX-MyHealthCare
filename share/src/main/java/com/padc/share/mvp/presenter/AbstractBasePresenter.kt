package com.padc.share.mvp.presenter

import androidx.lifecycle.ViewModel
import com.padc.share.mvp.view.BaseView

abstract class AbstractBasePresenter <T :  BaseView> : BasePresenter<T>,ViewModel(){
    var mView : T? = null

    override fun initPresenter(view: T) {
        mView = view
    }
}