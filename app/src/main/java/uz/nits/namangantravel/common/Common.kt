package uz.nits.namangantravel.common

import uz.nits.namangantravel.remote.IGoogleAPIService
import uz.nits.namangantravel.remote.RetrofitClient

object Common {

    var GOOGLE_API_URL = "https://maps.googleapis.com/"

    val googleApiService: IGoogleAPIService
        get() = RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService::class.java)
}