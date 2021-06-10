package com.uni.inistudents.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
    companion object {
        fun buildView(parent: ViewGroup?, viewType: Int): View = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
    }
}