package com.myproject.newtestapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myproject.newtestapp.R
import com.myproject.newtestapp.models.StudentDataModel

class StudentDataAdapter(private val mList: List<StudentDataModel>) : RecyclerView.Adapter<StudentDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_details, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]

        holder.name.text = item.name
        holder.email.text = item.email
        holder.phoneNo.text = item.phone.toString()
        holder.className.text = item.className
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val email: TextView = itemView.findViewById(R.id.tvEmail)
        val phoneNo: TextView = itemView.findViewById(R.id.tvPhone)
        val className: TextView = itemView.findViewById(R.id.tvClass)
    }
}