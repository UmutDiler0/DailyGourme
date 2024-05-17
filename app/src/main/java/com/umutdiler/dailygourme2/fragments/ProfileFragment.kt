package com.umutdiler.dailygourme2.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.umutdiler.dailygourme2.classes.Person
import com.umutdiler.dailygourme2.RecommendRecepieActivity
import com.umutdiler.dailygourme2.databinding.FragmentProfileBinding



class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    val db = Firebase.firestore
    lateinit var personList : ArrayList<Person>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        personList = ArrayList<Person>()
        getData()

        // yine set onc listenerlarla butonlara tıklandığında ilgili fragmente gitmesini sağlıyoruz


        binding.recommendRecepie.setOnClickListener {
            val intent = Intent(requireActivity(), RecommendRecepieActivity::class.java)
            startActivity(intent)
        }
        binding.addRecepieButton.setOnClickListener{
            val action = ProfileFragmentDirections.actionProfileFragmentToAddRecepieFragment()
            Navigation.findNavController(it).navigate(action)
        }
        binding.myRecepiesButton.setOnClickListener{
            val action = ProfileFragmentDirections.actionProfileFragmentToMyRecepies()
            Navigation.findNavController(it).navigate(action)
        }




    }
    private fun getData(){

        // burada firebase ile bağlantı kuruyoruz ve verileri çekiyoruz

        db.collection("Users").addSnapshotListener { value, error ->
            if(error != null){
                Log.e(TAG,"Error",error)
            }else{
                if(value != null){
                    if(value.isEmpty){
                        val documents = value.documents
                        for(document in documents){
                            val name = document.get("name") as String
                            val last = document.get("last") as String
                            val age = document.get("age") as String
                            val height = document.get("height") as String
                            val weight = document.get("weight") as String

                            with(binding){
                                nameText.text = name
                                ageText.text = age
                                heightText.text = height
                                weightText.text = weight
                            }

                            val person = Person(name,last,age,height,weight)

                        }
                    }
                }
            }
        }
    }


    }
