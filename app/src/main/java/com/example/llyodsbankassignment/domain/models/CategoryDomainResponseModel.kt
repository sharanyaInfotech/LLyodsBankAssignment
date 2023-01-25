package com.example.llyodsbankassignment.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class CategoryResponse(var categories : List<Categories> = arrayListOf())

@Parcelize
data class Categories(var idCategory             : String? = null,
                      var strCategory            : String? = null,
                      var strCategoryThumb       : String? = null,
                      var strCategoryDescription : String? = null): Parcelable
