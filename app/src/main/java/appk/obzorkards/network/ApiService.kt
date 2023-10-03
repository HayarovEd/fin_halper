package appk.obzorkards.network

import appk.obzorkards.model.CardModel
import appk.obzorkards.model.LoanModel
import appk.obzorkards.model.NewsModel
import appk.obzorkards.model.PrivacyModel
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