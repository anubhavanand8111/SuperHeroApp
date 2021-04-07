package com.anubhav.superheroapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HeroIdData(val heroId:String,val heroName:String, val heroImage:String){
    @PrimaryKey(autoGenerate = true) var id=0
}
