package com.example.reciperecommender.services

import com.example.reciperecommender.dataClasses.RandomRecipe
import retrofit2.Call
import retrofit2.http.GET

interface RandomService {
    @GET("random?number=1&instructionsRequired=true&apiKey=32a981fec0db43f4a7c05dfe0b528578")

    fun getRandom() : Call<RandomRecipe>
}