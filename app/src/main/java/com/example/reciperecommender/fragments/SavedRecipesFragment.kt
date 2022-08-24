package com.example.reciperecommender.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reciperecommender.MainActivity
import com.example.reciperecommender.R
import com.example.reciperecommender.SavedRecipesViewModel
import com.example.reciperecommender.adapters.SavedRecipesAdapter
import com.example.reciperecommender.dataClasses.SavedRecipes

class SavedRecipesFragment : Fragment(), SavedRecipesAdapter.OnItemClickListener {

    private val savedRecipesViewModel by lazy {
        activity?.let { ViewModelProviders.of(it).get(SavedRecipesViewModel::class.java) }
    }

    private val noSavedRecipesFragment = NoSavedRecipesFragment()
    private lateinit var savedRecipesRV: RecyclerView
    var allSavedRecipes = mutableListOf<SavedRecipes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved_recipes, container, false)
        savedRecipesRV = view.findViewById(R.id.savedRecipesRecyclerView)
        if (savedRecipesViewModel?.savedRecipesPass != null) {
            allSavedRecipes = savedRecipesViewModel!!.savedRecipesPass
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DisplaySavedRecipes()
    }

    override fun onResume() {
        super.onResume()
        DisplaySavedRecipes()
    }

    fun DisplaySavedRecipes() {
        var savedRecipeCount: Int = allSavedRecipes.count()

        if (savedRecipeCount > 0) {
            savedRecipesRV.layoutManager = GridLayoutManager(context, savedRecipeCount, GridLayoutManager.HORIZONTAL, false)
            savedRecipesRV.adapter = SavedRecipesAdapter(allSavedRecipes, this@SavedRecipesFragment)
        } else {
            (activity as MainActivity).makeCurrentFragment(noSavedRecipesFragment)
        }
    }

    override fun onItemClick(position: Int, id: String) {
        val id = id

        val bundle = Bundle()
        bundle.putString("id", id)
        val recipePage = RecipePage()
        recipePage.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.fl_wrapper, recipePage)?.commit()
    }

    override fun onDeleteIconClick(position: Int, name: String) {
        savedRecipesViewModel?.savedRecipesPass?.removeAt(position)
        savedRecipesRV.adapter?.notifyItemRemoved(position)
        Toast.makeText(view?.context, "$name removed from favourites", Toast.LENGTH_SHORT).show()
    }
}