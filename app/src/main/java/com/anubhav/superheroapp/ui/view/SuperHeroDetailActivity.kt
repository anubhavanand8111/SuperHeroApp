package com.anubhav.superheroapp.ui.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anubhav.superheroapp.R
import com.anubhav.superheroapp.data.db.FavIdDatabase
import com.anubhav.superheroapp.data.db.HeroIdData
import com.anubhav.superheroapp.data.models.SuperHero
import com.anubhav.superheroapp.ui.adapter.FavoriteHeroAdapter

import com.anubhav.superheroapp.ui.viewmodel.SuperHeroViewModel
import com.anubhav.superheroapp.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_favorite_hero.*
import kotlinx.android.synthetic.main.activity_super_hero_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SuperHeroDetailActivity : AppCompatActivity() {

    private val vm by lazy {
        ViewModelProvider(this).get(SuperHeroViewModel::class.java)
    }
    private var superHero: SuperHero? = null
    private var favHeroList:List<HeroIdData>?=null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super_hero_detail)

        val id=intent.getStringExtra("ID")

        if (id != null) {
            getHero(id)
        }

        btnFav.setOnClickListener {

             val heroId= superHero?.id.toString()
            val heroName=superHero?.name.toString()
            val heroImage=superHero?.image?.url.toString()
            val heroIdData=HeroIdData(heroId,heroName,heroImage)

            GlobalScope.launch {
                applicationContext?.let {
                    FavIdDatabase(it).getFavHeroDao().insertId(heroIdData)
                }
            }

            btnFav.isEnabled=false
            Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show()

        }

        GlobalScope.launch(Dispatchers.IO) {
            applicationContext?.let {
                val idList = FavIdDatabase(it).getFavHeroDao().getFavHeroId()
                withContext(Dispatchers.Main){


                    favHeroList=idList
                }

            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    private fun getHero(id: String) {

        vm.getHero(id).observe(this, {
            it?.let {resource ->
                when(resource.status){
                    Status.SUCCESS->{
                        progressBarDetailHero.visibility = View.GONE
                        resource.data?.let { v-> detailHeroNameTv.text=v.name
                            Glide.with(this).load(v.image?.url).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(detailHeroIv)
                            publisherTv.text=v.biography?.publisher
                            fullNameTv.text=v.biography?.fullName
                            alignmentTv.text=(v.biography?.alignment)
                            strengthTv.text=v.powerstats?.strength+"%"
                            intelligenceTv.text=v.powerstats?.intelligence+"%"
                            powerTv.text=v.powerstats?.power+"%"
                            speedTv.text=v.powerstats?.speed+"%"
                            superHero=v
                            if (v.appearance?.gender.equals("Male")){
                                genderIcon.setImageResource(R.drawable.ic_baseline_male_24)
                            }
                            else{
                                genderIcon.setImageResource(R.drawable.ic_baseline_female_24)
                            }

                            (v.powerstats?.strength)?.let { it1 ->
                                strengthProgressBar.setProgress(
                                    it1.toInt(),true)
                            }
                            (v.powerstats?.speed)?.let { it1 ->
                                speedProgressBar.setProgress(
                                    it1.toInt(),true)
                            }
                            (v.powerstats?.intelligence)?.let { it1 ->
                                intelligenceProgressBar.setProgress(
                                    it1.toInt(),true)
                            }
                            (v.powerstats?.power)?.let { it1 ->
                                powerProgressBar.setProgress(
                                    it1.toInt(),true)
                            }
                        }
                    }

                    Status.ERROR->{
                        progressBarDetailHero.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBarDetailHero.visibility = View.VISIBLE

                    }

                }

            }
        })

    }
}