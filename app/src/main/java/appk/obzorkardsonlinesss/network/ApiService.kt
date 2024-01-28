package appk.obzorkardsonlinesss.network

import appk.obzorkardsonlinesss.model.CardModel
import appk.obzorkardsonlinesss.model.LoanModel
import appk.obzorkardsonlinesss.model.NewsModel
import appk.obzorkardsonlinesss.model.PrivacyModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("politika-confidens.json")
    fun getPrivacyPolicy(): Call<List<PrivacyModel>>

    @GET("news.json")
    fun getNews(): Call<List<NewsModel>>

    @GET("kard.json")
    fun getCards(): Call<List<CardModel>>

    @GET("zaim.json")
    fun getLoans(): Call<List<LoanModel>>
}