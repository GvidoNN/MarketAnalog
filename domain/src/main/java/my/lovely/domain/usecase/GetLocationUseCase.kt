package my.lovely.domain.usecase

import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import java.util.*
import javax.inject.Inject

class GetLocationUseCase@Inject constructor(private val context: Context) {

    private lateinit var locationManager: LocationManager

    fun getLocation(): String {
        var cityName = "Санкт-Петербург"
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location != null) {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            cityName = addresses!![0].locality
        }
        return cityName
    }
}