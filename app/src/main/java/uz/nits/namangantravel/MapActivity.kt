package uz.nits.namangantravel

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONException
import org.json.JSONObject
import uz.nits.namangantravel.databinding.MapActivityLayoutBinding
import uz.nits.namangantravel.utils.JsonParser
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MapActivity : AppCompatActivity() {

    private lateinit var binding: MapActivityLayoutBinding
    private lateinit var supportMapFragment: SupportMapFragment

    companion object {
        private lateinit var map: GoogleMap
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var currentLat = 0.0
    private var currentLong = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MapActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.google_map_id) as SupportMapFragment
        val placeTypeList = listOf("atm", "bank", "hospital", "movie_theater", "restaurant")
        val placeNameList = listOf("ATM", "Bank", "Hospital", "Movie Theater", "Restaurant")

        binding.spinnerId.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, placeNameList)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                44
            )
        }

        binding.findId.setOnClickListener {
            val i = binding.spinnerId.selectedItemPosition
            val url = "https://maps.google.com/maps/api/place/nearbysearch/json?" +
                    "location=" + currentLat + "," + currentLong +
                    "&radius=5000" +
                    "&type=" + placeTypeList[i] +
                    "&sensor=true" +
                    "&key=" + resources.getString(R.string.google_map_key)

            PlaceTask().execute(url)
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLat = location.latitude
                currentLong = location.longitude
                supportMapFragment.getMapAsync { googleMap ->
                    map = googleMap
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(currentLat, currentLong), 10f
                        )
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 44) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            }
        }
    }

    class PlaceTask : AsyncTask<String, Int, String>() {
        override fun doInBackground(vararg strings: String?): String {
            var data = ""
            try {
                data = downloadUrl(strings[0])
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return data
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            ParserTask().execute(result)
        }

        @Throws(IOException::class)
        private fun downloadUrl(string: String?): String {
            val url = URL(string)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val stream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(stream))
            val builder = StringBuilder()
            var line = ""
            while (reader.readLine().also { line = it } != null) {
                builder.append(line)
            }
            return builder.toString()
        }
    }

        class ParserTask : AsyncTask<String, Int, List<HashMap<String, String>>>() {
        override fun doInBackground(vararg strings: String?): List<HashMap<String, String>>? {
            val jsonParser = JsonParser()
            var mapList: List<HashMap<String, String>>? = null
            val jsonObject: JSONObject?
            try {
                jsonObject = JSONObject(strings[0])
                mapList = jsonParser.parseResult(jsonObject)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return mapList
        }

        override fun onPostExecute(result: List<HashMap<String, String>>) {
            map.clear()
            for (i in result.indices) {
                val hashMapList = result[i]
                val lat = hashMapList["lat"]?.toDouble()
                val lng = hashMapList["lng"]?.toDouble()
                val name = hashMapList["name"]
                val latLng = LatLng(lat!!, lng!!)
                val makeOptions = MarkerOptions()
                makeOptions.position(latLng)
                makeOptions.title(name)
                map.addMarker(makeOptions)

            }
        }
    }
}
