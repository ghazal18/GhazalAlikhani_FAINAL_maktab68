package com.example.myapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEditCommentBinding
import com.example.myapplication.model.UpdateReview
import com.example.myapplication.viewModels.ReviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCommentFragment : Fragment() {
    lateinit var binding: FragmentEditCommentBinding
    val reviewViewModel: ReviewViewModel by viewModels()
    val args:EditCommentFragmentArgs by navArgs()
    var rating = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_edit_comment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.EditReviewButton.setOnClickListener {
            println("the review id is ${args.id} and the review rating ${rating} and the review is ${binding.EditTextReview.text.toString()}")
            reviewViewModel.updateReview(args.id, UpdateReview(rating, binding.EditTextReview.text.toString()))
        }
        binding.CommentRatingBar.setOnRatingBarChangeListener(::ratingBar)
    }
    private fun ratingBar(ratingBar: RatingBar?, fl: Float, b: Boolean) {
        println("the number of ratin :" + "$fl")
        rating = fl.toInt()

    }

}