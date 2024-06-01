package com.umutdiler.dailygourme2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.umutdiler.dailygourme2.R
import com.umutdiler.dailygourme2.databinding.FragmentSplashBinding

/**
 * Splash fragment Fragment() sınıfından kalıtım almaktadır
 */
class SplashFragment : Fragment() {

    /**
     * onCreateView metodu ile fragment_splash.xml dosyası fragmente bağlanır
     * onViewCreated metodu ile fragment açıldığında yapılacak işlemler belirlenir
     * mainActivityde olduğu gibi burada da override ettiğimiz ve bir üst fonksiyondan super ile aldığımız parametreler bulunmaktadır
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        android.os.Handler(requireContext().mainLooper).postDelayed({
            /**
             * 5 saniye sonra main fragmente geçiş yapılacak
             * bunu da fragment_nav dan oluşturduğumuz aksiyonlardan birini çağırıyoruz
              */
            val action = SplashFragmentDirections.actionSplashFragmentToMainFragment()
            Navigation.findNavController(view).navigate(action)
        }, 5000)
    }

}