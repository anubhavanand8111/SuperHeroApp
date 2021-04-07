package com.anubhav.superheroapp.data.repos

import com.anubhav.superheroapp.data.api.Client

object SuperHeroRepository {

    suspend fun searchSuperHero(hero:String) = Client.api.searchHero(hero)

    suspend fun getSuperHero(id:String) = Client.api.getHero(id)
}