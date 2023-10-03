package appk.obzorkards.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CardModel : Serializable {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("price_info")
    var priceInfo: String? = null

    @SerializedName("image_url")
    var imageUrl: String? = null

    @SerializedName("sum")
    var sum: String? = null

    @SerializedName("term")
    var term: String? = null

    @SerializedName("percent")
    var percent: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("text")
    var text: String? = null
}