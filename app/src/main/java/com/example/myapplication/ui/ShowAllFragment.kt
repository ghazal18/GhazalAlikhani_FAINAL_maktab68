package com.example.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R


class ShowAllFragment : Fragment() {
    val args: ShowAllFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_show_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var type = args.listType
        if (type == "best"){

        }else if(type == "new"){

        }else{

        }
    }
}