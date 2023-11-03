package br.edu.infnet.petshop.api

import retrofit2.http.GET
import retrofit2.Call

interface MeowFactsService {
    @GET("?lang=por-br")
    fun getMeowFact(): Call<MeowFact>
}