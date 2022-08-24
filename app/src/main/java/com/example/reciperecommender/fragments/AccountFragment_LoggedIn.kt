package com.example.reciperecommender.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.reciperecommender.MainActivity
import com.example.reciperecommender.R
import kotlinx.android.synthetic.main.fragment_account__logged_in.*

class AccountFragment_LoggedIn : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account__logged_in, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        logout.setOnClickListener {
            (activity as MainActivity).logout()
        }
    }
}