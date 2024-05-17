package com.umutdiler.dailygourme2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.umutdiler.dailygourme2.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    /*burada viewbinding kullanıyoruz
     viewbinding ile layout dosyalarındaki elemanlara erişim sağlayabiliyoruz
     textbox , button gibi elemanlara erişim sağlayabiliyoruz
     */
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // burada ise navigation controller oluşturuyoruz ve fragmentlarımınız yol haritasını belirliyoruz
        navController = Navigation.findNavController(this, R.id.fragment)

    }




}