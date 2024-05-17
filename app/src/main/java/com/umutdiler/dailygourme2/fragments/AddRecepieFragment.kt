package com.umutdiler.dailygourme2.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.Firebase
import com.umutdiler.dailygourme2.classes.Recepies
import com.umutdiler.dailygourme2.databinding.FragmentAddRecepieBinding
import kotlin.collections.hashMapOf


class AddRecepieFragment : Fragment() {

    private var _binding : FragmentAddRecepieBinding? = null
    private val binding get() = _binding!!
    val db = Firebase.firestore




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddRecepieBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recepie = Recepies("","","")

        // burada save recepie butonuna tıklandığında verileri firebase'e kaydediyoruz
        binding.saveRecepie.setOnClickListener {

            recepie.name = binding.recepieName.text.toString()
            recepie.ingredients = binding.recepieIngredient.text.toString()
            recepie.descripton = binding.howToMake.text.toString()


            // burada da verilerin boşluğu doluluğu kontrol ediliyor ve eğer boşluk varsa kullanıcıya uyarı veriliyor ve database kaydediliyor
            if(recepie.name.isEmpty() || recepie.ingredients.isEmpty()){
                Toast.makeText(requireContext(),"Recepie or Ingredient can't leave empty",Toast.LENGTH_LONG).show()
            }else{
                val recepie = hashMapOf(
                    "name" to recepie.name,
                    "ingredients" to recepie.ingredients,
                    "description" to recepie.descripton
                )
                db.collection("recepies")
                    .document(recepie.toString())
                    .set(recepie)
                    .addOnSuccessListener {documentReference ->
                        Log.d(TAG,"DocumentSnapshot added with ID: ${documentReference}")
                    }.addOnFailureListener {e->
                        Log.w(TAG,"Error adding document",e)
                    }
            }
        }
        }
    }



