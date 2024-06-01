package com.umutdiler.dailygourme2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.umutdiler.dailygourme2.adapter.RecepiesAdapter
import com.umutdiler.dailygourme2.classes.GetData
import com.umutdiler.dailygourme2.classes.SetData
import com.umutdiler.dailygourme2.classes.Recepies
import com.umutdiler.dailygourme2.databinding.FragmentMyRecepiesBinding

/**
 * diğer fragmentlardan farklı olarak bir dinamaik liste yapımız var bu liste içine sasdece Recepie türünden değişkenler alabiliriz
 * ve bu listeyi başlangıçta boş bir liste olarak tanımlıyoruz
 */
class MyRecepies : Fragment(), GetData {

    private var _binding: FragmentMyRecepiesBinding? = null
    private val binding get() = _binding!!
    var recepieList: MutableList<Recepies> = mutableListOf()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var adapter: RecepiesAdapter
    private var recepie  = Recepies("","","","",)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyRecepiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recepie.email = arguments?.let { MyRecepiesArgs.fromBundle(it).email }.toString()

        getData(recepie.email)

    }

    override fun getData(email : String) {

        recepieList.clear()


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
                            val email = document.get("email") as String

                            recepie = Recepies(foodName,ingredients,description,email)
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