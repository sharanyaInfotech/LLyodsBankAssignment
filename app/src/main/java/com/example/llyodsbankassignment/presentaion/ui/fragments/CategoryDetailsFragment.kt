package com.example.llyodsbankassignment.presentaion.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.llyodsbankassignment.R
import com.example.llyodsbankassignment.databinding.FragmentCategoryDetailsBinding
import com.project.domain.models.models.Categories
import com.project.utils.Constants.KEY_DETAIL_DATA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCategoryDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View = FragmentCategoryDetailsBinding.inflate(
        inflater,
        container,
        false
    ).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI(){
        val bundleData = if (Build.VERSION.SDK_INT >= 33) {
            requireArguments().getParcelable(KEY_DETAIL_DATA, Categories::class.java)
        } else {
            requireArguments().getParcelable(KEY_DETAIL_DATA)
        }
        binding.btnMore.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_listFrg_to_mealDetailFragment, bundleOf(Pair(KEY_DETAIL_DATA, bundleData)))
        })

        with(binding){
            bundleData.run {
                 binding.foodDescription.text = bundleData?.strCategoryDescription
            }
        }
    }
}