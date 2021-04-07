package com.anubhav.superheroapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anubhav.superheroapp.R
import com.anubhav.superheroapp.data.models.SuperHero
import com.anubhav.superheroapp.ui.adapter.SearchSuperHeroAdapter
import com.anubhav.superheroapp.ui.viewmodel.SuperHeroViewModel
import com.anubhav.superheroapp.utils.Status
import kotlinx.android.synthetic.main.activity_all_super_hero.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(),SearchSuperHeroAdapter.OnSearchedItemClickListener {

    private val vm by lazy {
        ViewModelProvider(this).get(SuperHeroViewModel::class.java)
    }
    private val superHeroesList = arrayListOf<SuperHero>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val heroName=intent.getStringExtra("q")

        if (heroName != null) {
            findHero(heroName)
        }

    }

    private fun findHero(query: String) {

        vm.searchHeroes(query).observe(this, {
            it?.let {resource ->
                when(resource.status){
                    Status.SUCCESS->{
                        progressBarSearchHero.visibility = View.GONE

                        resource.data?.let { v-> superHeroesList.addAll(v)

                            rv.apply {
                                layoutManager = LinearLayoutManager(this@SearchActivity)
                                adapter = SearchSuperHeroAdapter(superHeroesList,this@SearchActivity,this@SearchActivity)

                            }

                        }

                        if (superHeroesList.isEmpty()){
                            noHeroFoundSearchActivity.visibility = View.VISIBLE
                        }
                    }

                    Status.ERROR->{
                        progressBarSearchHero.visibility = View.GONE

                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBarSearchHero.visibility = View.VISIBLE

                    }

                }

            }


        })
    }

    override fun onItemClick(position: Int) {
        val clickedItem=superHeroesList[position]
        val id = clickedItem.id
        val intent =Intent(this,SuperHeroDetailActivity::class.java)
            intent.putExtra("ID",id)
        startActivity(intent)
    }
}