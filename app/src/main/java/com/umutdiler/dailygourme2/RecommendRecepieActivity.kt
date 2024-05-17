package com.umutdiler.dailygourme2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.runBlocking

class RecommendRecepieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recommend_recepie)

        // burada etPromt ile kullanıcıdan alınan veriyi alıyoruz ve btnSubmit ile de bu veriyi kullanarak modeli çalıştırıyoruz
        // ve geminiye prompt olarak veriyoruz ve sonucu tVResult ile ekrana yazdırıyoruz

        val eTPrompt= findViewById<EditText>(R.id.eTPrompt)
        val btnSubmit= findViewById<Button>(R.id.btnSubmit)
        val tVResult= findViewById<TextView>(R.id.tVResult)

        btnSubmit.setOnClickListener {
            val prompt= eTPrompt.text.toString()

            val generativeModel = GenerativeModel(
                // For text-only input, use the gemini-pro model
                modelName = "gemini-pro",
                apiKey = "AIzaSyCS5iMCL6yNReRJMavfBt5-GhpGkPAdr3c",
                // ENTER YOUR KEY
            )
            runBlocking {
                val response = generativeModel.generateContent(prompt)
                tVResult.text= response.text
            }
        }

    }
}
