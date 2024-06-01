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
import com.umutdiler.dailygourme2.classes.SetData
import com.umutdiler.dailygourme2.classes.Recepies
import com.umutdiler.dailygourme2.databinding.FragmentAddRecepieBinding


class AddRecepieFragment : Fragment(), SetData {

    private var _binding: FragmentAddRecepieBinding? = null
    private val binding get() = _binding!!
    val db = Firebase.firestore
    var recepie = Recepies("", "", "","")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRecepieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveRecepie.setOnClickListener {
            setData(it)
        }
    }

    override fun setData(view : View) {
        /**
         * Bu fragment registerFragment ile aynı mantıkta çalıyor tek fark burada kullandığımız nesen Recepie sınıfına ait
         */
        with(recepie){
            foodName = binding.recepieName.text.toString()
            ingredients = binding.recepieIngredient.text.toString()
            description = binding.howToMake.text.toString()
            email = arguments.let { com.umutdiler.dailygourme2.fragments.AddRecepieFragmentArgs.fromBundle(it!!).email }
        }

        if (recepie.foodName.isEmpty() || recepie.ingredients.isEmpty() || recepie.description.isEmpty()) {
            Toast.makeText(
                requireContext(), "Recepie or Ingredient can't leave empty", Toast.LENGTH_LONG
            ).show()
        } else {
            val recepieHash = hashMapOf(
                "foodName" to recepie.foodName,
                "ingredients" to recepie.ingredients,
                "description" to recepie.description,
                "email" to recepie.email
            )
            db.collection("recepies").document(recepie.foodName).set(recepieHash)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference}")
                    Toast.makeText(requireContext(),"Your recepie successfully added",Toast.LENGTH_LONG).show()
                }.addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    Toast.makeText(requireContext(),"Your recepie is not added",Toast.LENGTH_LONG).show()
                }
        }
    }
}



