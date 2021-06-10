package com.uni.inistudents.adapter

import android.view.View
import android.view.ViewGroup
import com.uni.inistudents.R
import com.uni.inistudents.listener.OnUniversityClicked
import com.uni.inistudents.model.DataInfo
import com.uni.inistudents.utils.language
import kotlinx.android.synthetic.main.item_university.view.*

class UniversityListAdapter(private var list: List<DataInfo>, private val listener: OnUniversityClicked) : BaseRecyclerViewAdapter<UniversityListAdapter.UniversityListRecyclerView>() {

    var selectedUniversity: DataInfo? = null
        set(value) {
            val oldPos = list.indexOf(field)
            val newPos = list.indexOf(value)
            field = value
            notifyItemChanged(oldPos)
            notifyItemChanged(newPos)
        }

    fun filterList(list: List<DataInfo>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityListRecyclerView {
        return UniversityListRecyclerView(buildView(parent, viewType), listener)
    }

    override fun onBindViewHolder(holder: UniversityListRecyclerView, position: Int) {
        holder.bind(list[position], isSelect = list[position] == selectedUniversity )
    }

    override fun getItemCount(): Int = list.count()


    override fun getItemViewType(position: Int): Int = R.layout.item_university


    inner class UniversityListRecyclerView(itemView: View, private val listener: OnUniversityClicked) : BaseViewHolder(itemView) {

        private lateinit var university: DataInfo
        private var isSelect: Boolean = false

        init {
            itemView.setOnClickListener { listener.onUniversityClicked(university) }
        }

        fun bind(university: DataInfo, isSelect: Boolean) {
            this.university = university
            this.isSelect = isSelect

            itemView.tvUniversityName.text = if (language == "en") university.title_en else university.title
            itemView.ivCheck.visibility = if (isSelect) View.VISIBLE else View.GONE

        }
    }

}