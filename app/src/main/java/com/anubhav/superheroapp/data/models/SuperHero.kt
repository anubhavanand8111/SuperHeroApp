package com.anubhav.superheroapp.data.models


data class SuperHero(
		val images: Images? = null,
	val image: Image? = null,
	val appearance: Appearance? = null,
	val response: String? = null,
	val name: String? = null,
	val powerstats: Powerstats? = null,
	val id: String? = null,
	val biography: Biography? = null
)