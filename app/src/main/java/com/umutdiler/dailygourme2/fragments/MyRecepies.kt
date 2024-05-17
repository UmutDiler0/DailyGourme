package com.umutdiler.dailygourme2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.umutdiler.dailygourme2.adapter.RecepiesAdapter
import com.umutdiler.dailygourme2.classes.Recepies
import com.umutdiler.dailygourme2.databinding.FragmentMyRecepiesBinding


class MyRecepies : Fragment() {

    private var _binding: FragmentMyRecepiesBinding? = null
    private val binding get() = _binding!!
    var recepieList: ArrayList<Recepies>? = null
    private var db: FirebaseFirestore? = null
    var adapter: RecepiesAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyRecepiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    // recycler view ile layout bağlantısı burada yapılır
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        adapter = RecepiesAdapter(recepieList!!)
        binding.recepiesRecycler.adapter = adapter
    }
    // databaseden gelen verileri alıp recepie adlı bir objenin içine atıyoruz ve bu objeyi de arraylistimizin içine atıp
    // adaptera veri olarak yolluyoruz
    fun getData() {
        db = FirebaseFirestore.getInstance()
        db!!.collection("Recepies").addSnapshotListener { value, error ->
            if (error != null) {
                println(error.localizedMessage)
            } else {
                if (value != null) {
                    if (value.isEmpty) {
                        println("No Data")
                    } else {
                        val documents = value.documents
                        for (document in documents) {
                            val name = document.get("name") as String
                            val ingredients = document.get("ingredients") as String
                            val description = document.get("description") as String
                            val recepie = Recepies(name, ingredients, description)
                            recepieList?.add(recepie)
                        }
                    }
                }
            }
        }
    }


}