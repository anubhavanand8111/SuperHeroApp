package com.anubhav.superheroapp.data.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {

   private val gson:Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.superheroapi.com/api/3793452797437622/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val api = retrofit.create(SuperHeroService::class.java)
}