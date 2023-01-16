package com.example.llyodsbankassignment.presentaion.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.models.models.Meals
import com.project.domain.models.usecases.GetMealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(val getMealUseCase: GetMealUseCase): ViewModel() {
    private val _mealList = MutableStateFlow<Resource<List<Meals>>>(Resource.loading(null))
    val mealSate: StateFlow<Resource<List<Meals>>>
        get() = _mealList


    fun MealDetailList() {
        viewModelScope.launch {
            getMealUseCase()
                .catch { e ->
                    _mealList.value = (Resource.error(e.toString(), null))
                }
                .collect {
                    _mealList.value = (Resource.success(it.meals))
                }
        }
    }

}