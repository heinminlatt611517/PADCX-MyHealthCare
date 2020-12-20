package com.padc.padcx_myhealthcare_monthly_assignment.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.adapter.MedicineAdapter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog.PrescribeMedicineDialogFragment
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog.PrescribeMedicineDialogFragment.Companion.BUNDLE_MEDICINE_ID
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog.PrescribeMedicineDialogFragment.Companion.BUNDLE_NAME
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.PrescribeMedicinePresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.PrescribeMedicinePresenterImpl
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.PrescribeMedicineView
import com.padc.padcx_myhealthcare_monthly_assignment.utils.SessionManager
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.*
import com.padc.share.utils.DateUtils
import kotlinx.android.synthetic.main.activity_prescribe_medicine.*
import kotlinx.android.synthetic.main.list_item_medicine.*
import kotlinx.android.synthetic.main.list_item_medicine.view.*
import kotlinx.android.synthetic.main.prescribe_medicine_dialog.*
import kotlinx.android.synthetic.main.prescribe_medicine_dialog.view.*
import kotlinx.android.synthetic.main.prescribe_medicine_dialog.view.ed_day
import kotlinx.android.synthetic.main.prescribe_medicine_routine_dialog.view.*
import java.util.*
import kotlin.collections.ArrayList

class PrescribeMedicineActivity : BaseActivity(), PrescribeMedicineView {

    companion object {
        const val PARAM_CONSULTATION_CHAT_ID = " chat id"
        fun newIntent(context: Context, consultationID: String): Intent {

            val intent = Intent(context, PrescribeMedicineActivity::class.java)
            intent.putExtra(PARAM_CONSULTATION_CHAT_ID, consultationID)
            return intent
        }
    }
    private  var filterlist : ArrayList<MedicineVO> = arrayListOf()
    private lateinit var list : List<MedicineVO>

    private lateinit var mPresenter: PrescribeMedicinePresenter
    private lateinit var mMedicineAdapter: MedicineAdapter

    private var prescriptionLists: ArrayList<PrescriptionVO> = arrayListOf()

    private val medicineCountLists = mutableListOf<String>()
    private val medicineTypeLists = mutableListOf<String>()

    private lateinit var mConsultationChatVO: ConsultationChatVO

    private var routine: String? = null
    private var consultationID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescribe_medicine)

        setUpPresenter()
        setUpRecyclerView()
        setUpActionListener()

        consultationID = intent.getStringExtra(PARAM_CONSULTATION_CHAT_ID)
        mPresenter.onUiReady(this, SessionManager.doctor_speciality.toString(),consultationID.toString())

    }


    private fun setUpActionListener() {

        ed_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }
        })




        btn_prescribeAndStop.setOnClickListener {

            val doctorVO = DoctorVO(
                SessionManager.doctor_id.toString(),
                SessionManager.doctor_name.toString(),
                SessionManager.doctor_email.toString(),
                SessionManager.doctor_photo,
                0,
                SessionManager.doctor_device_id,
                SessionManager.doctor_degree,
                SessionManager.doctor_bigraphy,
                "",
                SessionManager.doctor_phone,
                SessionManager.doctor_speciality

            )
            val consultationChatVO = ConsultationChatVO(
                consultationID.toString(), SessionManager.doctor_id, "",
                true,mConsultationChatVO.patient_id, mConsultationChatVO.patient_info,mConsultationChatVO.doctor_info,
                "",DateUtils().getCurrentDate(),mConsultationChatVO.case_summary
            )

            mPresenter.onTapStopConsultation(mConsultationChatVO)
        }
    }

    fun filter(text : String)
    {
        filterlist.clear()
        list?.let{

            for( item in list)
            {
                var st = item.name.toString().toLowerCase()
                if(st.contains(text))
                {
                    filterlist.add(item)
                }
            }
            mMedicineAdapter.setMedicineList(filterlist)
        }

    }

    private fun setUpRecyclerView() {

        rv_prescribeMedicine?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mMedicineAdapter = MedicineAdapter(mPresenter)
        rv_prescribeMedicine?.adapter = mMedicineAdapter

    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(PrescribeMedicinePresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    override fun displayMedicineLists(lists: List<MedicineVO>) {

        Log.d("medicineLists", lists.size.toString())
        this.list = lists
        mMedicineAdapter.setNewData(lists.toMutableList())
    }


    override fun showText(text: String) {
        Log.d("listsSize", text)
    }

    override fun navigateToSplashScreen() {
        startActivity(SplashScreenActivity.newIntent(this))
        finish()
    }

    override fun displayPatientRequestData(data: ConsultationChatVO) {
        mConsultationChatVO = data
    }



    override fun displayPrescriptionLists(prescriptionVO: PrescriptionVO) {

        Log.d("plists", prescriptionLists.size.toString())
    }

    override fun navigateToChatScreen() {
        startActivity(ChatActivity.newIntent(this,intent.getStringExtra(PARAM_CONSULTATION_CHAT_ID).toString()))
        finish()
    }


    override fun showErrorMessage(errorMessage: String) {
        showSnackbar(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this

    override fun showPrescribeMedicineDialog(medicineVO: MedicineVO) {

        var morningstatus =true
        var afternoonstatus =true
        var nightstatus =true

        var number =1
        var daycount : Int = 0
        var tabcount : String = "1"
        var eatingtime : String =""
        var daystemp : String =""
        var count =0


        val view = layoutInflater.inflate(R.layout.prescribe_medicine_routine_dialog, null)
        val tabAccount = view?.findViewById<TextView>(R.id.tv_tabcount)
        val comment = view?.findViewById<EditText>(R.id.tv_comment)
         

        val dialog = this?.let { Dialog(it) }

        dialog?.apply {
            setCancelable(true)
            setContentView(view)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        view.txt_medicine_name.text  =  medicineVO.name
        tabAccount?.text =  tabcount

        view.tv_morning.setOnClickListener {

            morningstatus = if(morningstatus) {
                view.tv_morning.setBackgroundResource(R.drawable.rounded_corner_bluecolor)
                view.tv_morning.setTextColor(Color.WHITE)
                count++
                false
            }else{
                view.tv_morning.setBackgroundResource(R.drawable.bg_rounded_border_grey)
                view.tv_morning.setTextColor(Color.BLACK)
                count--
                true
            }

            if(count > -1)
            {
                var result = number * daycount * count
                tabAccount?.text = result.toString()
                tabcount = result.toString()
            }
        }

        view.tv_afternoon.setOnClickListener {
            afternoonstatus = if(afternoonstatus) {
                view.tv_afternoon.setBackgroundResource(R.drawable.rounded_corner_bluecolor)
                view.tv_afternoon.setTextColor(Color.WHITE)
                count++
                false
            }else{
                view.tv_afternoon.setBackgroundResource(R.drawable.bg_rounded_border_grey)
                view.tv_afternoon.setTextColor(Color.BLACK)
                count--
                true
            }
            if(count > -1)
            {
                var result = number * daycount * count
                tabAccount?.text = result.toString()
                tabcount = result.toString()
            }
        }

        view.tv_night.setOnClickListener {
            nightstatus = if(nightstatus) {
                view.tv_night.setBackgroundResource(R.drawable.rounded_corner_bluecolor)
                view.tv_night.setTextColor(Color.WHITE)
                count++
                false
            }else{
                view.tv_night.setBackgroundResource(R.drawable.bg_rounded_border_grey)
                view.tv_night.setTextColor(Color.BLACK)
                count--
                true
            }
            if(count > -1)
            {
                var result = number * daycount * count
                tabAccount?.text = result.toString()
                tabcount = result.toString()
            }
        }

        view.tv_before_eating.setOnClickListener {
            view.tv_before_eating.setBackgroundResource(R.drawable.rounded_corner_bluecolor)
            view.tv_before_eating.setTextColor(Color.WHITE)
            view.tv_after_eating.setBackgroundResource(R.drawable.bg_rounded_border_grey)
            view.tv_after_eating.setTextColor(Color.BLACK)
            eatingtime= "အစားမစားမှီသောက်ရန်"
        }

        view.tv_after_eating.setOnClickListener {
            view.tv_after_eating.setBackgroundResource(R.drawable.rounded_corner_bluecolor)
            view.tv_after_eating.setTextColor(Color.WHITE)
            view.tv_before_eating.setBackgroundResource(R.drawable.bg_rounded_border_grey)
            view.tv_before_eating.setTextColor(Color.BLACK)
            eatingtime = "အစားစားပြီးမှ သောက်ရန်"
        }

        view.day_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                var day = parent.getItemAtPosition(position).toString()
                if(day == "Days")
                {
                    daycount =1
                    daystemp= " Days"
                }else{
                    daycount = 7
                    daystemp=" Week"
                }

                var result = number * daycount * count
                tabAccount?.text = result.toString()
                tabcount = result.toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        view.ed_day.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                var data= s.toString()
                if(data.isNotEmpty())
                {
                    number = data.toInt()
                    var result = number * daycount * count
                    view.tv_tabcount.text = result.toString()
                    tabcount = result.toString()
                }
            }
        })



        view.confirm.setOnClickListener {
            // prescription list add
            var days : String =""
            if(morningstatus)
            {
                days += " မနက် ၊ "
            }
            if(afternoonstatus)
            {
                days += "နေ့  ၊  "
            }
            if(nightstatus)
            {
                days += "ည"
            }

            var medicaltime : String =""
            val routineLists: ArrayList<RoutineVO> = arrayListOf()

            var routineVO = RoutineVO(
                id= "0",
                amount = medicineVO.price.toString(),
                days = view.ed_day.text.toString() + daystemp,
                comment = comment?.text.toString(),
                quantity = tabcount,
                times = days,
                repeat = eatingtime
            )
            routineLists.add(routineVO)

            var prescriptionVO = PrescriptionVO(
                id = UUID.randomUUID().toString(),
                count = tabcount,
                price =  medicineVO.price.toString(),
                medicine = medicineVO.name.toString(),
                routineVO= routineLists
            )
            if(comment?.text.toString().isNotEmpty()) {

                    mPresenter.addToPrescribeMedicineLists(prescriptionVO)


                dialog?.dismiss()
            }else{
                Toast.makeText(this,"Please enter all field",Toast.LENGTH_SHORT).show()
            }
        }

        dialog?.show()

    }


    //        Log.d("medicine", medicineVO.name.toString())
//
//        val view = layoutInflater.inflate(R.layout.prescribe_medicine_dialog, null)
//        val dialog = this?.let { Dialog(it) }
//
//        dialog?.apply {
//            setCancelable(false)
//            setContentView(view)
//            window?.setBackgroundDrawableResource(android.R.color.transparent)
//        }
//
//        view.tv_medicine.text = medicineVO.name
//
//        for (index in 0 until view.chip_group_amount.childCount) {
//            val chip: Chip = view.chip_group_amount.getChildAt(index) as Chip
//
//            // Set the chip checked change listener
//            chip.setOnCheckedChangeListener { view, isChecked ->
//                if (isChecked) {
//                    medicineCountLists.add(view.text.toString())
//                } else {
//                    medicineCountLists.remove(view.text.toString())
//                }
//
//                if (medicineCountLists.isNotEmpty()) {
//                    mPresenter.addMedicineCount(medicineCountLists.toString())
//
//                }
//            }
//        }
//
//        for (index in 0 until view.chip_group_medicine.childCount) {
//            val chip: Chip = view.chip_group_medicine.getChildAt(index) as Chip
//
//            // Set the chip checked change listener
//            chip.setOnCheckedChangeListener { view, isChecked ->
//                if (isChecked) {
//                    medicineTypeLists.add(view.text.toString())
//                } else {
//                    medicineTypeLists.remove(view.text.toString())
//                }
//
//                if (medicineTypeLists.isNotEmpty()) {
//                    mPresenter.addMedicineType(medicineTypeLists.toString())
//
//                }
//            }
//        }
//
//        view.routine_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View,
//                position: Int,
//                id: Long
//            ) {
//                routine = parent.getItemAtPosition(position).toString()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {}
//        }
//
//        view.btn_insert_medicine.setOnClickListener {
//            mPresenter.addToPrescriptionLists(
//                medicineVO.name.toString(),
//                view.ed_amount.text.toString(),
//                view.ed_day.text.toString(),
//                routine.toString(),
//                view.ed_comment.text.toString(),
//                medicineTypeLists.toString(),
//                medicineCountLists.toString()
//            )
//            dialog?.dismiss()
//        }
//
//        dialog?.show()






}