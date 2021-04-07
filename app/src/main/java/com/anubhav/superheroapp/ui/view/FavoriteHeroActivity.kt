package com.anubhav.superheroapp.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anubhav.superheroapp.R
import com.anubhav.superheroapp.data.db.FavIdDatabase
import com.anubhav.superheroapp.data.db.HeroIdData
import com.anubhav.superheroapp.ui.adapter.FavoriteHeroAdapter
import kotlinx.android.synthetic.main.activity_favorite_hero.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteHeroActivity : AppCompatActivity() {

    private var favHeroList:List<HeroIdData>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_hero)

        GlobalScope.launch(Dispatchers.IO) {
            applicationContext?.let {
                val idList = FavIdDatabase(it).getFavHeroDao().getFavHeroId()
                withContext(Dispatchers.Main){


                    favHeroList=idList

                    favHeroRv.apply {
                        layoutManager = LinearLayoutManager(this@FavoriteHeroActivity)
                        adapter = FavoriteHeroAdapter(favHeroList as ArrayList<HeroIdData>,this@FavoriteHeroActivity)

                    }
                    if (favHeroList?.isNullOrEmpty() == true){
                        noHeroFoundFavoriteActivity.visibility=View.VISIBLE
                    }
                }

            }

        }

    }
}