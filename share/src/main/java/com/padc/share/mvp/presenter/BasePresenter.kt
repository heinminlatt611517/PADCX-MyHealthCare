package com.padc.share.mvp.presenter

import com.padc.share.mvp.view.BaseView

interface BasePresenter<T : BaseView>{
    fun initPresenter(view : T)
}