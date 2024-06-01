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
import com.umutdiler.dailygourme2.classes.Person
import com.umutdiler.dailygourme2.databinding.FragmentMainBinding

/**
 * burada az önceki activityde anlatılan şeylerin aynısı var
 */
class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!
    // auth database için geçerli olan bir değişken databasedeki kişi giriş sistemi için kullanılıyor
    private  var auth = Firebase.auth
    // burada ise Person sınıfında person adında bir nesne  oluşturuyoruz ve içindeki propertylere boş string değerler atıyoruz
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

        /**
         * burada register ve login butonlarına tıklanınca ne olacağını belirtiyoruz fragment_nav da belirttiğimiz aksiyonları
         * çağırıyoruz fragment_nav da verdiğimiz her bir ok aksiyon anlamına geliyor
         */

        binding.registerButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragment2ToRegisterFragment2()
            Navigation.findNavController(it).navigate(action)
        }
        binding.loginButton.setOnClickListener {
            person.email = binding.emailText.text.toString()
            person.password = binding.passwordText.text.toString()

            // boş alan olmadığından emin oluyoruz ve eğer boş alan varsa kullanıcıya uyarı veriyoruz aynı zamanda
            // email ve passwordin doğru olup olmadığını kontrol ediyoruz

            if(person.email.isEmpty() || person.password.isEmpty()){
                Toast.makeText(requireContext(),"Email or Password can't leave empty",
                    Toast.LENGTH_LONG).show()
            }else{
                /**
                 * burada firebase auth database kullanarak email ve password ile giriş yapmaya çalışıyoruz
                 * bunların databasedeki doğruluğunu kontrol ediyoruz ve eğer doğruysa profile fragmentine yönlendiriyoruz
                 * eğer yanlışsa kullanıcıya uyarı veriyoruz
                 */
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