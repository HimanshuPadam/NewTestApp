package com.myproject.newtestapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.newtestapp.R
import com.myproject.newtestapp.adapters.AppAdapter
import com.myproject.newtestapp.databinding.FragmentMyAppsBinding
import com.myproject.newtestapp.models.AppItemModel

class MyAppsFragment : Fragment() {
    lateinit var binding: FragmentMyAppsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyAppsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appList = arrayListOf(
            AppItemModel(R.drawable.ic_google, "WhatsApp"),
            AppItemModel(R.drawable.ic_google, "Instagram"),
            AppItemModel(R.drawable.ic_google, "Facebook"),
            AppItemModel(R.drawable.ic_google, "Telegram"),
            AppItemModel(R.drawable.ic_google, "Snapchat"),
            AppItemModel(R.drawable.ic_google, "Twitter")
        )
        binding.rvApps.layoutManager =
            LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        binding.rvApps.adapter = AppAdapter(appList)
    }
}