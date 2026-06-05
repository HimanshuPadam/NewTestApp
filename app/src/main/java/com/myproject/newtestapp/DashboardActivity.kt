package com.myproject.newtestapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.newtestapp.databinding.ActivityDashboardBinding
import com.myproject.newtestapp.models.StudentData
import com.myproject.newtestapp.adapters.Adapter


class DashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding
    var adapter: Adapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val studentData = arrayListOf(
            StudentData(
                "Himanshu",
                "himanshu@gmail.com",
                9876543210,
                "CSE"
            ),
            StudentData(
                "Shoray",
                "jeking@gmail.com",
                1234567890,
                "ECE"
            ),
            StudentData(
                "Nikhil",
                "nikhil@gmail.com",
                9876512340,
                "ME"
            ),
            StudentData(
                "Naveen",
                "naveen@gmail.com",
                1234509876,
                "CS-AI"
            ),
            StudentData(
                "Kiran",
                "kiran@gmail.com",
                9876543214,
                "SAP"
            )
        )
        adapter = Adapter(studentData)
        binding.rvDashboard.layoutManager = LinearLayoutManager(this)
        binding.rvDashboard.adapter = adapter
    }
}