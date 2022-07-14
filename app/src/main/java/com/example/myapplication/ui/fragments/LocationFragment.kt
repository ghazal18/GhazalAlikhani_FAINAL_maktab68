package com.example.myapplication.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import com.example.myapplication.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.example.myapplication.databinding.FragmentLocationBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LocationFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap
    var latitude = 0.0
    var longitude = 0.0
    lateinit var binding: FragmentLocationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocationPermission()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback { readyMap ->
            Log.d("location", "map ready")

            map = readyMap

        })
        binding.buttonSaveLocation.setOnClickListener {
            Toast.makeText(
                context,
                " this is latitude $latitude and this is  longitude $longitude",
                Toast.LENGTH_SHORT
            ).show()
            binding.buttonSaveLocation.isEnabled = true
        }


    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    showLocation()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    showLocation()
                }
                else -> {
                    Toast.makeText(context, "goodbye", Toast.LENGTH_SHORT).show()
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun showLocation() {

        if (!isLocationEnabled()) {
            Toast.makeText(context, "turn on your location", Toast.LENGTH_SHORT).show()
        }
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
            Toast.makeText(context, "لطفا به برنامه دسترسی لوکیشن دهید", Toast.LENGTH_SHORT).show()
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location == null) {
                showLocationOnMap(LatLng(35.725333, 51.446806))
            }
            location?.let {
                showLocationOnMap(LatLng(it.latitude, it.longitude))
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

    private fun showLocationOnMap(latLng: LatLng) {
        map.clear()
        map.setMinZoomPreference(15.0f)
        map.setMaxZoomPreference(60.0f)
        map.addMarker(
            MarkerOptions()
                .position(latLng)
                .draggable(true)
                .position(latLng)
                .title("Marker in location")
                .zIndex(2.0f)
        )
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        map.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(p0: Marker) {
            }

            override fun onMarkerDragEnd(marker: Marker) {
                Toast.makeText(context, "thisis mew drap", Toast.LENGTH_SHORT).show()
            }

            override fun onMarkerDragStart(p0: Marker) {
                Toast.makeText(context, "thisis mew drap", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }


}