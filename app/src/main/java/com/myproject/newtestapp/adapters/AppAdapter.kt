package com.myproject.newtestapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myproject.newtestapp.R
import com.myproject.newtestapp.models.AppItemModel

class AppAdapter(val list: List<AppItemModel>) : RecyclerView.Adapter<AppAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.imgApp)
        val text: TextView = itemView.findViewById(R.id.tvApp)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.image.setImageResource(list[position].image)
        holder.text.text = list[position].name
    }

    override fun getItemCount(): Int {
        return list.size
    }
}