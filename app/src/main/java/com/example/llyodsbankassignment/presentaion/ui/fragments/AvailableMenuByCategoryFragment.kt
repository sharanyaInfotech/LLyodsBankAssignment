package com.example.llyodsbankassignment.presentaion.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.llyodsbankassignment.R
import com.example.llyodsbankassignment.databinding.FragmentAvailableMenuByCategoryBinding
import com.example.llyodsbankassignment.databinding.FragmentCategoryListBinding
import com.example.llyodsbankassignment.presentaion.ui.adapter.CategoryAdapter
import com.example.llyodsbankassignment.presentaion.ui.adapter.MeanDetailsAdapter
import com.example.llyodsbankassignment.presentaion.ui.viewmodels.CategoryViewModel
import com.example.llyodsbankassignment.presentaion.ui.viewmodels.MealDetailViewModel
import com.example.llyodsbankassignment.presentaion.ui.viewmodels.Resource
import com.project.domain.models.models.Categories
import com.project.domain.models.models.Meals
import com.project.utils.showOrGone
import com.project.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AvailableMenuByCategoryFragment : Fragment() {

    private lateinit var binding: FragmentAvailableMenuByCategoryBinding
    private lateinit var adapter: MeanDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View = FragmentAvailableMenuByCategoryBinding.inflate(
        inflater,
        container,
        false
    ).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setUpViewModel()
    }

    private fun setupUI() {
        binding.recMealList.layoutManager = LinearLayoutManager(activity)
        adapter = MeanDetailsAdapter()
        binding.recMealList.addItemDecoration(
            DividerItemDecoration(
                binding.recMealList.context,
                (binding.recMealList.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recMealList.adapter = adapter
    }

    private fun setUpViewModel(){
        val mealviewModel: MealDetailViewModel by hiltNavGraphViewModels(R.id.app_navigations)
        lifecycleScope.launch {
            mealviewModel.MealDetailList()
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mealviewModel.mealSate.collectLatest {
                    when(it.status){
                        Resource.Status.LOADING ->{
                            binding.progressBar.showOrGone(true)
                        }

                        Resource.Status.SUCCESS ->{
                            binding.progressBar.showOrGone(false)
                            adapter.updateList(it.data as ArrayList<Meals>)
                        }

                        Resource.Status.ERROR -> {
                            activity?.showToast("Something went wrong")
                        }
                    }
                }
            }

        }
    }

}