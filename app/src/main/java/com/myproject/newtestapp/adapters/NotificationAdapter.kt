package com.myproject.newtestapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myproject.newtestapp.R
import com.myproject.newtestapp.models.NotificationModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//class NotificationAdapter(val mList: List<NotificationModel>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): NotificationAdapter.ViewHolder {
//
//    }
//
//    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
//    }
//    class ViewHolder {
//
//    }
//}
class NotificationAdapter( val list: List<NotificationModel>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgApp: ImageView = itemView.findViewById(R.id.imgNotification)
        val txtHeading: TextView = itemView.findViewById(R.id.tvHeading)
        val txtDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val txtTime: TextView = itemView.findViewById(R.id.tvTime)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = list[position]

        holder.imgApp.setImageResource(item.icon)
        holder.txtHeading.text = item.heading
        holder.txtDescription.text = item.description

        val currentTime = SimpleDateFormat(
            "hh:mm a",
            Locale.getDefault()
        ).format(Date())

        holder.txtTime.text = currentTime
    }

    override fun getItemCount(): Int {
        return list.size
    }
}