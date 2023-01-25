package com.example.llyodsbankassignment.views.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.llyodsbankassignment.domain.models.Meals
import com.example.llyodsbankassignment.domain.usecases.GetMealUseCase
import com.example.llyodsbankassignment.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(var getMealUseCase: GetMealUseCase): ViewModel() {
    private val _mealList = MutableStateFlow<Resource<List<Meals>>>(Resource.loading(null))
    val mealSate: StateFlow<Resource<List<Meals>>>
        get() = _mealList


    fun mealDetailList(catStr: String) {
        viewModelScope.launch {
            getMealUseCase(catStr)
                .catch { e ->
                    _mealList.value = (Resource.error(e.toString(), null))
                }
                .collect {
                    _mealList.value = (Resource.success(it.meals))
                }
        }
    }

}