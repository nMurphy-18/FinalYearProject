package com.example.reciperecommender.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reciperecommender.R
import com.example.reciperecommender.dataClasses.RecipeInfo
import kotlinx.android.synthetic.main.recipe_layout.view.*

class RecipeAdapter(private val ingredients: List<RecipeInfo.ExtendedIngredient>) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
        return ingredients.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val theIngredient = ingredients.get(position)
        holder.title.text = theIngredient.original
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.txtTitle
    }
}