package com.example.reciperecommender.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reciperecommender.R
import com.example.reciperecommender.dataClasses.RecipeInfo
import kotlinx.android.synthetic.main.directions_layout.view.*

class DirectionsAdapter(private val instructions: List<RecipeInfo.AnalyzedInstruction.Step>) :
    RecyclerView.Adapter<DirectionsAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
        return instructions.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.directions_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val theInstruction = instructions.get(position)
        holder.stepNumber.text = theInstruction.number.toString()
        holder.direction.text = theInstruction.step
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val stepNumber: TextView = view.txtStepNumber
        val direction: TextView = view.txtDirection
    }
}