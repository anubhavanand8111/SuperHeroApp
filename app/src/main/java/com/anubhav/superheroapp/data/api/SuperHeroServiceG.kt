package com.anubhav.superheroapp.data.api

import com.anubhav.superheroapp.data.models.SuperHero
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface SuperHeroServiceG {

    @GET("all.json")
    suspend fun getAllHero() : List<SuperHero>

}