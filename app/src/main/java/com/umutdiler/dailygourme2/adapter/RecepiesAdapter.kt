package com.umutdiler.dailygourme2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.umutdiler.dailygourme2.classes.Recepies
import com.umutdiler.dailygourme2.databinding.RecyclerRowBinding

class RecepiesAdapter(var recepieList : MutableList<Recepies>) : RecyclerView.Adapter<RecepiesAdapter.RecepiesViewHolder>() {

    /**
     * burada RecepiesAdapter adında bir class tanımlıyoruz ve bu classı RecyclerView sınıfından miras alıyoruz
     * my recepieste oluşturduğumuz ve içine değişkenleri tanımladığımız liste yapımızı burada kullanıyoruz
     * bunu da bind fonksiyonunun içinde yapıyoruz
     */
    class RecepiesViewHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
        val recyclerRowBinding = binding
        fun bind(recepie : Recepies){
            with(recyclerRowBinding){
                foodName.text = recepie.foodName
                foodIngredients.text = recepie.ingredients
                foodInstructions.text = recepie.description
                recyclerEmail.text = recepie.email
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecepiesViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),
            parent,false)
        return RecepiesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recepieList.size
    }

    override fun onBindViewHolder(holder: RecepiesViewHolder, position: Int) {

        holder.bind(recepieList[position])

    }
}