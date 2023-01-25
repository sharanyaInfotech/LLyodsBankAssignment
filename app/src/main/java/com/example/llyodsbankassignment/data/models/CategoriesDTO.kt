package com.example.llyodsbankassignment.data.models

import com.google.gson.annotations.SerializedName

data class CategoriesDTO(
    @SerializedName("status_code")
    val statusCode: Int = -1,
    @SerializedName("idCategory")
    val idCategory: String = "",
    @SerializedName("strCategory")
    val strCategory: String = "",
    @SerializedName("strCategoryThumb")
    val strCategoryThumb: String = "",
    @SerializedName("strCategoryDescription")
    val strCategoryDescription: String = "",
)