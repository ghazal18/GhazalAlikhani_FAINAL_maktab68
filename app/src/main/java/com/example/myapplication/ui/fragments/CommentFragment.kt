package com.example.myapplication.ui.fragments

import android.content.Context
import android.content.SharedPreferences
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
import com.example.myapplication.databinding.FragmentCommentBinding
import com.example.myapplication.model.AddReview
import com.example.myapplication.viewModels.ReviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentFragment : Fragment() {
    val args: CommentFragmentArgs by navArgs()
    val viewModel: ReviewViewModel by viewModels()
    lateinit var sp: SharedPreferences
    lateinit var binding: FragmentCommentBinding
    var rating = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp = this.requireActivity().getSharedPreferences("accountId", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var email = sp.getString("email", "")



        viewModel.reviewLiveData.observe(viewLifecycleOwner) {
            it.data?.let { it1 -> println(it1.id) }
        }
        binding.CommentRatingBar.setOnRatingBarChangeListener(::ratingBar)
        binding.addReviewButton.setOnClickListener {
            val review =
                email?.let {
                    AddReview(
                        args.productIdComment,
                        rating,
                        binding.EditTextReview.text.toString(),
                        binding.EditTextReviewer.text.toString(),
                        it
                    )
                }

            if (review != null) {
                viewModel.setReview(review)
            }
        }

    }

    private fun ratingBar(ratingBar: RatingBar?, fl: Float, b: Boolean) {
        println("the number of ratin :" + "$fl")
        rating = fl.toInt()

    }
}