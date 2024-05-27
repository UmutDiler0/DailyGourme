package com.umutdiler.dailygourme2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.umutdiler.dailygourme2.adapter.RecepiesAdapter
import com.umutdiler.dailygourme2.classes.Recepies
import com.umutdiler.dailygourme2.databinding.FragmentMyRecepiesBinding


class MyRecepies : Fragment() {

    private var _binding: FragmentMyRecepiesBinding? = null
    private val binding get() = _binding!!
    var recepieList: MutableList<Recepies> = mutableListOf()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var adapter: RecepiesAdapter
    lateinit var recepie : Recepies


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyRecepiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recepie = Recepies("","","")
        getData()

    }

    fun getData() {
        db!!.collection("recepies").orderBy("foodName", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(requireContext(),"Getting error while getting data",Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    if (!value.isEmpty) {
                       val documents = value.documents
                        for( document in documents){
                            val foodName = document.get("foodName") as String
                            val ingredients = document.get("ingredients") as String
                            val description = document.get("description") as String
                            recepie = Recepies(foodName,ingredients,description)
                            recepieList.add(recepie)
                        }



                        adapter = RecepiesAdapter(recepieList)
                        binding.recepiesRecycler.adapter = adapter

                    }
                }
            }
        }
    }


}