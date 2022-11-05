package uz.nits.namangantravel.model

class Results {

    var name: String? = null
    var icon: String? = null
    var photos: Array<Photos>? = null
    var id: String? = null
    var place_id: String? = null
    var place_level: Int = 0
    var reting: Double = 0.0
    var reference: String? = null
    var scope: String? = null
    var types: Array<String>? = null
    var opening_hours: OpeningHours? = null

    var address_components : Array<AddressComponent>? = null
    var adr_address : String? = null
    var formatted_address : String? = null
    var formatted_phone_number : String? = null
    var geometry: Geometry?=null
    var international_phone_number:String? = null
    var reviews: Array<Review>? = null
    var utc_offset:Int = 0
    var website:String?=null

}