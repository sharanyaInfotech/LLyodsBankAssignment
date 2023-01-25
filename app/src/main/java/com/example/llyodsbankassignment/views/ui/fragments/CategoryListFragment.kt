package com.example.llyodsbankassignment.views.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.llyodsbankassignment.R
import com.example.llyodsbankassignment.databinding.FragmentCategoryListBinding
import com.example.llyodsbankassignment.views.ui.adapter.CategoryAdapter
import com.example.llyodsbankassignment.views.ui.listners.OnItemClickListner
import com.example.llyodsbankassignment.views.ui.viewmodels.CategoryViewModel
import com.example.llyodsbankassignment.utils.Resource
import com.example.llyodsbankassignment.domain.models.Categories
import com.example.llyodsbankassignment.utils.Constants.KEY_DETAIL_DATA
import com.example.llyodsbankassignment.utils.showOrGone
import com.example.llyodsbankassignment.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CategoryListFragment : Fragment(), OnItemClickListner {

    private lateinit var binding:FragmentCategoryListBinding
    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View = FragmentCategoryListBinding.inflate(
        inflater,
        container,
        false
    ).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setObserver()
    }

    private fun setupUI() {
        binding.recCatList.layoutManager = LinearLayoutManager(activity)
        adapter = CategoryAdapter(this)
        binding.recCatList.addItemDecoration(
            DividerItemDecoration(
                binding.recCatList.context,
                (binding.recCatList.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recCatList.adapter = adapter

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        })
    }

    private fun setObserver(){
        val catViwModel: CategoryViewModel by hiltNavGraphViewModels(R.id.app_navigations)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                catViwModel.catState.collectLatest {
                    when(it.status){
                        Resource.Status.LOADING ->{
                            withContext(Dispatchers.Main) {
                                binding.progressBar.showOrGone(true)
                            }
                        }

                        Resource.Status.SUCCESS ->{
                                binding.progressBar.showOrGone(false)
                                adapter.updateList(it.data as ArrayList<Categories>)
                        }

                        Resource.Status.ERROR -> {
                                binding.progressBar.showOrGone(false)
                                binding.recCatList.showOrGone(true)
                                binding.txtNoData.text = "No data found"
                                activity?.showToast("Something went wrong")
                        }
                    }
                }
            }

        }
    }

    override fun onItemSelected(item: Any, itemPos: Int) {
        findNavController().navigate(R.id.action_listFrg_to_DetailFragment, bundleOf(Pair(KEY_DETAIL_DATA, item)))
    }
}