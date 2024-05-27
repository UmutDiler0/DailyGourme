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
import com.google.android.libraries.places.api.Places
import com.umutdiler.dailygourme2.classes.API_KEY
import kotlinx.coroutines.runBlocking

class RecommendRecepieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recommend_recepie)

        val eTPrompt= findViewById<EditText>(R.id.eTPrompt)
        val btnSubmit= findViewById<Button>(R.id.btnSubmit)
        val tVResult= findViewById<TextView>(R.id.tVResult)



        btnSubmit.setOnClickListener {
            val prompt = eTPrompt.text.toString()

            if (prompt.contains("recepie") || prompt.contains("tarif")) {
                val generativeModel = GenerativeModel(

                    modelName = "gemini-pro",
                    apiKey = API_KEY

                )
                runBlocking {
                    val response = generativeModel.generateContent(prompt)
                    tVResult.text = response.text
                }
            } else {
                tVResult.text = "Please enter a prompt that contains the word 'recepie' or 'tarif'"
            }
        }

    }
}
