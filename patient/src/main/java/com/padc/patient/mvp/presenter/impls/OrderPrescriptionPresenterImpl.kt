package com.padc.patient.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.padc.patient.mvp.presenter.OrderPrescriptionPresenter
import com.padc.patient.mvp.view.OrderPrescriptionView
import com.padc.patient.utils.SessionManager
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.*
import com.padc.share.mvp.presenter.AbstractBasePresenter
import java.util.*
import kotlin.collections.ArrayList

class OrderPrescriptionPresenterImpl : OrderPrescriptionPresenter,AbstractBasePresenter<OrderPrescriptionView>() {

    private val mPatientModel: PatientModel = PatientModelImpl
    private val mPrescriptionDataLists = MutableLiveData<List<PrescriptionVO>>()
    private val mPatientFullAddress = MutableLiveData<String>()

    private val mAddressLists : ArrayList<AddressVO> = arrayListOf()
    lateinit var mOwner: LifecycleOwner
    lateinit var mConsultatioId : String
    override fun onUiReady(lifecycleOwner: LifecycleOwner, patientID: String,consultationID : String) {
        mConsultatioId = consultationID
        mOwner = lifecycleOwner
        mPatientModel.getPatientByEmail(patientID,onSuccess = {
            mAddressLists.addAll(it.address)
                mView?.displayPatientAddress(mAddressLists)

        },onError = {

        })



        mPatientModel.getPrescriptionByID(consultationID,onSuccess = {
            mView?.displayPrescribeMedicineLists(it)
           mPrescriptionDataLists.value = it
        },
        onFailure = {
            mView?.showErrorMessage(it)
        })


    }

    override fun onTapMadePayment(addressLists: List<AddressVO> ) {

        mAddressLists.addAll(addressLists)

        val patientVO = PatientVO(
                SessionManager.patient_id.toString(),
                SessionManager.patient_name.toString(),
                SessionManager.patient_email.toString(),
                SessionManager.patient_device_id,
                SessionManager.patient_photo,
                SessionManager.patient_bloodType,
                SessionManager.patient_bloodPressure,
                mAddressLists,
                SessionManager.patient_weight,
                SessionManager.patient_height,
                SessionManager.patient_dateOfBirth.toString(),
                SessionManager.patient_allegric,
                arrayListOf()

        )

        mPatientModel.registerNewPatient(patientVO = patientVO,onSuccess = {
            mView?.showPaymentDialog()
        },onFailure = {})
    }




    override fun checkOutMedicine(totalPrice : String,prescriptionVO: ArrayList<PrescriptionVO>,consultationID :String) {

        mPatientModel.getConsultationChat(mConsultatioId,onSuccess = {}, onError = {})

        val patientVO = PatientVO(
            SessionManager.patient_id.toString(),
            SessionManager.patient_name.toString(),
            SessionManager.patient_email.toString(),
            SessionManager.patient_device_id,
            SessionManager.patient_photo,
            SessionManager.patient_bloodType,
            SessionManager.patient_bloodPressure,
            mAddressLists,
            SessionManager.patient_weight,
            SessionManager.patient_height,
            SessionManager.patient_dateOfBirth.toString(),
            SessionManager.patient_allegric,
            arrayListOf()
        )

        mPatientModel.getConsultationChatFromDB(mConsultatioId)
            .observe(mOwner, Observer { data ->
                data?.let {

                    val checkOutVO = CheckOutVO(UUID.randomUUID().toString(),"",totalPrice,
                    patientVO,it.doctor_info, DeliveryRoutineVO(),prescriptionVO
                    )

                    mPatientModel.checkoutMedicine(checkOutVO,onSuccess = {
                        mView?.navigateToHomeFragment()
                    },onFailure = {
                        mView?.showErrorMessage(it)
                    })
                }
            })


    }

    override fun getPrescriptionLists(): LiveData<List<PrescriptionVO>> {
        return mPrescriptionDataLists
    }

    override fun getPatientFullAddress(): LiveData<String> {
        return mPatientFullAddress
    }

    override fun onTapAddress(address: AddressVO, previousPosition: Int) {
        mPatientFullAddress.value = address.fullAddress
        mView?.selectedRecyclerAddress(address,previousPosition)

    }
    override fun showEmptyAddressView() {
        mView?.showEmptyAddressView()
    }

    override fun showRecyclerAddressView() {
        mView?.showRecyclerAddressView()
    }


}