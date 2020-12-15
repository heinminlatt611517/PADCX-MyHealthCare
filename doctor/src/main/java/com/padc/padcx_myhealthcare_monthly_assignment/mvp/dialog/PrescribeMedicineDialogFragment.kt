package com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.chip.Chip
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.PrescribeMedicinePresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.PrescribeMedicinePresenterImpl
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.prescribe_medicine_dialog.*


class PrescribeMedicineDialogFragment : DialogFragment() {

    companion object {
        const val TAG_ADD_PRESCRIBE_DIALOG = "TAG_ADD_PRESCRIBE_DIALOG"
        const val BUNDLE_NAME = "BUNDLE_NAME"
        const val BUNDLE_DESCRIPTION = "BUNDLE_DESCRIPTION"
        const val BUNDLE_AMOUNT = "BUNDLE_AMOUNT"
        const val BUNDLE_MEDICINE_ID = "BUNDLE_PATIENT_ID"
        const val BUNDLE_BIRTH_DATE = "BUNDLE_BIRTH_DATE"
        const val BITMAP_IMAGE = "BITMAP_IMAGE"

        fun newFragment(): PrescribeMedicineDialogFragment {
            return PrescribeMedicineDialogFragment()
        }
    }

    private var routine: String? = null
    private lateinit var mPresenter: PrescribeMedicinePresenter
    private val  medicineCountLists = mutableListOf<String>()
    private val medicineTypeLists = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.prescribe_medicine_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpActionsListener()
        setUpItemSelectedListener()
        init()
    }

    private fun setUpItemSelectedListener() {
        routine_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                routine = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun init() {
//        tv_medicineName.text = arguments?.getString(BUNDLE_NAME)
    }

    private fun setUpActionsListener() {

        for (index in 0 until chip_group_amount.childCount) {
            val chip:Chip = chip_group_amount.getChildAt(index) as Chip

            // Set the chip checked change listener
            chip.setOnCheckedChangeListener{view, isChecked ->
                if (isChecked){
                    medicineCountLists.add(view.text.toString())
                }else{
                    medicineCountLists.remove(view.text.toString())
                }

                if (medicineCountLists.isNotEmpty()){
                   mPresenter.addMedicineCount(medicineCountLists.toString())

                }
            }
        }

        for (index in 0 until chip_group_medicine.childCount) {
            val chip:Chip = chip_group_medicine.getChildAt(index) as Chip

            // Set the chip checked change listener
            chip.setOnCheckedChangeListener{view, isChecked ->
                if (isChecked){
                    medicineTypeLists.add(view.text.toString())
                }else{
                    medicineTypeLists.remove(view.text.toString())
                }

                if (medicineTypeLists.isNotEmpty()){
                    mPresenter.addMedicineType(medicineTypeLists.toString())

                }
            }
        }

        btn_insert_medicine.setOnClickListener {
            mPresenter.addToPrescriptionLists(arguments?.getString(BUNDLE_NAME).toString(),ed_amount.text.toString(),
            ed_day.text.toString(),routine.toString(),ed_comment.text.toString(),medicineTypeLists.toString(),medicineCountLists.toString())
           dismiss()
        }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(PrescribeMedicinePresenterImpl::class.java)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.FILL_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog?.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
    }


}