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
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.umutdiler.dailygourme2.RecommendRecepieActivity
import com.umutdiler.dailygourme2.classes.GetData
import com.umutdiler.dailygourme2.databinding.FragmentProfileBinding

/**
 * farklı olaarak GetData implement ediyoruz interfacein içindeki getData fonksiynunu da
 * override ediyoruz
 */
class ProfileFragment : Fragment(), GetData {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * burda farklı olarak aksiyonla main fragmenttan girilen email bilgisini bu fragmenta taşıyoruz
         * ve bu bilgiyi getData fonksiyonuna gönderiyoruz
         */
        var email = arguments?.let { ProfileFragmentArgs.fromBundle(it).email }.toString()

        getData(email)

        // intent aksiyonlardan farklı olarak bir fragmenta değil activitye geçmeyi sağlar
        binding.recommendRecepie.setOnClickListener {
            val intent = Intent(requireActivity(), RecommendRecepieActivity::class.java)
            startActivity(intent)
        }
        binding.addRecepieButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToAddRecepieFragment(email)
            Navigation.findNavController(it).navigate(action)
        }
        binding.myRecepiesButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToMyRecepies(email)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getData(email: String) {

        db.collection("users").orderBy("email", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Error", error)
                } else {
                    if (snapshot != null) {
                        if (!snapshot.isEmpty) {

                            val documents = snapshot.documents

                            for (document in documents) {
                                if (document.get("email") == email) {
                                    val name = document.get("name") as String
                                    val last = document.get("last") as String
                                    val age = document.get("age") as String
                                    val height = document.get("height") as String
                                    val weight = document.get("weight") as String

                                    var calorie =
                                        (weight.toDouble() * 13.75 + height.toDouble() * 5 - age.toDouble() * 6.77).toString()




                                    with(binding) {
                                        nameText.text = String.format("%s %s", name, last)
                                        ageText.text = String.format("Age : %s", age)
                                        heightText.text = String.format("Height : %s", height)
                                        weightText.text = String.format("Weight : %s", weight)
                                        calorieText.text = String.format("Calorie : %s", calorie)
                                    }
                                }

                            }


                        }
                    }
                }
            }
    }


}
