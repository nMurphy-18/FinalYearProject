package com.example.reciperecommender

import androidx.lifecycle.ViewModel
import com.example.reciperecommender.dataClasses.SavedRecipes

class SavedRecipesViewModel: ViewModel() {

    var savedRecipesPass = mutableListOf<SavedRecipes>()
}