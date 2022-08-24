package com.example.reciperecommender.services

import com.example.reciperecommender.dataClasses.NutrientInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("complexSearch?instructionsRequired=true&addRecipeNutrition=true&apiKey=32a981fec0db43f4a7c05dfe0b528578")

    fun searchForRecipe(@Query("query") query : String, @Query("type") type : String,
                        @Query("cuisine") cuisine : String,
                        @Query("diet") diet : String,
                        @Query("excludeIngredients") exclude : String,
                        @Query("intolerances") intolerances : String) : Call <NutrientInfo>
}