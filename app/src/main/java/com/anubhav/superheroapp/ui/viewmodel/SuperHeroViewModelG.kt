package com.anubhav.superheroapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.anubhav.superheroapp.data.repos.SuperHeroRepositoryG
import com.anubhav.superheroapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class SuperHeroViewModelG : ViewModel(){

    fun getAllHeroes() = liveData(Dispatchers.IO){

       emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = SuperHeroRepositoryG.getAllSuperHero()))
        }catch (ex:Exception){
            emit(Resource.error(data = null,message = ex.message ?: "Error Occured!"))
        }
    }
}