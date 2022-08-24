package com.example.reciperecommender.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reciperecommender.R

class DirectionsFragment : Fragment() {

    private lateinit var directionRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_directions, container, false)
        directionRecyclerView = view.findViewById(R.id.directionRecyclerView)
        return view
    }
}