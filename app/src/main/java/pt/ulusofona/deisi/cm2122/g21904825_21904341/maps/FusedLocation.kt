package pt.ulusofona.deisi.cm2122.g21904825_21904341.maps

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*

@SuppressLint("MissingPermission")
class FusedLocation private constructor(context: Context) : LocationCallback() {
    private val TAG = FusedLocation::class.java.simpleName
    private val TIME_BETWEEN_UPDATES = 20 * 1000L
    private var client = FusedLocationProviderClient(context)
    private var locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = TIME_BETWEEN_UPDATES
    }

    init {
        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()

        LocationServices.getSettingsClient(context)
            .checkLocationSettings(locationSettingsRequest)

        client.requestLocationUpdates(locationRequest, this, Looper.getMainLooper())

    }

    override fun onLocationResult(locationResult: LocationResult) {
        Log.i(TAG, locationResult?.lastLocation.toString())
        notifyListeners(locationResult)
    }

    companion object {
        private var listener: OnLocationChangedListener? = null
        private var instance: FusedLocation? = null

        fun registerListener(listener: OnLocationChangedListener) {
            this.listener = listener
        }

        fun unregisterListener() {
            listener = null
        }

        fun notifyListeners(locationResult: LocationResult) {
            val location = locationResult.lastLocation
            listener?.onLocationChanged(location.latitude, location.longitude)
        }

        fun start(context: Context) {
            instance = if (instance == null) FusedLocation(context) else instance
        }

    }

}