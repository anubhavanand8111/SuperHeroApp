package com.anubhav.superheroapp.data.models

data class SuperHeroSearchResponse(
	val resultsFor: String? = null,
	val response: String? = null,
	val results: List<SuperHero>? = null
)

