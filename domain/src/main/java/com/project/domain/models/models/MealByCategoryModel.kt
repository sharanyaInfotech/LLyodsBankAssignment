package com.project.domain.models.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MealByCategoryModel(var meals : ArrayList<Meals> = arrayListOf())

@Parcelize
data class Meals (
var strMeal      : String? = null,
var strMealThumb : String? = null,
var idMeal       : String? = null) : Parcelable
