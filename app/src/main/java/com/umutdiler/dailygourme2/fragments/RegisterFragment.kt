package com.umutdiler.dailygourme2.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.umutdiler.dailygourme2.classes.SetData
import com.umutdiler.dailygourme2.classes.Person
import com.umutdiler.dailygourme2.databinding.FragmentRegisterBinding

/**
 * proifler fragmentta olduğu gibi burada da bir implementasyon yapıyoruz
 */
class RegisterFragment : Fragment(), SetData {

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private var person= Person("","","","","","","")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            setData(it)
        }
    }
    fun updateUI(user: FirebaseUser?){
        if(user == null){
            Toast.makeText(requireContext(),"Email or Password are not Correct",Toast.LENGTH_LONG).show()
        }
    }

    override fun setData(view : View) {
        /**
         * boşluklara girdiğimiz verileri person nesnesinin propertylerine atıyoruz
         * property ne diye sormayın gidin çeviriden bakın :D
         */
        with(person){
            email = binding.registerEmail.text.toString()
            password = binding.registerPassword.text.toString()
            name = binding.editNameText.text.toString()
            lastName = binding.editLastText.text.toString()
            age = binding.editAgeText.text.toString()
            height = binding.registerHeight.text.toString()
            weight = binding.registerWeight.text.toString()
        }



        if(person.email.isEmpty() || person.password.isEmpty() || person.height.isEmpty() ||
            person.weight.isEmpty() ||
            person.name.isEmpty() || person.age.isEmpty() || person.lastName.isEmpty()){
            Toast.makeText(requireContext(),"Please fill all the blanks",Toast.LENGTH_LONG).show()
        }else{

            auth.createUserWithEmailAndPassword(person.email,person.password).addOnCompleteListener {task ->
                val user = hashMapOf(
                    "email" to person.email,
                    "password" to person.password,
                    "name" to person.name,
                    "last" to person.lastName,
                    "age" to person.age,
                    "height" to person.height,
                    "weight" to person.weight,
                )
                if(task.isSuccessful){
                    db.collection("users")
                        .document(person.email)
                        .set(user)
                        .addOnSuccessListener {documentReference->
                            Log.d(TAG,"DocumentSnapshot added with ID: ${documentReference}")
                        }
                        .addOnFailureListener { e->
                            Log.w(TAG,"Error added document",e)
                        }
                    Log.d(TAG,"createUserWithEmail:Success")
                    val action = RegisterFragmentDirections.actionRegisterFragment2ToProfileFragment2(person.email)
                    Navigation.findNavController(view).navigate(action)

                }else{
                    Log.w(TAG,"createUserWithEmail:Failure")
                    val user = auth.currentUser
                    updateUI(user)
                }
            }


        }
    }



}