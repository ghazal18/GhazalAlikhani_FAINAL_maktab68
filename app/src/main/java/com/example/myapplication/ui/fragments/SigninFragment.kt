package com.example.myapplication.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSigninBinding
import com.example.myapplication.model.Billing
import com.example.myapplication.model.Customer
import com.example.myapplication.model.Shipping
import com.example.myapplication.network.errorCode
import com.example.myapplication.viewModels.UserViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninFragment : Fragment() {
    lateinit var binding: FragmentSigninBinding
    val viewModel: UserViewModel by viewModels()
    lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var latLon = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        sp = this.requireActivity().getSharedPreferences("accountId", Context.MODE_PRIVATE)
        editor = sp.edit()
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
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
        showLocation()
        binding.saveInformationButton.setOnClickListener {
            var customer = Customer(
                0,
                binding.EditTextName.text.toString(),
                binding.EditTextLastName.text.toString(),
                binding.EditTextEmail.text.toString(),
                binding.EditTextUserName.text.toString(),
                Billing(
                    binding.EditTextFirstAddress.text.toString()+latLon,
                    binding.EditTextSecondAddress.text.toString()+latLon,
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
                if (it.data != null) {
                    println(it.data.id)
                    editor.putInt("id", it.data.id)
                    editor.putString("email", it.data.email)
                    editor.apply()
                }
                Toast.makeText(context, it.code?.let { it1 -> errorCode(it1) }, Toast.LENGTH_SHORT)
                    .show()

                if (it.code == "201") {
                    Toast.makeText(context, "سفارش شما ثبت شد", Toast.LENGTH_SHORT).show()
                }
            }
            viewModel.connectionStatus.observe(viewLifecycleOwner) {
                if (!it) {
                    Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        binding.buttonShowMap1.setOnClickListener {
            findNavController().navigate(R.id.action_signinFragment_to_locationFragment)
        }
        binding.buttonShowMap2.setOnClickListener {
            findNavController().navigate(R.id.action_signinFragment_to_locationFragment)
        }

    }

    @SuppressLint("MissingPermission")
    private fun showLocation() {

        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        if (!isLocationEnabled()) {
            Log.d("location", "turn on your location")
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                Toast.makeText(
                    context,
                    "latitude" + it.latitude + " , long=" + it.longitude,
                    Toast.LENGTH_LONG
                ).show()
                Log.d("location lastLocation", "latitude" + it.latitude + " , long=" + it.longitude)
                latLon = ",it.latitude,it.longitude"
            }
        }
        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    Toast.makeText(
                        context,
                        "latitude" + it.latitude + " , long=" + it.longitude,
                        Toast.LENGTH_LONG
                    ).show()

                }
            }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }


}