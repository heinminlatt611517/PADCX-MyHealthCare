package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls

import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.GeneralQuestionPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.GeneralQuestionView
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.models.impls.DoctorModelImpl
import com.padc.share.data.vos.ChatMessageVO
import com.padc.share.data.vos.RelatedQuestionVO
import com.padc.share.data.vos.SenderTypeVO
import com.padc.share.mvp.presenter.AbstractBasePresenter
import com.padc.share.utils.DateUtils
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*

class GeneralQuestionPresenterImpl : GeneralQuestionPresenter,AbstractBasePresenter<GeneralQuestionView>() {

    private val mDoctorModel : DoctorModel = DoctorModelImpl
    private var Id = ""


    override fun onUiReady(lifecycleOwner: LifecycleOwner, speciality: String,consultationID : String) {
        Id = consultationID

       mDoctorModel.getRelatedQuestionBySpeciality(speciality,onSuccess = {
           mView?.displayGeneralQuestionLists(it)
       },onFailure = {})

    }

    override fun onTapGeneralQuestion(question: String) {

        val message =  ChatMessageVO(
            UUID.randomUUID().toString(),
            DateUtils().getCurrentDateTime(),question, "",
            SenderTypeVO(
                UUID.randomUUID().toString(),
                "doctor", ""
            ))
        mDoctorModel.sendMessage(Id, message, onSuccess = {
        }, onFailure = {})

        mView?.navigateToChatScreen()

    }


}