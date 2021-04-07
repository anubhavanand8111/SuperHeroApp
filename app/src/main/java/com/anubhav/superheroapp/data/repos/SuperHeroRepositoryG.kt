package com.anubhav.superheroapp.data.repos


import com.anubhav.superheroapp.data.api.ClientG

object SuperHeroRepositoryG {

    suspend fun getAllSuperHero() = ClientG.api.getAllHero()
}