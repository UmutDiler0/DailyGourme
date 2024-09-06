package com.umutdiler.dailygourme2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.umutdiler.dailygourme2.MainActivity
import com.umutdiler.dailygourme2.R
import com.umutdiler.dailygourme2.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNav = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.visibility = View.GONE
        android.os.Handler(requireContext().mainLooper).postDelayed({
            val action = SplashFragmentDirections.actionSplashFragmentToMainFragment()
            Navigation.findNavController(view).navigate(action)
        }, 5000)
    }

}