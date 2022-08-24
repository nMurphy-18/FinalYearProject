package com.example.reciperecommender.services

import com.example.reciperecommender.dataClasses.RecipeInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeService {
    @GET("{recipe_id}/information?apiKey=32a981fec0db43f4a7c05dfe0b528578")

    fun getRecipe(@Path("recipe_id") recipeid : String) : Call<RecipeInfo>
}