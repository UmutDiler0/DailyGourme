package com.umutdiler.dailygourme2.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.umutdiler.dailygourme2.classes.Recepies
import com.umutdiler.dailygourme2.databinding.FragmentAddRecepieBinding


class AddRecepieFragment : Fragment() {

    private var _binding: FragmentAddRecepieBinding? = null
    private val binding get() = _binding!!
    val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRecepieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recepie = Recepies("", "", "")

        binding.saveRecepie.setOnClickListener {

            recepie.foodName = binding.recepieName.text.toString()
            recepie.ingredients = binding.recepieIngredient.text.toString()
            recepie.description = binding.howToMake.text.toString()


            if (recepie.foodName.isEmpty() || recepie.ingredients.isEmpty()) {
                Toast.makeText(
                    requireContext(), "Recepie or Ingredient can't leave empty", Toast.LENGTH_LONG
                ).show()
            } else {
                val recepieHash = hashMapOf(
                    "foodName" to recepie.foodName,
                    "ingredients" to recepie.ingredients,
                    "description" to recepie.description
                )
                db.collection("recepies").document(recepie.foodName).set(recepieHash)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference}")
                    }.addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }
        }
    }
}



