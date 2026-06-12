package com.myproject.newtestapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.myproject.newtestapp.R
import com.myproject.newtestapp.adapters.StudentDataAdapter
import com.myproject.newtestapp.databinding.FragmentHomeBinding
import com.myproject.newtestapp.models.StudentDataModel
import java.util.Locale
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    var studentDataAdapter: StudentDataAdapter? = null
    lateinit var studentList: ArrayList<StudentDataModel>
    lateinit var searchList : ArrayList<StudentDataModel>
    lateinit var database: DatabaseReference
    lateinit var studentListener: ValueEventListener


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        studentList = ArrayList()
        searchList = ArrayList()

        binding.animationView.pauseAnimation()
        binding.animationView.playAnimation()

        binding.animationView.setAnimation(R.raw.another_animation)


        studentDataAdapter = StudentDataAdapter(searchList)
        database = Firebase.database.reference.child("Students")
        getStudents()


        binding.rvDashboard.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvDashboard.adapter = studentDataAdapter
        binding.searchBar.clearFocus()
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchBar.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    studentList.forEach{
                        if(it.name.lowercase(Locale.getDefault()).contains(searchText)){
                            searchList.add(it)
                        }
                        else if(it.className.lowercase(Locale.getDefault()).contains(searchText)){
                            searchList.add(it)
                        }
                        else if(it.email.lowercase(Locale.getDefault()).contains(searchText)){
                            searchList.add(it)
                        }
                        else if(it.phone.toString().lowercase(Locale.getDefault()).contains(searchText)){
                            searchList.add(it)
                        }
                    }
                    binding.rvDashboard.adapter!!.notifyDataSetChanged()

                    if(searchList.isEmpty()){
                        Toast.makeText(
                            requireContext(),
                            "No Student Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                else{
                    searchList.clear()
                    searchList.addAll(studentList)
                    binding.rvDashboard.adapter!!.notifyDataSetChanged()
                }
                return false
            }

        })

        binding.swipeRefresh.setOnRefreshListener {
            getStudents()
            binding.swipeRefresh.isRefreshing = false
        }

        val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(SlideModel(R.drawable.ic_school1, "This is the school infrastructure."))
        imageList.add(SlideModel(R.drawable.ic_school2, "All children are friendly in nature"))
        imageList.add(SlideModel(R.drawable.ic_school3, "and play with fun with each other."))
        binding.imageSlider.setImageList(imageList, scaleType = ScaleTypes.FIT)

        binding.fabAdd.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Add New Student")

            val addDetailsLayout = layoutInflater.inflate(R.layout.add_details, null)
            builder.setView(addDetailsLayout)

            builder.setPositiveButton("Add", null)
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val name = addDetailsLayout.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etName)
                    .text.toString().trim()
                val email = addDetailsLayout.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etEmail)
                    .text.toString().trim()
                val phone = addDetailsLayout.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etNumber)
                    .text.toString().trim()
                val department = addDetailsLayout.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etDepartment)
                    .text.toString().trim()

                when {
                    name.isEmpty() || email.isEmpty() || phone.isEmpty() || department.isEmpty() -> {
                        Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    phone.length != 10 -> {
                        Toast.makeText(requireContext(), "Enter Valid 10 Digit Mobile Number", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        Toast.makeText(requireContext(), "Enter Valid Email", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }
                val student = StudentDataModel(name, email, phone.toLong(), department)
                val studentId = database.push().key!!

                database.child(studentId).setValue(student)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Student Added Successfully", Toast.LENGTH_SHORT)
                            .show()
                        alertDialog.dismiss()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Failed: ${it.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::database.isInitialized && ::studentListener.isInitialized) {
            database.removeEventListener(studentListener)
        }
    }
    
    private fun getStudents() {
        if (::studentListener.isInitialized) {
            database.removeEventListener(studentListener)
        }
        studentListener= object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!isAdded) return
                studentList.clear()
                searchList.clear()
                for (studentSnapshot in snapshot.children) {
                    val student =studentSnapshot.getValue(StudentDataModel::class.java)

                    if (student != null) {
                        studentList.add(student)
                        searchList.add(student)
                    }
                }
                studentDataAdapter?.notifyDataSetChanged()
                if(isAdded) {
                    Toast.makeText(
                        requireContext(),
                        "${studentList.size} Students Loaded",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    binding.animationView.pauseAnimation()
                    binding.animationView.visibility = View.GONE
                    binding.rvDashboard.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                context?.let {
                    Toast.makeText(
                        requireContext(),
                        error.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }


        }
        database.addValueEventListener(studentListener)
    }
}