package com.uni.inistudents.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.inistudents.R
import com.uni.inistudents.adapter.UniversityListAdapter
import com.uni.inistudents.listener.OnSelectedUniversity
import com.uni.inistudents.model.DataInfo
import com.uni.inistudents.presenter.UniversityListPresenter
import com.uni.inistudents.utils.dialogFragmentAddServiceCategoryLayoutParams
import com.uni.inistudents.view.UniversityListView
import kotlinx.android.synthetic.main.university_list_dialog_fragment.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class UniversityListDialogFragment: BaseDialogFragmentView(), UniversityListView {

    private var listener: OnSelectedUniversity? = null

    @InjectPresenter
    override lateinit var presenter: UniversityListPresenter

    @ProvidePresenter
    fun providePresenter() = UniversityListPresenter(
            arguments!!.getString("UNI_ID")!!
    )

    override fun getLayoutResID(): Int = R.layout.university_list_dialog_fragment

    override fun setupView(savedInstanceState: Bundle?) {
        rvUniversity.layoutManager = LinearLayoutManager(requireContext())
        tvClose.setOnClickListener { presenter.closeDialogClicked() }
        etSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do something
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do something
            }

            override fun afterTextChanged(s: Editable?) {
                presenter.filterUni(s.toString())
            }

        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as OnSelectedUniversity
        } catch (e: ClassCastException){
            throw ClassCastException(context.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        dialogFragmentAddServiceCategoryLayoutParams(dialog!!, context!!)
    }

    override fun onCloseDialogClicked(university: DataInfo?) {
        listener?.onSelectedUniversity(university)
        dialog!!.dismiss()
    }

    override fun setUniversityAdapter(adapter: UniversityListAdapter) {
        rvUniversity.adapter = adapter
    }
}