package appk.obzorkardsonlinesss.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class NewsModel : Serializable {

    @SerializedName("title")
    var title: String? = null

    @SerializedName("text")
    var text: String? = null

    @SerializedName("image_url")
    var imageUrl: String? = null
}