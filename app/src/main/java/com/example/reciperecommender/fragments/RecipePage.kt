package com.example.reciperecommender.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.reciperecommender.R
import com.example.reciperecommender.SavedRecipesViewModel
import com.example.reciperecommender.adapters.DirectionsAdapter
import com.example.reciperecommender.adapters.RecipeAdapter
import com.example.reciperecommender.adapters.ViewPagerAdapter
import com.example.reciperecommender.dataClasses.RecipeInfo
import com.example.reciperecommender.dataClasses.SavedRecipes
import com.example.reciperecommender.services.RecipeService
import com.example.reciperecommender.services.ServiceBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_directions.*
import kotlinx.android.synthetic.main.fragment_ingredients.*
import kotlinx.android.synthetic.main.fragment_recipe_page.*
import kotlinx.android.synthetic.main.fragment_recipe_page.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipePage : Fragment() {

    private val savedRecipesViewModel by lazy {
        activity?.let { ViewModelProviders.of(it).get(SavedRecipesViewModel::class.java) }
    }

    lateinit var savedRecipe: SavedRecipes
    lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_page, container, false)
        val args = this.arguments
        id = args?.get("id").toString()

        view.addToCollection.setOnClickListener {
            saveRecipe()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadRecipeInfo(id)
        setUpTabs()
    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(IngredientsFragment(), "Ingredients")
        adapter.addFragment(DirectionsFragment(), "Directions")

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    fun loadRecipeInfo(id: String) {
        val service = ServiceBuilder.buildService(RecipeService::class.java)
        val requestCall = service.getRecipe(id)

        requestCall.enqueue(object : Callback<RecipeInfo> {
            override fun onResponse(call: Call<RecipeInfo>, response: Response<RecipeInfo>) {
                if (response.isSuccessful) {
                    val recipeInfo = response.body()!!
                    val recipeDirections = response.body()!!.analyzedInstructions[0]
                    val steps = recipeDirections.steps

                    recipeHeader.text = recipeInfo.title
                    Picasso.get().load(recipeInfo.image).into(imageView)
                    recipeTimeNumber.text = recipeInfo.readyInMinutes.toString()
                    recipeServingsNumber.text = recipeInfo.servings.toString()

                    var ingredients = recipeInfo.extendedIngredients.count()
                    var directions = steps.count()

                    ingredientRecyclerView.layoutManager = GridLayoutManager(context, ingredients, GridLayoutManager.HORIZONTAL, false)
                    ingredientRecyclerView.adapter = RecipeAdapter(response.body()!!.extendedIngredients)

                    directionRecyclerView.layoutManager = GridLayoutManager(context, directions, GridLayoutManager.HORIZONTAL, false)
                    directionRecyclerView.adapter = DirectionsAdapter(steps)

                    savedRecipe = SavedRecipes(recipeInfo.id.toString(), recipeInfo.title, recipeInfo.image, recipeInfo.servings.toString(), recipeInfo.readyInMinutes.toString())

                } else {
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("API error")
                            .setMessage("Response, but something went wrong ${response.message()}")
                            .setPositiveButton(android.R.string.ok) { _, _ -> }
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show()
                    }
                }
            }
            override fun onFailure(call: Call<RecipeInfo>, t: Throwable) {
                context?.let {
                    AlertDialog.Builder(it)
                        .setTitle("API error")
                        .setMessage("No response, something went wrong $t")
                        .setPositiveButton(android.R.string.ok) { _, _ ->}
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                }
            }
        })
    }

    fun saveRecipe() {
        Toast.makeText(context, "Recipe added to favourites", Toast.LENGTH_SHORT).show()
        savedRecipesViewModel?.savedRecipesPass?.add(savedRecipe)
    }
}