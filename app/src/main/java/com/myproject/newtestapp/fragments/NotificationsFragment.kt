package com.myproject.newtestapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.newtestapp.R
import com.myproject.newtestapp.adapters.NotificationAdapter
import com.myproject.newtestapp.databinding.FragmentNotificationsBinding
import com.myproject.newtestapp.models.NotificationModel

class NotificationsFragment : Fragment() {
    lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notificationList = arrayListOf(

            NotificationModel(
                R.drawable.ic_notification,
                "District Cricket Tournament",
                "Registration starts tomorrow."
            ),

            NotificationModel(
                R.drawable.ic_notification,
                "Football Tournament Selection",
                "Your registration is rejected."
            ),

            NotificationModel(
                R.drawable.ic_notification,
                "Chess Tournament Selection",
                "Your registration is approved."
            )
        )

        binding.rvNotifications.layoutManager = LinearLayoutManager(requireActivity())

        binding.rvNotifications.adapter = NotificationAdapter(notificationList)
    }
}