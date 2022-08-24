package com.example.reciperecommender.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reciperecommender.R
import com.example.reciperecommender.dataClasses.NutrientInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recipe_results_layout.view.*
import retrofit2.Callback

class ResultsAdapter(private val results: List<NutrientInfo.Result>,
                     private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<ResultsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_results_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val theResult = results.get(position)

        holder.title.text = theResult.title
        holder.minutes.text = theResult.readyInMinutes.toString()
        holder.kcal.text = theResult.nutrition.nutrients[0].amount.toString()
        Picasso.get().load(theResult.image).into(holder.img)
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val title: TextView = view.recipeResult
        val minutes: TextView = view.recipeResultNumber
        val kcal: TextView = view.recipeResultKcal
        val img = view.recipeResultImg

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            val result = results.get(position)
            val id: String = result.id.toString()
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position, id)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, id: String)
    }
}