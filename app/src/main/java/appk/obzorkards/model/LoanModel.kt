package appk.obzorkards.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoanModel : Serializable {

    @SerializedName("image_url")
    var imageUrl: String? = null

    @SerializedName("rating")
    var rating: Int? = null

    @SerializedName("age")
    var age: String? = null

    @SerializedName("percent")
    var percent: String? = null

    @SerializedName("sum_one")
    var sumOne: String? = null

    @SerializedName("term_to")
    var termTo: String? = null

    @SerializedName("days_info")
    var daysInfo: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("address")
    var address: String? = null

    @SerializedName("work_time")
    var workTime: String? = null

    @SerializedName("license")
    var license: String? = null

    @SerializedName("scorost")
    var scorost: String? = null

    @SerializedName("dokuments")
    var dokuments: String? = null

    @SerializedName("sposob")
    var sposob: String? = null

    @SerializedName("visa")
    var visa: Boolean? = false

    @SerializedName("mastercard")
    var mastercard: Boolean? = false

    @SerializedName("mir")
    var mir: Boolean? = false

    @SerializedName("qiwi")
    var qiwi: Boolean? = false

    @SerializedName("yandex")
    var yandex: Boolean? = false
}