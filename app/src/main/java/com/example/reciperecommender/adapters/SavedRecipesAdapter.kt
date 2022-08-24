package com.example.reciperecommender.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.reciperecommender.R
import com.example.reciperecommender.dataClasses.SavedRecipes
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.saved_recipes_layout.view.*

class SavedRecipesAdapter(private val savedrecipes: List<SavedRecipes>,
                          private val listener: OnItemClickListener) :
    RecyclerView.Adapter<SavedRecipesAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
        return savedrecipes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.saved_recipes_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val theRecipe = savedrecipes.get(position)

        holder.name.text = theRecipe.title
        holder.minutes.text = theRecipe.time
        holder.servings.text = theRecipe.servings
        Picasso.get().load(theRecipe.image).into(holder.img)
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view),
    View.OnClickListener {
        val name: TextView = view.savedRecipeName
        val minutes: TextView = view.savedRecipeMinutes
        val servings: TextView = view.savedRecipeServings
        val img = view.savedRecipeImg

        val deleteIcon = view.deleteIcon

        init {
            view.setOnClickListener(this)
            this.deleteIcon.setOnClickListener {
                val position: Int = adapterPosition
                val result = savedrecipes.get(position)
                val name: String = result.title
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteIconClick(position, name)
                }
            }
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            val result = savedrecipes.get(position)
            val id: String = result.id
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position, id)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, id: String)
        fun onDeleteIconClick(position: Int, name: String)
    }
}