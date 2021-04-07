package com.anubhav.superheroapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.anubhav.superheroapp.data.repos.SuperHeroRepository
import com.anubhav.superheroapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class SuperHeroViewModel:ViewModel() {

    fun searchHeroes(name: String) = liveData(Dispatchers.IO){

        emit(Resource.loading(data = null))

        try {

            emit(Resource.success(data = SuperHeroRepository.searchSuperHero(name).body()?.results))
        }catch (ex: Exception){
            emit(Resource.error(data = null,message = ex.message ?: "Error Occured!"))
        }

    }

    fun getHero(id: String) = liveData(Dispatchers.IO){

        emit(Resource.loading(data = null))

        try {

            emit(Resource.success(data = SuperHeroRepository.getSuperHero(id).body()))
        }catch (ex: Exception){
            emit(Resource.error(data = null,message = ex.message ?: "Error Occured!"))
        }

    }


}