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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.umutdiler.dailygourme2.MainActivity
import com.umutdiler.dailygourme2.R
import com.umutdiler.dailygourme2.classes.Person
import com.umutdiler.dailygourme2.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!
    private  var auth = Firebase.auth
    private var person = Person("","","","","","","")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNav = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.visibility = View.VISIBLE

        binding.registerButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragment2ToRegisterFragment2()
            Navigation.findNavController(it).navigate(action)
        }
        binding.loginButton.setOnClickListener {
            person.email = binding.emailText.text.toString()
            person.password = binding.passwordText.text.toString()

            if(person.email.isEmpty() || person.password.isEmpty()){
                Toast.makeText(requireContext(),"Email or Password can't leave empty",
                    Toast.LENGTH_LONG).show()
            }else{
                auth.signInWithEmailAndPassword(person.email,person.password).addOnCompleteListener {task->
                    if(task.isSuccessful){
                        Log.d(TAG,"signInWithEmail:Success")
                        val action = MainFragmentDirections.actionMainFragment2ToProfileFragment2(person.email)
                        Navigation.findNavController(it).navigate(action)
                        val user = auth.currentUser
                    }else{
                        Log.w(TAG,"signInWithEmail:Failure")
                        updateUI(null)
                    }
                }
            }
        }
    }
    fun updateUI(user : FirebaseUser?){
        if(user == null){
            Toast.makeText(requireContext(),"Email or Password are not Correct",
                Toast.LENGTH_LONG).show()
        }
    }


}