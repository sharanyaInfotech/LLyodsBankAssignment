package com.example.llyodsbankassignment.presentaion.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.models.models.Categories
import com.project.domain.models.usecases.CategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(val getCategoryUseCase: CategoryUseCase): ViewModel() {
    private val _cateList = MutableStateFlow<Resource<List<Categories>>>(Resource.loading(null))
    val catState: StateFlow<Resource<List<Categories>>>
        get() = _cateList


    fun fetchCategoryList() {
        viewModelScope.launch {
            getCategoryUseCase()
                .catch { e ->
                    _cateList.value = (Resource.error(e.toString(), null))
                }
                .collect {
                    _cateList.value = (Resource.success(it.categories))
                }
        }
    }

}