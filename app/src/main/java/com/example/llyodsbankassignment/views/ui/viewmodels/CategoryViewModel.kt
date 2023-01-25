package com.example.llyodsbankassignment.views.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.llyodsbankassignment.domain.models.Categories
import com.example.llyodsbankassignment.domain.usecases.CategoryUseCase
import com.example.llyodsbankassignment.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(var getCategoryUseCase: CategoryUseCase): ViewModel() {
    private val _cateList = MutableStateFlow<Resource<List<Categories>>>(Resource.loading(null))
    val catState: StateFlow<Resource<List<Categories>>>
        get() = _cateList

    init {
        fetchCategoryList()
    }

    private fun fetchCategoryList() {
        viewModelScope.launch {
            getCategoryUseCase()
                .catch {e-> _cateList.emit(Resource.error(e.toString(),null))}
                .collect{ _cateList.emit(Resource.success(it.categories)) }
        }
    }
}