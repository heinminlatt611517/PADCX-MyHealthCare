package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.PrescribeMedicinePresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.PrescribeMedicineView
import com.padc.padcx_myhealthcare_monthly_assignment.utils.SessionManager
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.models.impls.DoctorModelImpl
import com.padc.share.data.vos.*
import com.padc.share.mvp.presenter.AbstractBasePresenter
import com.padc.share.utils.prescription
import java.util.*
import kotlin.collections.ArrayList

class PrescribeMedicinePresenterImpl : PrescribeMedicinePresenter,
    AbstractBasePresenter<PrescribeMedicineView>() {

    private val mDoctorModel: DoctorModel = DoctorModelImpl

    private val mPrescriptionLists = MutableLiveData<List<PrescriptionVO>>()



    private var medicineLists: ArrayList<MedicineVO> = arrayListOf()
    private var prescriptionLists: ArrayList<PrescriptionVO> = arrayListOf()

    override fun onUiReady(lifecycleOwner: LifecycleOwner, speciality: String,consultationID: String) {


        mDoctorModel.getConsultationChat(consultationID,onSuccess = {}, onError = {})

       mDoctorModel.getConsultationChatFromDB(consultationID)

        mDoctorModel.getConsultationChatFromDB(consultationID)
                .observe(lifecycleOwner, Observer {
                    it?.let {
                        mView?.displayPatientRequestData(it)
                    }
                })


        mDoctorModel.getMedicineBySpeciality(speciality, onSuccess = {
            medicineLists.addAll(it)
                    mView?.displayMedicineLists(medicineLists)

        }, onFailure = {
            mView?.showErrorMessage(it)
        })




    }


    override fun onTapStopConsultation(consultationVO : ConsultationChatVO) {

        if (prescriptionLists.isNotEmpty()){
            mDoctorModel.finishConsultation(consultationVO, prescriptionLists, onSuccess = {
                mView?.navigateToChatScreen()
            }, onError = {})
        }

    }

    override fun addMedicineCount(data: String) {

    }

    override fun addMedicineType(data: String) {

    }

    override fun addToPrescriptionLists(
        medicine: String,
        amount: String,
        day: String,
        spinnerItem: String,
        comment: String,
        type : String,
        count : String
    ) {
//        val routineLists: ArrayList<RoutineVO> = arrayListOf()
//
//
//        val routineVO = RoutineVO(
//            UUID.randomUUID().toString(),
//            day + spinnerItem,
//            count,
//            type,
//            comment
//        )
//        routineLists.add(routineVO)
//
//       val prescriptionVO =
//            PrescriptionVO(UUID.randomUUID().toString(), 0, 0, medicine, routineLists)
//
//        prescriptionLists.add(prescriptionVO)
//
//        mDoctorModel.getMedicineBySpeciality(SessionManager.doctor_speciality.toString(), onSuccess = {
//
//                medicineLists.forEach {medicineVO ->
//                        if (medicine == medicineVO.name){
//                            medicineVO.isSelect = true
//                        }
//
//                    mView?.displayMedicineLists(medicineLists)
//                }
//        }, onFailure = {
//            mView?.showErrorMessage(it)
//        })

    }

    override fun addToPrescribeMedicineLists(prescriptionVO: PrescriptionVO) {
        prescriptionLists.add(prescriptionVO)


        mDoctorModel.getMedicineBySpeciality(SessionManager.doctor_speciality.toString(), onSuccess = {

            medicineLists.forEach {medicineVO ->
                if (prescriptionVO.medicine == medicineVO.name){
                    medicineVO.isSelect = true
                }

                mView?.displayMedicineLists(medicineLists)
            }
        }, onFailure = {
            mView?.showErrorMessage(it)
        })
    }


    override fun onTapAddMedicine(medicineVO: MedicineVO) {
        mView?.showPrescribeMedicineDialog(medicineVO)
    }

    override fun onTapRemoveMedicine(medicineVO: MedicineVO) {
        for (i in medicineLists) {

            i.name.let { medicineName ->
                if (medicineName.equals(medicineVO.name)) {
                    i.isSelect = false
                    mView?.displayMedicineLists(medicineLists)
                }

            }

        }
    }
}