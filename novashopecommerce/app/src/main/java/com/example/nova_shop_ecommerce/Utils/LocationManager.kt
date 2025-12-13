package com.example.nova_shop_ecommerce.Utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

class LocationManager(private val context: Context) {

    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation: StateFlow<Location?> = _currentLocation

    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun getLastKnownLocation(): Location? {
        if (!hasLocationPermission()) return null

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return try {
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) ?:
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        } catch (e: SecurityException) {
            null
        }
    }

    fun updateLocation(): Boolean {
        val location = getLastKnownLocation()
        _currentLocation.value = location
        return location != null
    }

    fun getLocationString(): String {
        val location = _currentLocation.value
        return if (location != null) {
            "${location.latitude}, ${location.longitude}"
        } else {
            "Ubicación no disponible"
        }
    }

    fun getAddressFromLocation(): AddressInfo? {
        val location = _currentLocation.value
        if (location == null) return null

        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )

            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0]
                AddressInfo(
                    direccion = address.getAddressLine(0) ?: "",
                    comuna = address.locality ?: address.subLocality ?: "",
                    ciudad = address.adminArea ?: "",
                    pais = address.countryName ?: ""
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    data class AddressInfo(
        val direccion: String,
        val comuna: String,
        val ciudad: String,
        val pais: String
    )
}