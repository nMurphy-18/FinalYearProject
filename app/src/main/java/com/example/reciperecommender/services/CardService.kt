package com.example.reciperecommender.services

import com.example.reciperecommender.dataClasses.Recipe
import retrofit2.Call
import retrofit2.http.GET

interface CardService {
    @GET("complexSearch?instructionsRequired=true&apiKey=32a981fec0db43f4a7c05dfe0b528578")
    //27 key = 32a981fec0db43f4a7c05dfe0b528578
    //26 key = 6beebe6fb7fe4e40841607e86b9acd2c
    //uni key = cd0c9fbcb641434f8ff58e3fc233907f
    //dump key = f4935eba2bde4227a073fa71a6ebc481
    fun searchRecipe() : Call<Recipe>
}