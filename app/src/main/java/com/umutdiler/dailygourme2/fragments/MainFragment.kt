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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.umutdiler.dailygourme2.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // burada register buttonuna tıklandığında register ekranına gitmesini sağlıyoruz
        // logine basıldığında ise email ve passwordin doğru olup olmadığını kontrol ediyoruz eğer doğruysa profile ekranına yönlendiriyoruz
        // değilse kullanıcıya uyarı veriyoruz

        binding.registerButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragment2ToRegisterFragment2()
            Navigation.findNavController(it).navigate(action)
        }
        binding.loginButton.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()

            // boş alan olmadığından emin oluyoruz ve eğer boş alan varsa kullanıcıya uyarı veriyoruz aynı zamanda email ve passwordin doğru olup olmadığını kontrol ediyoruz

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(requireContext(),"Email or Password can't leave empty",Toast.LENGTH_LONG).show()
            }else{
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task->
                    if(task.isSuccessful){
                        Log.d(TAG,"signInWithEmail:Success")
                        val action = MainFragmentDirections.actionMainFragment2ToProfileFragment2()
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
            Toast.makeText(requireContext(),"Email or Password are not Correct",Toast.LENGTH_LONG).show()
        }
    }


}