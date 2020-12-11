package com.padc.patient.mvp.view

import com.padc.share.mvp.view.BaseView


interface ProfileView : BaseView {
    fun onTapSaveUserData()
    fun onTapCancelUserData()
    fun onTapEditProfileImage()
}