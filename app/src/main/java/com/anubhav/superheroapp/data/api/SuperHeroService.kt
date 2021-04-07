package com.anubhav.superheroapp.data.api

import com.anubhav.superheroapp.data.models.SuperHero
import com.anubhav.superheroapp.data.models.SuperHeroSearchResponse
import com.anubhav.superheroapp.utils.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface SuperHeroService {

    //suspend fun getHeroes() : Response<JsonObject>

    @GET("search/{hero}")
    suspend fun searchHero(@Path("hero") hero:String) :Response<SuperHeroSearchResponse>

    @GET("{id}")
    suspend fun getHero(@Path("id") id:String) :Response<SuperHero>

}
