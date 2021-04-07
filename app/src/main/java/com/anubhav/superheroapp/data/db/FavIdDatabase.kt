package com.anubhav.superheroapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [HeroIdData::class],version = 2,exportSchema = false)
abstract class FavIdDatabase:RoomDatabase() {

    abstract fun getFavHeroDao():HeroIdDao

    companion object{
        @Volatile private var instance:FavIdDatabase?=null
        private val LOCK=Any()
        operator fun invoke(context: Context)=instance?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance=it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            FavIdDatabase::class.java,
            "favheroid"
        ).fallbackToDestructiveMigration().addMigrations(object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE 'HeroIdData' ADD COLUMN 'heroName' TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE 'HeroIdData' ADD COLUMN 'heroImage' TEXT NOT NULL DEFAULT ''")
            }
        }).build()
    }
}