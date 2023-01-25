package com.example.llyodsbankassignment.data.models

import com.google.gson.annotations.SerializedName

data class MealResponseDTO(var meals : ArrayList<MealsDTO> = arrayListOf())

data class MealsDTO (
    @SerializedName("strMeal"      ) var strMeal      : String? = null,
    @SerializedName("strMealThumb" ) var strMealThumb : String? = null,
    @SerializedName("idMeal"       ) var idMeal       : String? = null

)