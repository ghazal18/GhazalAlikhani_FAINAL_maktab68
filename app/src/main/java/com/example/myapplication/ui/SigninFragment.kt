package com.example.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSigninBinding
import com.example.myapplication.model.Billing
import com.example.myapplication.model.Customer
import com.example.myapplication.model.Shipping
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class SigninFragment : Fragment() {
    lateinit var binding: FragmentSigninBinding
    val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signin, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.saveInformationButton.setOnClickListener {
            var customer = Customer(
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
                Toast.makeText(context, "سفارش شما ثبت شد", Toast.LENGTH_SHORT).show()
            }
        }

    }


}