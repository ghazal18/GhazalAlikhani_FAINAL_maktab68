package com.example.myapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCommentBinding
import com.example.myapplication.model.AddReview
import com.example.myapplication.viewModels.ReviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentFragment : Fragment() {
    val args: CommentFragmentArgs by navArgs()
    val viewModel: ReviewViewModel by viewModels()
    lateinit var binding: FragmentCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val review =
            AddReview(args.productIdComment, 4, "testing", "testing", "thisIsTEst@gmail.com")
        viewModel.setReview(review)
        viewModel.reviewLiveData.observe(viewLifecycleOwner){
            it.data?.let { it1 -> println(it1.id) }
        }
    }
}