package com.example.llyodsbankassignment.views.ui.fragments

import android.os.Build
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
import com.example.llyodsbankassignment.views.ui.adapter.MeanDetailsAdapter
import com.example.llyodsbankassignment.views.ui.viewmodels.MealDetailViewModel
import com.example.llyodsbankassignment.utils.Resource
import com.example.llyodsbankassignment.domain.models.Categories
import com.example.llyodsbankassignment.domain.models.Meals
import com.example.llyodsbankassignment.utils.Constants
import com.example.llyodsbankassignment.utils.showOrGone
import com.example.llyodsbankassignment.utils.showToast
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
        val bundleData = if (Build.VERSION.SDK_INT >= 33) {
            requireArguments().getParcelable(Constants.KEY_DETAIL_DATA, Categories::class.java)
        } else {
            requireArguments().getParcelable(Constants.KEY_DETAIL_DATA)
        }
        val mealviewModel: MealDetailViewModel by hiltNavGraphViewModels(R.id.app_navigations)
        lifecycleScope.launch {
            bundleData?.strCategory?.let { mealviewModel.mealDetailList(it) }
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