package com.uni.inistudents.presenter

import android.util.Log
import com.uni.inistudents.adapter.UniversityListAdapter
import com.uni.inistudents.listener.OnUniversityClicked
import com.uni.inistudents.model.DataInfo
import com.uni.inistudents.model.UniversitiesDto
import com.uni.inistudents.view.UniversityListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState


@InjectViewState
class UniversityListPresenter(private var selectedUniId: String = ""): BasePresenter<UniversityListView>(), OnUniversityClicked {

    private var selectedUniversity: DataInfo? = null
    private var universities: ArrayList<DataInfo> = ArrayList()
    private var universityListAdapter: UniversityListAdapter? = null
        set(value) {
            field = value
            viewState.setUniversityAdapter(value!!)
        }

    init {
        loadUniversityList()
    }

    fun closeDialogClicked(){
        viewState.onCloseDialogClicked(selectedUniversity)
    }

    private fun loadUniversityList(){
        subscription.add(restApi().iniStudentService.getUniversityList("list")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {onSuccessLoadUniversityList(it)},
                        onError = {Log.d("error->>>", it.message.toString())}
                ))

    }

    private fun onSuccessLoadUniversityList(university: UniversitiesDto){
        universities.addAll(university.data)
        universityListAdapter = UniversityListAdapter(university.data, this)
        if (!isCheckSavedUniversity(university)){
            university.data.forEach {
                if (selectedUniId == it.id){
                    selectedUniversity = it
                    universityListAdapter?.selectedUniversity = it
                }
            }
        }
    }

    override fun onUniversityClicked(university: DataInfo?) {
        selectedUniversity = university
        universityListAdapter?.selectedUniversity = university
        viewState.onCloseDialogClicked(selectedUniversity)
    }

    private fun isCheckSavedUniversity(university: UniversitiesDto) : Boolean{
        university.data.forEach {
            return if (it.id == preferences().universityId){
                selectedUniversity = it
                universityListAdapter?.selectedUniversity = it
                true
            } else false
        }
        return false
    }

    fun filterUni(text: String){
        var filteredUniversities = ArrayList<DataInfo>()
        universities.forEach {
            if (it.title.toLowerCase().contains(text.toLowerCase())){
                filteredUniversities.add(it)
            }
        }
        universityListAdapter!!.filterList(filteredUniversities)
    }
}