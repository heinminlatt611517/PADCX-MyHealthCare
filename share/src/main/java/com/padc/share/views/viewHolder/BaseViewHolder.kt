package com.padc.share.views.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var mData : T? = null

    init {
        itemView.setOnClickListener {
            clickItem(it)
        }
    }

    abstract fun clickItem(it: View?)

    abstract fun bindData(data : T)


}