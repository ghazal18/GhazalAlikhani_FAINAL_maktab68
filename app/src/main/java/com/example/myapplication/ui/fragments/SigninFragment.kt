package com.example.myapplication.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSigninBinding
import com.example.myapplication.model.Billing
import com.example.myapplication.model.Customer
import com.example.myapplication.model.Shipping
import com.example.myapplication.viewModels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninFragment : Fragment() {
    lateinit var binding: FragmentSigninBinding
    val viewModel: UserViewModel by viewModels()
    lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        sp = this.requireActivity().getSharedPreferences("accountId", Context.MODE_PRIVATE)
        editor = sp.edit()
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signin, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.saveInformationButton.setOnClickListener {
            var customer = Customer(
                0,
                binding.EditTextName.text.toString(),
                binding.EditTextLastName.text.toString(),
                binding.EditTextEmail.text.toString(),
                binding.EditTextUserName.text.toString(),
                Billing(
                    binding.EditTextFirstAddress.text.toString(),
                    binding.EditTextSecondAddress.text.toString(),
                    binding.EditTextCity.text.toString(),
                    binding.EditTextCompany.text.toString(),
                    binding.EditTextCountry.text.toString(),
                    binding.EditTextEmail.text.toString(),
                    binding.EditTextName.text.toString(),
                    binding.EditTextLastName.text.toString(),
                    binding.EditTextPhone.text.toString(),
                    binding.EditTextPostCode.text.toString(),
                    binding.EditTextState.text.toString()
                ),
                Shipping(
                    binding.EditTextFirstAddress.text.toString(),
                    binding.EditTextSecondAddress.text.toString(),
                    binding.EditTextCity.text.toString(),
                    binding.EditTextCompany.text.toString(),
                    binding.EditTextCountry.text.toString(),
                    binding.EditTextName.text.toString(),
                    binding.EditTextLastName.text.toString(),
                    binding.EditTextPostCode.text.toString(),
                    binding.EditTextState.text.toString()
                )
            )
            viewModel.getAndSetCustomer(customer)
            viewModel.customerLiveData.observe(viewLifecycleOwner) {
                if (it != null) {
                    println(it.customer_id)
                    editor.putInt("id",it.customer_id)
                    editor.apply()
                }
                Toast.makeText(context, "سفارش شما ثبت شد", Toast.LENGTH_SHORT).show()
            }
            viewModel.connectionStatus.observe(viewLifecycleOwner){
                if (!it){
                    Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


}