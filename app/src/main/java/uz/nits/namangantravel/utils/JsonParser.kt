package uz.nits.namangantravel.utils

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class JsonParser {
    private fun parseJsonObject(jsonObject: JSONObject): HashMap<String, String> {
        var dataList = HashMap<String, String>()
        try {
            val name = jsonObject.getString("name")
            val latitude = jsonObject.getJSONObject("geometry")
                .getJSONObject("location").getString("lat")
            val longitude = jsonObject.getJSONObject("geometry")
                .getJSONObject("location").getString("lng")

            dataList.put("name", name)
            dataList.put("lat", latitude)
            dataList.put("lng", longitude)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return dataList
    }

    private fun parseJsonArray(jsonArray: JSONArray?): ArrayList<HashMap<String, String>> {
        val dataList = ArrayList<HashMap<String, String>>()
        if (jsonArray != null) {
            for (i in 0 until jsonArray.length()) {
                try {
                    val data = parseJsonObject(jsonArray.getJSONObject(i))
                    dataList.add(data)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
        return dataList
    }

    fun parseResult(jsonObject: JSONObject): List<HashMap<String, String>> {
        var jsonArray: JSONArray? = null

        try {
            jsonArray = jsonObject.getJSONArray("results")
        }catch (e: JSONException){
            e.printStackTrace()
        }

        return parseJsonArray(jsonArray)
    }

}
