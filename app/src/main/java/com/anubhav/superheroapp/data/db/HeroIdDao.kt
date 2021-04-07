package com.anubhav.superheroapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HeroIdDao {

    @Insert
    suspend fun insertId(heroIdDao: HeroIdData)

    @Query("SELECT * FROM HeroIdData")
    suspend fun getFavHeroId():List<HeroIdData>
}