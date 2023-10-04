package appk.obzorkardsonliness.network

import appk.obzorkardsonliness.model.CardModel
import appk.obzorkardsonliness.model.LoanModel
import appk.obzorkardsonliness.model.NewsModel
import appk.obzorkardsonliness.model.PrivacyModel
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