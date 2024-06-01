package com.umutdiler.dailygourme2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.umutdiler.dailygourme2.databinding.ActivityMainBinding


/**
 * app compat activityden miras alınıyor
  */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    /**
     * burada onCreate overrride ediliyor ve override edildiği yerden super ile saveDInstanceState alınıyor
      */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // navigation bağlanıyor navigationa da fragment_nav dan bakabilirsiniz
        navController = Navigation.findNavController(this, R.id.fragment)


    }




}