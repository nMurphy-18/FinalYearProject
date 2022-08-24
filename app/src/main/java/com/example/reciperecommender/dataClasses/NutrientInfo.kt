package com.example.reciperecommender.dataClasses


import com.google.gson.annotations.SerializedName

data class NutrientInfo(
    @SerializedName("number")
    val number: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("totalResults")
    val totalResults: Int
) {
    data class Result(
        @SerializedName("aggregateLikes")
        val aggregateLikes: Int,
        @SerializedName("analyzedInstructions")
        val analyzedInstructions: List<Any>,
        @SerializedName("cheap")
        val cheap: Boolean,
        @SerializedName("creditsText")
        val creditsText: String,
        @SerializedName("cuisines")
        val cuisines: List<String>,
        @SerializedName("dairyFree")
        val dairyFree: Boolean,
        @SerializedName("diets")
        val diets: List<String>,
        @SerializedName("dishTypes")
        val dishTypes: List<String>,
        @SerializedName("gaps")
        val gaps: String,
        @SerializedName("glutenFree")
        val glutenFree: Boolean,
        @SerializedName("healthScore")
        val healthScore: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("imageType")
        val imageType: String,
        @SerializedName("license")
        val license: String,
        @SerializedName("lowFodmap")
        val lowFodmap: Boolean,
        @SerializedName("nutrition")
        val nutrition: Nutrition,
        @SerializedName("occasions")
        val occasions: List<Any>,
        @SerializedName("pricePerServing")
        val pricePerServing: Double,
        @SerializedName("readyInMinutes")
        val readyInMinutes: Int,
        @SerializedName("servings")
        val servings: Int,
        @SerializedName("sourceName")
        val sourceName: String,
        @SerializedName("sourceUrl")
        val sourceUrl: String,
        @SerializedName("spoonacularScore")
        val spoonacularScore: Int,
        @SerializedName("spoonacularSourceUrl")
        val spoonacularSourceUrl: String,
        @SerializedName("summary")
        val summary: String,
        @SerializedName("sustainable")
        val sustainable: Boolean,
        @SerializedName("title")
        val title: String,
        @SerializedName("vegan")
        val vegan: Boolean,
        @SerializedName("vegetarian")
        val vegetarian: Boolean,
        @SerializedName("veryHealthy")
        val veryHealthy: Boolean,
        @SerializedName("veryPopular")
        val veryPopular: Boolean,
        @SerializedName("weightWatcherSmartPoints")
        val weightWatcherSmartPoints: Int
    ) {
        data class Nutrition(
            @SerializedName("caloricBreakdown")
            val caloricBreakdown: CaloricBreakdown,
            @SerializedName("flavonoids")
            val flavonoids: List<Any>,
            @SerializedName("ingredients")
            val ingredients: List<Any>,
            @SerializedName("nutrients")
            val nutrients: List<Nutrient>,
            @SerializedName("properties")
            val properties: List<Any>,
            @SerializedName("weightPerServing")
            val weightPerServing: WeightPerServing
        ) {
            data class CaloricBreakdown(
                @SerializedName("percentCarbs")
                val percentCarbs: Double,
                @SerializedName("percentFat")
                val percentFat: Double,
                @SerializedName("percentProtein")
                val percentProtein: Double
            )

            data class Nutrient(
                @SerializedName("amount")
                val amount: Double,
                @SerializedName("name")
                val name: String,
                @SerializedName("percentOfDailyNeeds")
                val percentOfDailyNeeds: Double,
                @SerializedName("unit")
                val unit: String
            )

            data class WeightPerServing(
                @SerializedName("amount")
                val amount: Int,
                @SerializedName("unit")
                val unit: String
            )
        }
    }
}