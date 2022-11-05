package uz.nits.namangantravel.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
import uz.nits.namangantravel.model.MyPlace

interface IGoogleAPIService {
    @GET
    fun getNearbyPlaces(@Url url: String): Call<MyPlace>
}