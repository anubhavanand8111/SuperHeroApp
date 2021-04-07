package com.anubhav.superheroapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anubhav.superheroapp.R
import com.anubhav.superheroapp.data.models.SuperHero
import com.anubhav.superheroapp.ui.adapter.SuperHeroAdapter
import com.anubhav.superheroapp.ui.viewmodel.SuperHeroViewModelG
import com.anubhav.superheroapp.utils.Status
import kotlinx.android.synthetic.main.activity_filtered.*
import kotlinx.android.synthetic.main.activity_search.*

class FilteredActivity : AppCompatActivity(),SuperHeroAdapter.OnItemClickListener {


    private val vmG by lazy {
        ViewModelProvider(this).get(SuperHeroViewModelG::class.java)

    }
    private var superHeroesList = arrayListOf<SuperHero>()


    private var filterGender:String?=null
    private var filterAlignment:String?=null
    private var filterPublisher:String?=null
    private var sortBy:String?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered)

         filterGender=intent.getStringExtra("FILTEREDGENDER")
         filterAlignment=intent.getStringExtra("FILTEREDALIGNMENT")
         filterPublisher=intent.getStringExtra("FILTEREDPUBLISHER")
         sortBy=intent.getStringExtra("SORTBY")

        getAllHero()


    }

    override fun onItemClick(position: Int) {
        val clickedItem=superHeroesList[position]
        val id = clickedItem.id
        val intent = Intent(this,SuperHeroDetailActivity::class.java)
        intent.putExtra("ID",id)
        startActivity(intent)

    }
    private fun getAllHero() {
        vmG.getAllHeroes().observe(this, {

            it?.let {resource ->
                when(resource.status){
                    Status.SUCCESS->{
                        progressBar.visibility = View.GONE
                        resource.data?.let { v-> superHeroesList.addAll(v)
                            applyFilters()
                            rvFilter.apply {
                                layoutManager = LinearLayoutManager(this@FilteredActivity)
                                adapter = SuperHeroAdapter(superHeroesList,this@FilteredActivity,this@FilteredActivity)
                            }
                        }

                    }

                    Status.ERROR->{
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE

                    }

                }

            }

        })
    }

    private fun applyFilters() {
        if (!(filterGender.equals("Any"))) {
            superHeroesList =
                superHeroesList.filter { v -> v.appearance?.gender.equals(filterGender) } as ArrayList<SuperHero>
        }


        if (!(filterAlignment.equals("all"))) {
            superHeroesList =
                superHeroesList.filter { v -> v.biography?.alignment.equals(filterAlignment) } as ArrayList<SuperHero>
        }
        if (!(filterPublisher.equals("All"))) {
            superHeroesList =
                superHeroesList.filter { v -> v.biography?.publisher.equals(filterPublisher) } as ArrayList<SuperHero>
        }

        if ((sortBy.equals("intelligence"))) {
            superHeroesList.sortByDescending { v -> v.powerstats?.intelligence?.toInt() }
        }
        else if((sortBy.equals("strength"))) {
            superHeroesList.sortByDescending { v -> v.powerstats?.strength?.toInt() }
        }
        else if((sortBy.equals("speed"))) {
            superHeroesList.sortByDescending { v -> v.powerstats?.speed?.toInt() }
        }
        else if((sortBy.equals("durability"))) {
            superHeroesList.sortByDescending { v -> v.powerstats?.durability?.toInt() }
        }
        else if((sortBy.equals("power"))) {
            superHeroesList.sortByDescending { v -> v.powerstats?.power?.toInt() }
        }
        else if((sortBy.equals("combat"))) {
            superHeroesList.sortByDescending { v -> v.powerstats?.combat?.toInt() }
        }

    }



}