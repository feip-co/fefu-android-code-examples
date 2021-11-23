package ru.fefu.lesson9

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.events.MapListener
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.fefu.lesson9.databinding.ActivityLocationBinding
import java.lang.Exception

class LocationActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_RESOLVE_GOOGLE_API_ERROR = 1337
        private const val REQUEST_CODE_RESOLVE_GPS_ERROR = 1338
    }

    private lateinit var binding: ActivityLocationBinding

    private val polyline by lazy {
        Polyline().apply {
            outlinePaint.color = ContextCompat.getColor(
                this@LocationActivity,
                R.color.purple_700
            )
        }
    }

    private val permissionRequestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions[Manifest.permission.ACCESS_FINE_LOCATION]?.let {
                if (it) {
                    showUserLocation()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(this, getPreferences(Context.MODE_PRIVATE))

        // todo check if permission granted
        permissionRequestLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )

        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMap()

        binding.btnStartService.setOnClickListener {
            startLocationService()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_RESOLVE_GOOGLE_API_ERROR || requestCode == REQUEST_CODE_RESOLVE_GPS_ERROR) {
            if (resultCode == Activity.RESULT_OK) startLocationService()
        }
    }

    private fun startLocationService() {
        // todo check permissions & request if needed
        if (isGooglePlayServicesAvailable()) {
            checkIfGpsEnabled(
                { LocationService.startForeground(this, 1) },
                {
                    if (it is ResolvableApiException) {
                        it.startResolutionForResult(this, REQUEST_CODE_RESOLVE_GPS_ERROR)
                    } else {
                        Toast.makeText(this, "GPS Unavailable", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val result = googleApiAvailability.isGooglePlayServicesAvailable(this)
        if (result == ConnectionResult.SUCCESS) return true
        if (googleApiAvailability.isUserResolvableError(result)) {
            googleApiAvailability.getErrorDialog(
                this,
                result,
                REQUEST_CODE_RESOLVE_GOOGLE_API_ERROR
            )?.show()
        } else {
            Toast.makeText(
                this,
                "Google services unavailable",
                Toast.LENGTH_SHORT
            ).show()
        }
        return false
    }

    private fun checkIfGpsEnabled(success: () -> Unit, error: (Exception) -> Unit) {
        LocationServices.getSettingsClient(this)
            .checkLocationSettings(
                LocationSettingsRequest.Builder()
                    .addLocationRequest(LocationService.locationRequest)
                    .build()
            )
            .addOnSuccessListener { success.invoke() }
            .addOnFailureListener { error.invoke(it) }
    }

    private fun initMap() {
        binding.mapView.minZoomLevel = 4.0
        // post положит выполнение кода внутри в очередь,
        // что позволит выполнить этот код после полной инициализации mapView
        binding.mapView.post {
            binding.mapView.zoomToBoundingBox(
                BoundingBox(
                    43.232111,
                    132.117062,
                    42.968866,
                    131.768039
                ),
                false
            )
        }

        val eventReceiver = object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                polyline.addPoint(p)
                return true
            }

            override fun longPressHelper(p: GeoPoint?): Boolean = false
        }
        binding.mapView.overlays.add(MapEventsOverlay(eventReceiver))
        binding.mapView.overlayManager.add(polyline)
    }

    private fun showUserLocation() {
        val locationOverlay = MyLocationNewOverlay(
            object : GpsMyLocationProvider(applicationContext) {
                private var mapMoved = false
                override fun onLocationChanged(location: Location) {
                    // Location class doesn't has constructor for bearing remove
                    // With bearing mapView ignores hotspot & draws center of icon in center
                    // of user location, but we need to draw bottom of icon on user location
                    location.removeBearing()
                    super.onLocationChanged(location)
                    if (mapMoved) return
                    mapMoved = true
                    binding.mapView.controller.animateTo(
                        GeoPoint(
                            location.latitude,
                            location.longitude
                        ),
                        16.0,
                        1000
                    )
                }
            },
            binding.mapView
        )
        val locationIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_user_location)
        locationOverlay.setDirectionArrow(locationIcon, locationIcon)
        locationOverlay.setPersonHotspot(locationIcon.width / 2f, locationIcon.height.toFloat())
        locationOverlay.enableMyLocation()
        binding.mapView.overlays.add(locationOverlay)
    }
}